package com.ryanwarsaw.hekima;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.google.gson.GsonBuilder;
import com.ryanwarsaw.hekima.model.Curriculum;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.Cleanup;
import lombok.Getter;

public class MainActivity extends AppCompatActivity {

  @Getter
  public Curriculum curriculum;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    curriculum = parseContentFile(R.raw.content);
    Log.v("MainActivity", curriculum.toString());
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
