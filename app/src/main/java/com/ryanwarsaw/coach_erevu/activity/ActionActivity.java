package com.ryanwarsaw.coach_erevu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanwarsaw.coach_erevu.MainActivity;
import com.ryanwarsaw.coach_erevu.R;
import com.ryanwarsaw.coach_erevu.model.Preferences;
import com.ryanwarsaw.coach_erevu.model.Topic;

public class ActionActivity extends AppCompatActivity {

  private Topic topic;
  private Preferences preferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_action);

    // GSON is responsible for serializing and de-serializing our payload data
    // so that we can pass it between activities using intents without problems.
    final Gson gson = new GsonBuilder().create();
    topic = gson.fromJson(getIntent().getStringExtra("topic"), Topic.class);
    preferences = gson.fromJson(getIntent().getStringExtra("preferences"), Preferences.class);

    // Update header text for the current week we're on.
    ((TextView) findViewById(R.id.header_text)).setText(topic.getTitle());

    // Handle when a user interact's with the watch video button.
    final Button videoButton = findViewById(R.id.watch_video_button);

    // Dynamically change the button background color based on the content file, without changing
    // the rest of the drawable element's background(s), or other instances of the drawable.
    ((GradientDrawable) ((LayerDrawable) videoButton.getBackground().mutate())
            .findDrawableByLayerId(R.id.button_background))
            .setColor(Color.parseColor(preferences.getVideoButtonColor()));

    videoButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        final Intent intent = new Intent(ActionActivity.this, VideoActivity.class);
        intent.putExtra("video_name", topic.getVideoName());
        intent.putExtra("preferences", gson.toJson(preferences));

        MainActivity.getLoggingHandler().write(ActionActivity.this.getClass().getSimpleName(),
            "BUTTON_VIDEO_PRESS", topic.getVideoName());
        ActionActivity.this.startActivity(intent);
      }
    });

    final Button quizButton = findViewById(R.id.take_quiz_button);

    // Dynamically change the button background color based on the content file, without changing
    // the rest of the drawable element's background(s), or other instances of the drawable.
    ((GradientDrawable) ((LayerDrawable) quizButton.getBackground().mutate())
            .findDrawableByLayerId(R.id.button_background))
            .setColor(Color.parseColor(preferences.getQuizButtonColor()));

    quizButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        if (topic.getQuestions().size() > 0) {
          final Intent intent = new Intent(ActionActivity.this, QuizActivity.class);
          intent.putExtra("topic", gson.toJson(topic));
          intent.putExtra("preferences", gson.toJson(preferences));

          MainActivity.getLoggingHandler().write(ActionActivity.this.getClass().getSimpleName(),
              "BUTTON_QUIZ_PRESS", topic.getTitle());
          ActionActivity.this.startActivity(intent);
        }
      }
    });
  }
}