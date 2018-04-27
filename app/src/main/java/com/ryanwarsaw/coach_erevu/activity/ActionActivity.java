package com.ryanwarsaw.coach_erevu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.gson.GsonBuilder;
import com.ryanwarsaw.coach_erevu.R;
import com.ryanwarsaw.coach_erevu.model.Week;

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

    // Handle when a user interact's with the watch video button.
    final Button videoButton = findViewById(R.id.watch_video_button);
    videoButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        final Intent intent = new Intent(ActionActivity.this, VideoActivity.class);
        intent.putExtra("video_name", week.getVideoName());
        ActionActivity.this.startActivity(intent);
      }
    });
  }
}