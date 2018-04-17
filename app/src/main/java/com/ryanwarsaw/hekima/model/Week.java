package com.ryanwarsaw.hekima.model;

import com.google.gson.annotations.SerializedName;
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
  public String videoFileUrl;

  @SerializedName("questions")
  public Question[] questions;
}
