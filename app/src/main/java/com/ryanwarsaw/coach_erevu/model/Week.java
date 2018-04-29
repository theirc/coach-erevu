package com.ryanwarsaw.coach_erevu.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter(AccessLevel.PUBLIC)
public class Week {

  @SerializedName("id")
  public int id;

  @SerializedName("title")
  public String title;

  @SerializedName("topic")
  public String topic;

  @SerializedName("video")
  public String videoName;

  @SerializedName("questions")
  public List<Question> questions;
}
