package com.ryanwarsaw.coach_erevu.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanwarsaw.coach_erevu.CommonUtilities;
import com.ryanwarsaw.coach_erevu.MainActivity;
import com.ryanwarsaw.coach_erevu.R;
import com.ryanwarsaw.coach_erevu.activity.ActionActivity;
import com.ryanwarsaw.coach_erevu.activity.QuizActivity;
import com.ryanwarsaw.coach_erevu.activity.VideoActivity;
import com.ryanwarsaw.coach_erevu.model.Category;
import com.ryanwarsaw.coach_erevu.model.Preferences;
import com.ryanwarsaw.coach_erevu.model.Topic;

public class TopicAdapter extends ArrayAdapter<Topic> {

  private Context context;
  private Category category;
  private Preferences preferences;

  public TopicAdapter(@NonNull Context context, Category category, Preferences preferences) {
    super(context, R.layout.menu_item, category.getTopics());
    this.context = context;
    this.category = category;
    this.preferences = preferences;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    View menuItem = convertView != null ?
            convertView : LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
    final Button button = menuItem.findViewById(R.id.menu_button);
    final Topic topic = category.getTopics().get(position);

    button.setText(topic.getTitle());

    // Dynamically change the button background color based on the content file, without changing
    // the rest of the drawable element's background(s), or other instances of the drawable.
    CommonUtilities.mutateButtonBackgroundColor(button.getBackground(), topic.getColor());

    button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        final Button button = view.findViewById(R.id.menu_button);
        final Topic topic = category.findTopicByTitle((String) button.getText());
        final Gson gson = new GsonBuilder().create();

        MainActivity.getLoggingHandler().write(TopicAdapter.this.getClass()
                .getSimpleName(), "TOPIC_SELECTED", topic.getTitle());

        final Bundle args = new Bundle();
        args.putString("topic", gson.toJson(topic));
        args.putString("preferences", gson.toJson(preferences));

        // If this topic contains a quiz and video activity, send them to the action selection menu.
        if (topic.getQuestions() != null && topic.getVideoName() != null) {
          final Intent actionIntent = new Intent(context, ActionActivity.class);
          actionIntent.putExtras(args);
          context.startActivity(actionIntent);
        } else {
          // If the topic only contains a quiz then launch user directly into quiz activity.
          if (topic.getQuestions() != null && topic.getQuestions().size() > 0) {
            MainActivity.getLoggingHandler().write(TopicAdapter.this.getClass().getSimpleName(),
                    "BUTTON_QUIZ_PRESS", topic.getTitle());
            final Intent quizIntent = new Intent(context, QuizActivity.class);
            quizIntent.putExtras(args);
            context.startActivity(quizIntent);
          }

          // If the topic only contains a video then launch user directly into video activity.
          if (topic.getVideoName() != null) {
            MainActivity.getLoggingHandler().write(TopicAdapter.this.getClass().getSimpleName(),
                    "BUTTON_VIDEO_PRESS", topic.getTitle());
            final Intent videoIntent = new Intent(context, VideoActivity.class);
            videoIntent.putExtras(args);
            context.startActivity(videoIntent);
          }
        }
      }
    });

    return menuItem;
  }
}
