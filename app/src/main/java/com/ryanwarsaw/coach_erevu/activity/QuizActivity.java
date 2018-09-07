package com.ryanwarsaw.coach_erevu.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanwarsaw.coach_erevu.CommonUtilities;
import com.ryanwarsaw.coach_erevu.MainActivity;
import com.ryanwarsaw.coach_erevu.R;
import com.ryanwarsaw.coach_erevu.adapter.MultiChoiceAdapter;
import com.ryanwarsaw.coach_erevu.fragment.FreeTextFragment;
import com.ryanwarsaw.coach_erevu.fragment.MultipleChoiceFragment;
import com.ryanwarsaw.coach_erevu.model.Preferences;
import com.ryanwarsaw.coach_erevu.model.Question;
import com.ryanwarsaw.coach_erevu.model.Topic;

import java.util.Objects;

public class QuizActivity extends AppCompatActivity {

  private Topic topic;
  private Question question;
  private MultiChoiceAdapter multiChoiceAdapter;
  private int currentIndex = 0;
  private Preferences preferences;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);
    setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

    final Gson gson = new GsonBuilder().create();
    topic = gson.fromJson(getIntent().getStringExtra("topic"), Topic.class);
    preferences = gson.fromJson(getIntent().getStringExtra("preferences"), Preferences.class);

    CommonUtilities.setActivityStatusBarColor(this, topic.getColor());

    final ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      // Set the background of the ActionBar to the color of the category it represents.
      actionBar.setBackgroundDrawable(CommonUtilities.mutateButtonBackgroundColor(Objects
              .requireNonNull(ResourcesCompat
                      .getDrawable(getResources(), R.drawable.header_bar, null)), topic.getColor()));
    }

    inflateQuestion(currentIndex, false);
  }

  private void inflateQuestion(int questionIndex, boolean replace) {
    this.question = topic.getQuestions().get(questionIndex);
    this.currentIndex = questionIndex;

    // Set the title of the Action Bar based on question related information
    final ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle(getString(R.string.question_header, topic.getTitle(),
              currentIndex + 1, topic.getQuestions().size()));
    }

    // Check that the activity layout has a fragment container defined for questions.
    if (findViewById(R.id.question_fragment_container) != null) {
      final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      final Gson gson = new GsonBuilder().create();
      final Bundle args = new Bundle();
      args.putString("question", gson.toJson(question));
      args.putString("preferences", gson.toJson(preferences));

      if (question.getAnswerType().equals("multiple-choice")) {
        MultipleChoiceFragment multipleChoiceFragment = new MultipleChoiceFragment();
        multipleChoiceFragment.setArguments(args);

        if (replace) {
          transaction.replace(R.id.question_fragment_container, multipleChoiceFragment);
          transaction.addToBackStack(null);
        } else {
          transaction.add(R.id.question_fragment_container, multipleChoiceFragment);
        }

        transaction.commit();

      } else if (question.getAnswerType().equals("free-text")) {
        FreeTextFragment freeTextFragment = new FreeTextFragment();
        freeTextFragment.setArguments(args);

        if (replace) {
          transaction.replace(R.id.question_fragment_container, freeTextFragment);
          transaction.addToBackStack(null);
        } else {
          transaction.add(R.id.question_fragment_container, freeTextFragment);
        }

        transaction.commit();
      }
    }

    MainActivity.getLoggingHandler().write(getClass().getSimpleName(), "QUIZ_QUESTION", question.getQuestion());
  }

  public void advanceToNextQuestion() {
    if (currentIndex + 1 < topic.getQuestions().size()) {
      // Inflate the next question, and replace the existing fragment.
      inflateQuestion(++currentIndex, true);
    } else {
      // Build the dialog to inform the user they've finished the quiz, and present it to them.
      AlertDialog alertDialog = new AlertDialog.Builder(this).create();
      alertDialog.setTitle(getResources().getString(R.string.quiz_end_title));
      alertDialog.setMessage(getResources().getString(R.string.quiz_end));
      alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
        public void onDismiss(DialogInterface dialog) {
          finish();
        }
      });
      alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.okay_button),
              new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                  dialog.dismiss();
                }
              });
      alertDialog.show();
    }
  }
}
