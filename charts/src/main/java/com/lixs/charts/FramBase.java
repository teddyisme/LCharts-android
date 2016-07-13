package com.lixs.charts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FramBase extends LBaseView implements GestureDetector.OnGestureListener {
    private GestureDetector detector;

    protected float mBorderLandLength = 0f;
    protected float mBorderVerticalLength = 0f;
    protected int mPadding = 0;
    protected Paint mBorderLinePaint;

    protected Paint mDataLinePaint;
    protected Paint mTextPaint;
    protected Paint mTitlePaint;

    protected int defaultBorderColor = Color.argb(255, 217, 217, 217);
    protected int defaultLineColor = Color.argb(255, 74, 134, 232);

    protected int mTitleTextSize = 22;
    protected int mLabelTextSize = 20;

    protected List<Double> mDatas;
    protected List<Double> mTruelyDrawDatas;
    protected List<String> mDescription;
    protected List<String> mTruelyDescription;

    protected int showNum = 6;

    protected String mTitle;

    protected Double maxData = 0d;

    protected int mStartIndex = 0;

    public FramBase(Context context) {
        this(context, null);
    }

    public FramBase(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FramBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        detector = new GestureDetector(context, this);

        mDatas = new ArrayList<>();
        mDescription = new ArrayList<>();
        mTruelyDrawDatas = new ArrayList<>();
        mTruelyDescription = new ArrayList<>();
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.barCharts);
        defaultBorderColor = t.getColor(R.styleable.barCharts_borderColor, defaultBorderColor);
        defaultLineColor = t.getColor(R.styleable.barCharts_lineColor, defaultLineColor);
        mTitleTextSize = (int) t.getDimension(R.styleable.barCharts_titleTextSize, mTitleTextSize);
        mLabelTextSize = (int) t.getDimension(R.styleable.barCharts_labelTextSize, mLabelTextSize);
        mTitle = t.getString(R.styleable.barCharts_title);
        showNum = t.getInteger(R.styleable.radarCharts_showNumber, showNum);
        t.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    protected void drawFrame(Canvas canvas) {
        if (mTitle != null)
            canvas.drawText(mTitle, mWidth / 2 - mTitlePaint.measureText(mTitle) / 2, mHeight / 10, mTitlePaint);

        canvas.translate(mPadding * 3f, mHeight - mPadding * 2);

        canvas.drawLine(0, 0, mBorderLandLength, 0, mBorderLinePaint);

        canvas.drawLine(0, 0, 0, mBorderVerticalLength, mBorderLinePaint);

        canvas.drawText("0", -mTextPaint.measureText("0") - 2, 0, mTextPaint);

        canvas.drawText(String.valueOf(maxData / 2),
                -mTextPaint.measureText(String.valueOf(maxData / 2)) - 2,
                mBorderVerticalLength / 2,
                mTextPaint);

        canvas.drawText(String.valueOf(Math.round(maxData * 1.05)),
                -mTextPaint.measureText(String.valueOf(Math.round(maxData * 1.05))) - 2,
                mBorderVerticalLength,
                mTextPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        mPadding = dp2px(8);

        mBorderLandLength = mWidth - mPadding * 2;
        mBorderVerticalLength = -mHeight * 4 / 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setMaxData();
        drawFrame(canvas);
    }

    protected void setMaxData() {
        this.maxData = Collections.max(mTruelyDrawDatas);
    }

    public void setBorderColor(int color) {
        this.defaultBorderColor = color;
    }

    public void setShowNum(int showNum) {
        this.showNum = showNum;
    }

    public void setBarTitle(String title) {
        this.mTitle = title;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (canClickAnimation) animator.start();
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //往左滑动
        if (distanceX > dp2px(6)) {
            if (mStartIndex >= mDatas.size() - showNum) {
                mTruelyDrawDatas = mDatas.subList(mDatas.size() - showNum, mDatas.size());
                mTruelyDescription = mDescription.subList(mDatas.size() - showNum, mDatas.size());
            } else {
                mStartIndex++;
                if (mStartIndex > mDatas.size() - showNum)
                    mStartIndex = mDatas.size() - showNum;
                mTruelyDrawDatas = mDatas.subList(mStartIndex, mStartIndex + showNum);
                mTruelyDescription = mDescription.subList(mStartIndex, mStartIndex + showNum);
            }
        }
        //往右滑动
        else if (Math.abs(distanceX) > dp2px(6)) {
            if (mStartIndex <= 0) {
                mTruelyDrawDatas = mDatas.subList(0, showNum);
                mTruelyDescription = mDescription.subList(0, showNum);

            } else if (mStartIndex <= mDatas.size()) {
                mStartIndex--;
                if (mStartIndex < 0) mStartIndex = 0;
                if (mStartIndex + showNum < mDatas.size()) {
                    mTruelyDrawDatas = mDatas.subList(mStartIndex, mStartIndex + showNum);
                    mTruelyDescription = mDescription.subList(mStartIndex, mStartIndex + showNum);
                }
            }
        }

        postInvalidate();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        return false;
    }

}
