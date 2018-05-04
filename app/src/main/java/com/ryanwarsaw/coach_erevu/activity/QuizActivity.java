package com.ryanwarsaw.coach_erevu.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.GsonBuilder;
import com.ryanwarsaw.coach_erevu.MainActivity;
import com.ryanwarsaw.coach_erevu.R;
import com.ryanwarsaw.coach_erevu.adapter.AnswerAdapter;
import com.ryanwarsaw.coach_erevu.fragment.WrongAnswerFragment;
import com.ryanwarsaw.coach_erevu.model.Question;
import com.ryanwarsaw.coach_erevu.model.Week;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

  private Week week;
  private Question question;
  private AnswerAdapter answerAdapter;
  private int currentIndex = 0;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);

    final String payload = getIntent().getStringExtra("payload");
    week = new GsonBuilder().create().fromJson(payload, Week.class);

    populateQuestion(currentIndex);
  }

  private void populateQuestion(int index) {
    question = week.getQuestions().get(index);
    currentIndex = index;

    // Update the header text with current question index.
    final TextView headerText = findViewById(R.id.header_text);
    headerText.setText(week.getTitle() + " : " + getResources().getString(R.string.question) + " (" +
        (currentIndex + 1) + " " + getResources().getString(R.string.of) + " " + week.getQuestions().size() + ")");

    // Update the question text with the current question.
    final TextView questionText = findViewById(R.id.question_text);
    questionText.setText(question.getQuestion());

    // Load the appropriate components based on question type.
    if (question.getAnswerType().equals("multiple-choice")) {
      final ListView answerOptions = findViewById(R.id.answer_options);
      answerOptions.setVisibility(View.VISIBLE);
      if (answerAdapter != null) {
        answerAdapter.clear();
        answerAdapter.addAll(question.getAnswers());
        answerAdapter.notifyDataSetChanged();
      } else {
        answerAdapter = new AnswerAdapter(this, question);
        answerOptions.setAdapter(answerAdapter);
      }
    } else if (question.getAnswerType().equals("free-text")) {
      // Remove any previously entered text from the text box if it exists.
      final EditText freeText = findViewById(R.id.answer_free_text);
      freeText.getText().clear();

      final LinearLayout freeTextLayout = findViewById(R.id.free_text_layout);
      freeTextLayout.setVisibility(View.VISIBLE);
    }

    MainActivity.getLoggingHandler().write(getClass().getSimpleName(), "QUIZ_QUESTION", question.getQuestion());
  }

  public void advanceToNextQuestion() {
    // Populate the next question if we have remaining questions, otherwise finish activity.
    if (currentIndex + 1 < week.getQuestions().size()) {
      populateQuestion(++currentIndex);
    } else {
      finish();
    }
  }

  @Override
  public void onClick(View v) {
    if (question.getAnswerType().equals("multiple-choice")) {
      final Button button = v.findViewById(R.id.menu_button);
      final int index = question.getAnswers().indexOf(button.getText());
      // Then the content file specified a correct index, trigger wrong prompt if necessary.
      if (question.getCorrectAnswerIndex() > 0 && index != question.getCorrectAnswerIndex() - 1) {
        MainActivity.getLoggingHandler().write(getClass().getSimpleName(), "QUIZ_MULTI_CHOICE_WRONG", button.getText());
        WrongAnswerFragment fragment = new WrongAnswerFragment();
        Bundle args = new Bundle();
        args.putString("correct_answer", question.getAnswers().get(question.getCorrectAnswerIndex() - 1));
        args.putString("answer_explanation", question.getAnswerExplanation());
        fragment.setArguments(args);
        fragment.setOnDismissListener(new WrongAnswerFragment.OnDismissListener() {
          @Override
          public void onDismiss() {
            final ListView listView = findViewById(R.id.answer_options);
            listView.setVisibility(View.GONE);
            advanceToNextQuestion();
          }
        });
        fragment.show(getFragmentManager(), "wrong_answer_dialog");
      }
      else {
        final ListView listView = findViewById(R.id.answer_options);
        listView.setVisibility(View.GONE);
        MainActivity.getLoggingHandler().write(getClass().getSimpleName(), "QUIZ_MULTI_CHOICE_CORRECT", button.getText());
        advanceToNextQuestion();
      }
    } else if (question.getAnswerType().equals("free-text")) {
      final EditText freeText = findViewById(R.id.answer_free_text);
      MainActivity.getLoggingHandler().write(getClass().getSimpleName(), "QUIZ_FREE_TEXT_RESPONSE", freeText.getText().toString());

      InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      inputManager.hideSoftInputFromWindow((getCurrentFocus() == null) ? null
          : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

      final LinearLayout freeTextLayout = findViewById(R.id.free_text_layout);
      freeTextLayout.setVisibility(View.GONE);
      advanceToNextQuestion();
    }
  }
}
