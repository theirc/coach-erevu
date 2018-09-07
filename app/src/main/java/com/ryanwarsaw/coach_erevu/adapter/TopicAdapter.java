package com.ryanwarsaw.coach_erevu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
        final Intent intent = new Intent(context, ActionActivity.class);
        final Gson gson = new GsonBuilder().create();

        MainActivity.getLoggingHandler().write(TopicAdapter.this.getClass()
                .getSimpleName(), "TOPIC_SELECTED", topic.getTitle());
        intent.putExtra("topic", gson.toJson(topic));
        intent.putExtra("preferences", gson.toJson(preferences));

        context.startActivity(intent);
      }
    });

    return menuItem;
  }
}
