package com.ryanwarsaw.coach_erevu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.ryanwarsaw.coach_erevu.R;
import com.ryanwarsaw.coach_erevu.activity.QuizActivity;
import com.ryanwarsaw.coach_erevu.model.Question;

public class AnswerAdapter extends ArrayAdapter<String> {

  private Context context;
  private Question question;

  public AnswerAdapter(@NonNull Context context, Question question) {
    super(context, R.layout.menu_item, question.getAnswers());
    this.context = context;
    this.question = question;
  }

  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    View answerOption = convertView != null ?
        convertView : LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
    final Button button = answerOption.findViewById(R.id.menu_button);
    button.setText(question.getAnswers().get(position));
    button.setOnClickListener((QuizActivity) context);
    return answerOption;
  }
}
