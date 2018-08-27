package com.ryanwarsaw.coach_erevu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanwarsaw.coach_erevu.R;
import com.ryanwarsaw.coach_erevu.adapter.MultiChoiceAdapter;
import com.ryanwarsaw.coach_erevu.model.Preferences;
import com.ryanwarsaw.coach_erevu.model.Question;

public class MultipleChoiceFragment extends Fragment implements View.OnClickListener {

  private Question question;
  private Preferences preferences;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Gson gson = new GsonBuilder().create();
    Bundle args = getArguments();

    this.question = gson.fromJson(args.getString("question"), Question.class);
    this.preferences = gson.fromJson(args.getString("preferences"), Preferences.class);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_quiz_multi_choice, container, false);

    final TextView questionText = view.findViewById(R.id.question);
    questionText.setText(question.getQuestion());

    final TextView questionHint = view.findViewById(R.id.hint);
    // TODO: Implement question hints from within the content.json file

    final ListView questionAnswerOptions = view.findViewById(R.id.answer_options);
    questionAnswerOptions.setAdapter(new MultiChoiceAdapter(getContext(), question, preferences));

    return view;
  }

  @Override
  public void onClick(View view) {

  }
}
