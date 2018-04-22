package com.ryanwarsaw.hekima;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.google.gson.GsonBuilder;
import com.ryanwarsaw.hekima.adapter.MenuAdapter;
import com.ryanwarsaw.hekima.model.Curriculum;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.Cleanup;
import lombok.Getter;

public class MainActivity extends AppCompatActivity {

  @Getter
  public Curriculum curriculum;
  private MenuAdapter menuAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Manually update the orientation when initially starting the app.
    onConfigurationChanged(getResources().getConfiguration());

    // Import the content.json file into memory, log the result.
    curriculum = parseContentFile(R.raw.content);
    Log.v("MainActivity", curriculum.toString());

    // Build the main menu options from the content.json file
    menuAdapter = new MenuAdapter(this, curriculum);
    ((ListView) findViewById(R.id.menu_options)).setAdapter(menuAdapter);
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    LinearLayout layout = findViewById(R.id.main_menu);
    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
      layout.setOrientation(LinearLayout.HORIZONTAL);
    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
      layout.setOrientation(LinearLayout.VERTICAL);
    }
  }

  private Curriculum parseContentFile(int resourceId) {
    String jsonFile = "";
    try {
      @Cleanup InputStream inputStream = getResources().openRawResource(resourceId);
      @Cleanup ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      byte[] buffer = new byte[inputStream.available()];
      inputStream.read(buffer);
      outputStream.write(buffer);
      jsonFile = outputStream.toString();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new GsonBuilder().create().fromJson(jsonFile, Curriculum.class);
  }
}
