package com.ryanwarsaw.coach_erevu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanwarsaw.coach_erevu.CommonUtilities;
import com.ryanwarsaw.coach_erevu.R;
import com.ryanwarsaw.coach_erevu.adapter.TopicAdapter;
import com.ryanwarsaw.coach_erevu.model.Category;
import com.ryanwarsaw.coach_erevu.model.Preferences;

import java.util.Objects;

public class TopicActivity extends AppCompatActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_topic);
    setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

    final Gson gson = new GsonBuilder().create();
    final Intent intent = getIntent();

    final Category category = gson.fromJson(intent
            .getStringExtra("category"), Category.class);
    final Preferences preferences = gson.fromJson(intent
            .getStringExtra("preferences"), Preferences.class);

    CommonUtilities.setActivityStatusBarColor(this, category.getColor());

    final ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle(category.getTitle());

      // Set the background of the ActionBar to the color of the category it represents.
      actionBar.setBackgroundDrawable(CommonUtilities.mutateButtonBackgroundColor(Objects
              .requireNonNull(ResourcesCompat
              .getDrawable(getResources(), R.drawable.header_bar, null)), category.getColor()));
    }

    ((ListView) findViewById(R.id.topic_list_options))
            .setAdapter(new TopicAdapter(this, category, preferences));
  }
}
