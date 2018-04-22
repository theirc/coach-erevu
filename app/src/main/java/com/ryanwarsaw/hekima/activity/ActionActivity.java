package com.ryanwarsaw.hekima.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.google.gson.GsonBuilder;
import com.ryanwarsaw.hekima.R;
import com.ryanwarsaw.hekima.model.Week;

public class ActionActivity extends AppCompatActivity {

  private Week week;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_action);

    // We use GSON to serialize and deserialize our payload so we
    // can handily pass it around activities without problem.
    String payload = getIntent().getStringExtra("payload");
    week = new GsonBuilder().create().fromJson(payload, Week.class);
    Log.v("MainActivity", week.toString());
  }
}
