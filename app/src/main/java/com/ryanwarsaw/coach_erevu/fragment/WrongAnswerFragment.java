package com.ryanwarsaw.coach_erevu.fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import com.ryanwarsaw.coach_erevu.R;

public class WrongAnswerFragment extends DialogFragment {

  private OnDismissListener onDismissListener;

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new Builder(getActivity());
    String dialogString = getResources().getString(R.string.wrong_answer_dialog) +
                          getArguments().getString("correct_answer") + ".\"";

    String answerExplanation = getArguments().getString("answer_explanation");

    if (answerExplanation != null) {
      dialogString = dialogString + " " + answerExplanation;
    }

    builder.setMessage(dialogString)
      .setPositiveButton(R.string.okay_button, new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          dialog.dismiss();
        }
      });
    return builder.create();
  }

  @Override
  public void onDismiss(DialogInterface dialog) {
    this.onDismissListener.onDismiss();
  }

  @Override
  public void onStart() {
    super.onStart();
    ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.buttonDark));
  }

  public interface OnDismissListener {
    public void onDismiss();
  }

  public void setOnDismissListener(OnDismissListener onDismissListener) {
    this.onDismissListener = onDismissListener;
  }
}
