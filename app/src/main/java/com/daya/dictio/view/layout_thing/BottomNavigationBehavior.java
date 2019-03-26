package com.daya.dictio.view.layout_thing;

import android.animation.ValueAnimator;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

public class BottomNavigationBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    private int lastStartedType;
    private ValueAnimator offsetAnimator;
    private boolean isSnappingEnabled = true; // set to false to disable snap support


    public BottomNavigationBehavior() {
        super();
    }

    private void setSnappingEnabled(boolean isEnabled) {
        isSnappingEnabled = isEnabled;
        lastStartedType = 0;
        if (offsetAnimator != null) {
            offsetAnimator.cancel();
            offsetAnimator = null;
        }

    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V child, View directTargetChild,
                                       View target, int axes, int type) {

        if (axes != ViewCompat.SCROLL_AXIS_VERTICAL)
            return false;

        lastStartedType = type;
        if (offsetAnimator != null) {
            offsetAnimator.cancel();
        }

        return true;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target, int type) {
        if (!isSnappingEnabled)
            return;

        if (lastStartedType == ViewCompat.TYPE_TOUCH || type == ViewCompat.TYPE_NON_TOUCH) {

            // find nearest seam
            float currTranslation = child.getTranslationY();
            float childHalfHeight = child.getHeight() * 0.5f;


            if (currTranslation >= childHalfHeight) {
                animateBarVisibility(child, false); // translate down

            } else {
                animateBarVisibility(child, true); // translate up
            }

        }
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout,
                                  V child,
                                  View target,
                                  int dx,
                                  int dy,
                                  int[] consumed,
                                  int type) {

        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        child.setTranslationY(Math.max(0f, Math.min(child.getHeight(), child.getTranslationY() + dy)));
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, V child, View dependency) {
        if (dependency instanceof Snackbar.SnackbarLayout) {
            updateSnackbar(child, (Snackbar.SnackbarLayout) dependency);
        }

        return super.layoutDependsOn(parent, child, dependency);
    }


    private void updateSnackbar(View child, Snackbar.SnackbarLayout snackbarLayout) {
        if (snackbarLayout.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbarLayout.getLayoutParams();

            params.setAnchorId(child.getId());
            params.anchorGravity = Gravity.CENTER_HORIZONTAL;
            params.gravity = Gravity.CENTER_HORIZONTAL;
            snackbarLayout.setLayoutParams(params);
        }
    }


    public void animateBarVisibility(final View child, boolean isVisible) {
        if (offsetAnimator == null) {
            offsetAnimator = new ValueAnimator();
            offsetAnimator.setInterpolator(new DecelerateInterpolator());
            offsetAnimator.setDuration(150L);
            offsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    child.setTranslationY((float) animation.getAnimatedValue());
                }
            });
        } else {
            offsetAnimator.cancel();
        }

        float targetTranslation = isVisible ? 0f : child.getHeight();
        offsetAnimator.setFloatValues(child.getTranslationY(), targetTranslation);
        offsetAnimator.start();
    }


    public void showBottomNavigationView(BottomNavigationView view) {
        view.animate().translationY(0);
    }


}