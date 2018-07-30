package com.ryanwarsaw.coach_erevu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanwarsaw.coach_erevu.R;
import com.ryanwarsaw.coach_erevu.adapter.TopicAdapter;
import com.ryanwarsaw.coach_erevu.model.Category;
import com.ryanwarsaw.coach_erevu.model.Preferences;

public class TopicActivity extends AppCompatActivity {

  private Category category;
  private Preferences preferences;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_topic);

    final Gson gson = new GsonBuilder().create();
    final Intent intent = getIntent();

    category = gson.fromJson(intent.getStringExtra("category"), Category.class);
    preferences = gson.fromJson(intent.getStringExtra("preferences"), Preferences.class);

    ((ListView) findViewById(R.id.topic_list_options))
            .setAdapter(new TopicAdapter(this, category, preferences));
  }
}
