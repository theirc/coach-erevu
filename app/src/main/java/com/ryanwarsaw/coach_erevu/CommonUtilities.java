package com.ryanwarsaw.coach_erevu;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;

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
            .findDrawableByLayerId(R.id.button_background)
            .mutate();
    backgroundDrawable.setColor(Color.parseColor(hexColor));
    return buttonDrawable;
  }
}
