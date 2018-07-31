package com.ryanwarsaw.coach_erevu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter(AccessLevel.PUBLIC)
public class Category {

  @SerializedName("title")
  private String title;

  @SerializedName("color")
  private String color;

  @SerializedName("topics")
  private List<Topic> topics;

  public Topic findTopicByTitle(String title) {
    for (Topic topic : getTopics()) {
      if (topic.getTitle().equalsIgnoreCase(title)) {
        return topic;
      }
    }
    return null;
  }
}
