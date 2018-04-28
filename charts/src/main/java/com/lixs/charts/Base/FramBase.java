package com.lixs.charts.Base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.lixs.charts.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FramBase extends LBaseView {
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
        detector = new GestureDetector(context, new mGestureListener());

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
        showNum = t.getInteger(R.styleable.barCharts_barShowNumber, showNum);
        t.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (detector.onTouchEvent(event)) {
            return detector.onTouchEvent(event);
        }
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                endGesture();
                break;
            default:
                break;
        }

        return false;
    }

    private void endGesture() {
        scrollXSum = 0;
    }

    protected void drawFrame(Canvas canvas) {
        if (mTitle != null) {
            canvas.drawText(mTitle, mWidth / 2 - mTitlePaint.measureText(mTitle) / 2, mHeight / 10, mTitlePaint);
        }

        canvas.translate(mPadding * 3f, mHeight - mPadding * 2);

        canvas.drawLine(0, 0, mBorderLandLength, 0, mBorderLinePaint);

        canvas.drawLine(0, 0, 0, mBorderVerticalLength, mBorderLinePaint);

        canvas.drawText("0", -mTextPaint.measureText("0") - dp2px(2), 0, mTextPaint);

        canvas.drawText(String.valueOf(maxData / 2),
                -mTextPaint.measureText(String.valueOf(maxData / 2)) - dp2px(2),
                mBorderVerticalLength / 2,
                mTextPaint);

        canvas.drawText(String.valueOf(Math.round(maxData * 1.05)),
                -mTextPaint.measureText(String.valueOf(Math.round(maxData * 1.05))) - dp2px(2),
                mBorderVerticalLength,
                mTextPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        mPadding = dp2px(10);

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

    private int getScrollIndex(float distanceX) {
        if (showNum > mDatas.size()) {
            if ((int) (Math.abs(distanceX) / (mBorderLandLength / mDatas.size())) >= 1) {
                scrollXSum = 0;
                return 1;
            }
        } else {
            if ((int) (Math.abs(distanceX) / (mBorderLandLength / showNum)) >= 1) {
                scrollXSum = 0;
                return 1;
            }
        }
        return 0;
    }

    private float scrollXSum = 0;

    public class mGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (canClickAnimation) animator.start();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            scrollXSum += distanceX;
            //往左滑动
            if (scrollXSum > dp2px(6)) {
                if (mStartIndex >= mDatas.size() - showNum) {
                    mTruelyDrawDatas = mDatas.subList(mDatas.size() - showNum, mDatas.size());
                    mTruelyDescription = mDescription.subList(mDatas.size() - showNum, mDatas.size());
                } else {
                    mStartIndex += getScrollIndex(scrollXSum);
                    if (mStartIndex > mDatas.size() - showNum)
                        mStartIndex = mDatas.size() - showNum;
                    mTruelyDrawDatas = mDatas.subList(mStartIndex, mStartIndex + showNum);
                    mTruelyDescription = mDescription.subList(mStartIndex, mStartIndex + showNum);
                }
            }
            //往右滑动
            else if (Math.abs(scrollXSum) > dp2px(6)) {
                if (mStartIndex <= 0) {
                    mTruelyDrawDatas = mDatas.subList(0, showNum);
                    mTruelyDescription = mDescription.subList(0, showNum);

                } else if (mStartIndex <= mDatas.size()) {
                    mStartIndex -= getScrollIndex(scrollXSum);
                    if (mStartIndex < 0) mStartIndex = 0;
                    if (mStartIndex + showNum < mDatas.size()) {
                        mTruelyDrawDatas = mDatas.subList(mStartIndex, mStartIndex + showNum);
                        mTruelyDescription = mDescription.subList(mStartIndex, mStartIndex + showNum);
                    }
                }
            }

            postInvalidate();
            return false;
        }

    }
}
