package com.ryanwarsaw.coach_erevu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    final String payload = getIntent().getStringExtra("payload");
    week = new GsonBuilder().create().fromJson(payload, Week.class);

    ((TextView) findViewById(R.id.week_title)).setText(week.title);

    // Handle when a user interact's with the watch video button.
    final Button videoButton = findViewById(R.id.watch_video_button);
    videoButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        final Intent intent = new Intent(ActionActivity.this, VideoActivity.class);
        intent.putExtra("video_name", week.getVideoName());
        ActionActivity.this.startActivity(intent);
      }
    });

    final Button quizButton = findViewById(R.id.take_quiz_button);
    quizButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        if (week.getQuestions().size() > 0) {
          final Intent intent = new Intent(ActionActivity.this, QuizActivity.class);
          intent.putExtra("payload", payload);
          ActionActivity.this.startActivity(intent);
        }
      }
    });
  }
}