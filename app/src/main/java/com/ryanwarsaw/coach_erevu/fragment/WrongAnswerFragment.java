package com.ryanwarsaw.coach_erevu.fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.ryanwarsaw.coach_erevu.R;
import com.ryanwarsaw.coach_erevu.activity.QuizActivity;

public class WrongAnswerFragment extends DialogFragment {

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    super.onCreateDialog(savedInstanceState);
    AlertDialog.Builder builder = new Builder(getActivity());
    String dialogString = getResources().getString(R.string.wrong_answer_dialog) + " \"" +
                          getArguments().getString("correct_answer") + ".\"";

    String answerExplanation = getArguments().getString("answer_explanation");

    if (answerExplanation != null) {
      dialogString = dialogString + " " + answerExplanation;
    }

    builder.setMessage(dialogString).setPositiveButton(R.string.okay_button, new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          dialog.cancel();
        }
    });
    return builder.create();
  }

  @Override
  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);
    final ListView listView = getActivity().findViewById(R.id.answer_options);
    listView.setVisibility(View.GONE);
    ((QuizActivity) getActivity()).advanceToNextQuestion();
  }

  @Override
  public void onStart() {
    super.onStart();
    ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.buttonDark));
  }
}
