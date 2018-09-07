package com.ryanwarsaw.coach_erevu.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.gson.GsonBuilder;
import com.ryanwarsaw.coach_erevu.MainActivity;
import com.ryanwarsaw.coach_erevu.R;
import com.ryanwarsaw.coach_erevu.activity.QuizActivity;
import com.ryanwarsaw.coach_erevu.fragment.WrongAnswerFragment;
import com.ryanwarsaw.coach_erevu.model.Preferences;
import com.ryanwarsaw.coach_erevu.model.Question;

public class MultiChoiceAdapter extends ArrayAdapter<String> {

  private Context context;
  private Question question;
  private Preferences preferences;

  public MultiChoiceAdapter(@NonNull Context context, Question question, Preferences preferences) {
    super(context, R.layout.menu_item, question.getAnswers());
    this.context = context;
    this.question = question;
    this.preferences = preferences;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    View view = convertView != null ?
        convertView : LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
    final Button button = view.findViewById(R.id.menu_button);

    button.setText(question.getAnswers().get(position));
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        final Button button = view.findViewById(R.id.menu_button);
        final int answerIndex = question.getAnswers().indexOf(button.getText());

        if (question.getCorrectAnswerIndex() > 0 && answerIndex != question.getCorrectAnswerIndex() - 1) {
          // Analytics: Write a new entry with the wrong user provided answer for multi-choice question.
          MainActivity.getLoggingHandler().write(getClass().getSimpleName(), "QUIZ_MULTI_CHOICE_WRONG", button.getText());

          final WrongAnswerFragment wrongAnswerFragment = new WrongAnswerFragment();
          final Bundle args = new Bundle();
          args.putString("correct_answer", question.getAnswers().get(question.getCorrectAnswerIndex() - 1));
          args.putString("answer_explanation", question.getAnswerExplanation());
          args.putString("preferences", new GsonBuilder().create().toJson(preferences));

          wrongAnswerFragment.setArguments(args);
          wrongAnswerFragment.show(((QuizActivity) context).getFragmentManager(), "wrong_answer_dialog");

        } else {
          // Analytics: Write new entry with correct user provided answer for multi-choice question.
          MainActivity.getLoggingHandler().write(getClass().getSimpleName(), "QUIZ_MULTI_CHOICE_CORRECT", button.getText());

          // Generate the next question fragment and advance to next question
          ((QuizActivity) context).advanceToNextQuestion();
        }
      }
    });

    return view;
  }
}
