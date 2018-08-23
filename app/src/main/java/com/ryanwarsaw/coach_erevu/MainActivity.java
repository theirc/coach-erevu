package com.ryanwarsaw.coach_erevu;

import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.gson.GsonBuilder;
import com.ryanwarsaw.coach_erevu.adapter.CategoryAdapter;
import com.ryanwarsaw.coach_erevu.logging.LoggingHandler;
import com.ryanwarsaw.coach_erevu.model.Curriculum;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import lombok.Cleanup;
import lombok.Getter;

public class MainActivity extends AppCompatActivity {

  @Getter
  public Curriculum curriculum;
  private static LoggingHandler loggingHandler;

  private static String CONTENT_FILENAME = "content.json";
  private static String LOG_FILENAME = "coach_erevu_log.csv";
  private static String[] PERMISSIONS = {
      permission.WRITE_EXTERNAL_STORAGE,
      permission.READ_EXTERNAL_STORAGE
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    CommonUtilities.setActivityStatusBarColor(this, R.color.button);

    // Mostly for first-time users, makes sure we have proper permissions before inflating content.
    if (!hasPermissions(this, PERMISSIONS)) {
      ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
    } else {
      inflateContentFile(CONTENT_FILENAME);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
    if (requestCode == 1 && grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      // Successfully received permission approval, finish inflating the main menu.
      inflateContentFile(CONTENT_FILENAME);
    } else {
      finishAffinity(); // Failed to receive permission approval, kill the application.
    }
  }

  /**
   * Parses the content file and builds our internal Curriculum object. By default it will
   * check the Download/ folder on your Android device for a file with the name provided. If
   * it is unable to find that file, it will default to the app packaged version.
   * @param fileName The fully qualified name of the file (including extension).
   */
  private void inflateContentFile(String fileName) {
    File externalFile = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + fileName);
    loggingHandler = new LoggingHandler(LOG_FILENAME);

    try {
      @Cleanup InputStream inputStream = externalFile.exists() ?
              new FileInputStream(externalFile) : getResources().openRawResource(R.raw.content);
      @Cleanup ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      byte[] buffer = new byte[inputStream.available()];
      inputStream.read(buffer);
      outputStream.write(buffer);

      curriculum = new GsonBuilder().create().fromJson(outputStream.toString(), Curriculum.class);
    } catch (IOException e) {
      throw new RuntimeException("An error occurred while trying to parse file: " + fileName);
    }

    ((ListView) findViewById(R.id.menu_options)).setAdapter(new CategoryAdapter(this, getCurriculum()));
    getLoggingHandler().write(getClass().getSimpleName(), "APP_STARTED", getCurriculum().getVersion());
  }

  /**
   * CommonUtilities method for checking multiple permissions at once for a required operation.
   * @param context The relevant context object currently being used.
   * @param permissions String array of all permissions to check for approval.
   * @return bool Depending on whether all appropriate permissions are present or not.
   */
  private boolean hasPermissions(Context context, String... permissions) {
    if (VERSION.SDK_INT >= VERSION_CODES.M && context != null && permissions != null) {
      for (String permission : permissions) {
        int result = ActivityCompat.checkSelfPermission(context, permission);
        if (result != PackageManager.PERMISSION_GRANTED) return false;
      }
    }
    return true;
  }

  /**
   * Manage our global instance of the logging handler, instantiate a new one if null.
   * @return The app instance of the logging handler (for output).
   */
  public static LoggingHandler getLoggingHandler() {
    if (loggingHandler == null) {
      loggingHandler = new LoggingHandler(LOG_FILENAME);
    }
    return loggingHandler;
  }
}
