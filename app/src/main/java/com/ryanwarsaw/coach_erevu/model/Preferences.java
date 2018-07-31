package com.ryanwarsaw.coach_erevu.model;

import com.google.gson.annotations.SerializedName;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter(AccessLevel.PUBLIC)
public class Preferences {

  @SerializedName("quiz-button-color")
  private String quizButtonColor;

  @SerializedName("video-button-color")
  private String videoButtonColor;

  @SerializedName("wrong-answer-button-color")
  private String wrongAnswerButtonColor;
}
