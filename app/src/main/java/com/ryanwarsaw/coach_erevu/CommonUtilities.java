package com.ryanwarsaw.coach_erevu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

public class CommonUtilities {

  /**
   * Mutates our custom button design (with black top and bottom bars) with a custom background
   * color. We use this for all of the in-app buttons, as well as our Action / Tool Bar(s).
   * @param drawable The button drawable that we are mutating.
   * @param hexColor The string hex code of the color we want to apply to the drawable.
   * @return A mutated instance of the drawable (this is local, and not globally applied).
   */
  public static LayerDrawable mutateButtonBackgroundColor(Drawable drawable, String hexColor) {
    final LayerDrawable buttonDrawable = (LayerDrawable) drawable.mutate();
    final GradientDrawable backgroundDrawable = (GradientDrawable) buttonDrawable
            .findDrawableByLayerId(R.id.background)
            .mutate();
    backgroundDrawable.setColor(Color.parseColor(hexColor));
    return buttonDrawable;
  }

  /**
   * Changes the color of the Android status bar, for the given Activity context provided.
   * @param context The context for the activity we want to change the status color for.
   * @param hexColor The string hex code of the color we want to apply.
   */
  public static void setActivityStatusBarColor(Context context, String hexColor) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      final Window window = ((Activity) context).getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.setStatusBarColor(Color.parseColor(hexColor));
    }
  }

  /**
   * Changes the color of the Android status bar, for the given Activity context provided.
   * @param context The context for the activity we want to change the status color for.
   * @param colorResourceId The resource id for the Android color resource we want to apply.
   */
  public static void setActivityStatusBarColor(Context context, int colorResourceId) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      final Window window = ((Activity) context).getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.setStatusBarColor(ContextCompat.getColor(context, colorResourceId));
    }
  }
}
