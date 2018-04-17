package com.ryanwarsaw.hekima.model;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter(AccessLevel.PUBLIC)
public class Question {

  @SerializedName("question")
  public String question;

  @SerializedName("answer-type")
  public String answerType;

  @SerializedName("answers")
  public String[] answers;

  @SerializedName("correct-answer")
  public int correctAnswerIndex;
}
