package maxter.shrink;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;

public class NavigationIconOnClickListener implements View.OnClickListener{

    final AnimatorSet animatorSet = new AnimatorSet();
    Context content;
    View sheet;
    Interpolator interpolator;
    int height;
    boolean isBackDropShown = false;
    Drawable openIcon;
    Drawable closeIcon;

    NavigationIconOnClickListener(Context context, View sheet, Interpolator interpolator,
                                  Drawable openIcon, Drawable closeIcon) {
        this.content = context;
        this.sheet = sheet;
        this.interpolator = interpolator;
        this.openIcon = openIcon;
        this.closeIcon = closeIcon;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
    }

    @Override
    public void onClick(View v) {
        isBackDropShown = !isBackDropShown;

        animatorSet.removeAllListeners();
        animatorSet.end();
        animatorSet.cancel();

        if (openIcon != null && closeIcon != null) {
            if (v instanceof ImageView) {
                if (isBackDropShown)
                    ((ImageView)v).setImageDrawable(closeIcon);
                else
                    ((ImageView)v).setImageDrawable(openIcon);
            }
        }

        final int translateY = height - 200;
        ObjectAnimator animator = ObjectAnimator.ofFloat(sheet, "translationY", isBackDropShown ? translateY : 0);
        animator.setDuration(500);

        if (interpolator != null) {
            animator.setInterpolator(interpolator);
        }

        animatorSet.play(animator);
        animator.start();
    }
}
