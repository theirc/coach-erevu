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
import com.ryanwarsaw.coach_erevu.activity.TopicActivity;
import com.ryanwarsaw.coach_erevu.model.Category;
import com.ryanwarsaw.coach_erevu.model.Curriculum;

public class CategoryAdapter extends ArrayAdapter<Category> {

  private Context context;
  private Curriculum curriculum;

  public CategoryAdapter(@NonNull Context context, Curriculum curriculum) {
    super(context, R.layout.menu_item, curriculum.getCategories());
    this.context = context;
    this.curriculum = curriculum;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    View menuItem = convertView != null ?
        convertView : LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
    final Button button = menuItem.findViewById(R.id.menu_button);
    final Category category = curriculum.getCategories().get(position);

    button.setText(category.getTitle());

    // Dynamically change the button background color based on the content file, without changing
    // the rest of the drawable element's background(s), or other instances of the drawable.
    CommonUtilities.mutateButtonBackgroundColor(button.getBackground(), category.getColor());

    // Build the intent to launch the topic selector when the user clicks on the button.
    button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        final Button button = view.findViewById(R.id.menu_button);
        final Category category = curriculum.findCategoryByTitle((String) button.getText());
        final Intent intent = new Intent(context, TopicActivity.class);
        final Gson gson = new GsonBuilder().create();

        MainActivity.getLoggingHandler().write(CategoryAdapter.this.getClass()
                .getSimpleName(), "CATEGORY_SELECTED", category.getTitle());
        intent.putExtra("category", gson.toJson(category));
        intent.putExtra("preferences", gson.toJson(curriculum.getPreferences()));

        context.startActivity(intent);
      }
    });

    return menuItem;
  }
}
