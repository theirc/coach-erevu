package com.ryanwarsaw.coach_erevu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter(AccessLevel.PUBLIC)
public class Topic {

  @SerializedName("id")
  private int id;

  @SerializedName("title")
  private String title;

  @SerializedName("color")
  private String color;

  @SerializedName("video")
  private String videoName;

  @SerializedName("questions")
  private List<Question> questions;
}
