package com.ryanwarsaw.coach_erevu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter(AccessLevel.PUBLIC)
public class Question {

  @SerializedName("question")
  private String question;

  @SerializedName("answer-type")
  private String answerType;

  @SerializedName("answers")
  private List<String> answers;

  @SerializedName("correct-answer")
  private int correctAnswerIndex;

  @SerializedName("answer-explanation")
  private String answerExplanation;
}
