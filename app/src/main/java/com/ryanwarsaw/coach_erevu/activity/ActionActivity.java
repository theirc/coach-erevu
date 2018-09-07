package com.ryanwarsaw.coach_erevu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanwarsaw.coach_erevu.CommonUtilities;
import com.ryanwarsaw.coach_erevu.MainActivity;
import com.ryanwarsaw.coach_erevu.R;
import com.ryanwarsaw.coach_erevu.model.Preferences;
import com.ryanwarsaw.coach_erevu.model.Topic;

import java.util.Objects;

public class ActionActivity extends AppCompatActivity {

  private Topic topic;
  private Preferences preferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_action);
    setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

    // GSON is responsible for serializing and de-serializing our payload data
    // so that we can pass it between activities using intents without problems.
    final Gson gson = new GsonBuilder().create();
    topic = gson.fromJson(getIntent().getStringExtra("topic"), Topic.class);
    preferences = gson.fromJson(getIntent().getStringExtra("preferences"), Preferences.class);

    CommonUtilities.setActivityStatusBarColor(this, topic.getColor());

    final ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle(topic.getTitle());

      // Set the background of the ActionBar to the color of the category it represents.
      actionBar.setBackgroundDrawable(CommonUtilities.mutateButtonBackgroundColor(Objects
              .requireNonNull(ResourcesCompat
              .getDrawable(getResources(), R.drawable.header_bar, null)), topic.getColor()));
    }

    // Handle when a user interact's with the watch video button.
    final Button videoButton = findViewById(R.id.watch_video_button);

    // Dynamically change the button background color based on the content file, without changing
    // the rest of the drawable element's background(s), or other instances of the drawable.
    videoButton.setBackground(CommonUtilities
            .mutateButtonBackgroundColor(videoButton.getBackground(), preferences.getVideoButtonColor()));

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
    quizButton.setBackground(CommonUtilities
            .mutateButtonBackgroundColor(quizButton.getBackground(), preferences.getQuizButtonColor()));

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