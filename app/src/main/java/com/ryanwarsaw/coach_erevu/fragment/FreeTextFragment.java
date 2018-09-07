package com.ryanwarsaw.coach_erevu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanwarsaw.coach_erevu.MainActivity;
import com.ryanwarsaw.coach_erevu.R;
import com.ryanwarsaw.coach_erevu.activity.QuizActivity;
import com.ryanwarsaw.coach_erevu.model.Preferences;
import com.ryanwarsaw.coach_erevu.model.Question;

public class FreeTextFragment extends Fragment {

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
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_quiz_free_text, container, false);

    final TextView questionText = view.findViewById(R.id.question);
    questionText.setText(question.getQuestion());

    final TextView questionHint = view.findViewById(R.id.hint);
    // TODO: Implement question hints from within the content.json file

    final Button button = view.findViewById(R.id.submit_button);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final EditText response = view.findViewById(R.id.free_text_box);

        // Analytics: Write new entry with user response to free-text question.
        MainActivity.getLoggingHandler().write(getClass().getSimpleName(), "QUIZ_FREE_TEXT_RESPONSE", response.getText().toString());

        // Close the keyboard if it's still open and accessible to the user.
        InputMethodManager inputManager = (InputMethodManager) FreeTextFragment.this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
          inputManager.hideSoftInputFromWindow((FreeTextFragment.this.getActivity().getCurrentFocus() == null) ? null
                  : FreeTextFragment.this.getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // Generate the next question fragment and advance to next question
        ((QuizActivity) FreeTextFragment.this.getActivity()).advanceToNextQuestion();
      }
    });

    return view;
  }
}
