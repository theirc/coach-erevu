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
import com.google.gson.GsonBuilder;
import com.ryanwarsaw.coach_erevu.R;
import com.ryanwarsaw.coach_erevu.activity.ActionActivity;
import com.ryanwarsaw.coach_erevu.model.Curriculum;
import com.ryanwarsaw.coach_erevu.model.Week;

public class MenuAdapter extends ArrayAdapter<Week> {

  private Context context;
  private Curriculum curriculum;

  public MenuAdapter(@NonNull Context context, Curriculum curriculum) {
    super(context, R.layout.menu_item, curriculum.getWeeks());
    this.context = context;
    this.curriculum = curriculum;
  }

  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    View menuItem = convertView != null ?
        convertView : LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
    final Button button = menuItem.findViewById(R.id.menu_button);
    button.setText(curriculum.getWeeks().get(position).getTitle());
    button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        final Button button = view.findViewById(R.id.menu_button);
        final Week week = curriculum.findWeekByTitle((String) button.getText());
        final Intent intent = new Intent(context, ActionActivity.class);
        intent.putExtra("payload", new GsonBuilder().create().toJson(week));
        context.startActivity(intent);
      }
    });
    return menuItem;
  }
}
