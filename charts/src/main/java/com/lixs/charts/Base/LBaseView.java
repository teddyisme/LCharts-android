package com.lixs.charts.Base;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class LBaseView extends View {
    protected float mWidth = 0f;
    protected float mHeight = 0f;

    protected float scale = 0.5f;

    protected boolean canClickAnimation = false;

    protected ValueAnimator animator;

    public LBaseView(Context context) {
        super(context);
        initAnimation();
    }

    public LBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAnimation();
    }

    public LBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAnimation();
    }

    private void initAnimation() {
        animator = ValueAnimator.ofFloat(0.2f, 1);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(600);
        animator.setRepeatCount(0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scale = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
    }

    protected int dp2px(int dpValue) {
        return (int) getContext().getResources().getDisplayMetrics().density * dpValue;
    }

    public void setCanClickAnimation(boolean canClickAnimation) {
        this.canClickAnimation = canClickAnimation;
    }

}
