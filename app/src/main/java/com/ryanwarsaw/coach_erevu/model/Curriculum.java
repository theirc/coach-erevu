package com.ryanwarsaw.coach_erevu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter(AccessLevel.PUBLIC)
public class Curriculum {

  @SerializedName("version")
  private int version;

  @SerializedName("preferences")
  private Preferences preferences;

  @SerializedName("categories")
  private List<Category> categories;

  public Category findCategoryByTitle(String title) {
    for (Category category : getCategories()) {
      if (category.getTitle().equalsIgnoreCase(title)) {
        return category;
      }
    }
    return null;
  }
}
