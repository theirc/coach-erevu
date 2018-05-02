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
  public String question;

  @SerializedName("answer-type")
  public String answerType;

  @SerializedName("answers")
  public List<String> answers;

  @SerializedName("correct-answer")
  public int correctAnswerIndex;

  @SerializedName("answer-explanation")
  public String answerExplanation;
}
