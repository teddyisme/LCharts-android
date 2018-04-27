package com.lixs.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.lixs.charts.Base.FramBase;

import java.util.ArrayList;
import java.util.List;

/**
 * barChart
 * Created by lxs on 2016/7/1.
 */
public class BarChartView extends FramBase {
    private boolean hasMore = false;
    private float perBarW;
    private int mDrawNum;

    public BarChartView(Context context) {
        this(context, null);
    }

    public BarChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initPaint();
    }

    private void initPaint() {
        mBorderLinePaint = new Paint();
        mBorderLinePaint.setColor(defaultBorderColor);
        mBorderLinePaint.setStyle(Paint.Style.STROKE);
        mBorderLinePaint.setStrokeWidth(dp2px(1));
        mBorderLinePaint.setAntiAlias(true);

        mDataLinePaint = new Paint();
        mDataLinePaint.setAntiAlias(true);
        mDataLinePaint.setColor(defaultLineColor);
        mDataLinePaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.GRAY);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(mLabelTextSize);

        mTitlePaint = new Paint();
        mTitlePaint.setAntiAlias(true);
        mTitlePaint.setColor(Color.GRAY);
        mTitlePaint.setStyle(Paint.Style.STROKE);
        mTitlePaint.setTextSize(mTitleTextSize);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setDataLineWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDataLines(canvas);
    }

    private void drawDataLines(Canvas canvas) {
        perBarW = mBorderLandLength / mDrawNum;

        for (int i = 0; i < mDrawNum; i++) {
            float x = (i + 0.5f) * perBarW;
            float y = (float) (mBorderVerticalLength * 0.95f / maxData * mTruelyDrawDatas.get(i));

            canvas.drawLine(x, 0, x, y * scale, mDataLinePaint);

            String perData = String.valueOf(Math.round(scale < 1 ? Math.round(mTruelyDrawDatas.get(i) * scale) : mTruelyDrawDatas.get(i)));

            canvas.drawText(perData,
                    x - mTextPaint.measureText(perData) / 2,
                    y * scale - dp2px(4),
                    mTextPaint);


            if (mDescription.get(i) != null) {
                canvas.drawText(mTruelyDescription.get(i),
                        x - mTextPaint.measureText(mTruelyDescription.get(i)) / 2,
                        mPadding + mTextPaint.measureText("0"),
                        mTextPaint);
            }

        }
    }

    private void setDataLineWidth() {
        if (mDatas != null && mDatas.size() > 0) {
            mDrawNum = hasMore ? showNum : mDatas.size();
            mDataLinePaint.setStrokeWidth(mBorderLandLength / (mDrawNum * 2));
        }
    }


    public void setDatas(List<Double> mDatas, List<String> mDesciption) {
        this.mDatas.clear();
        this.mDatas.addAll(mDatas);
        this.mTruelyDrawDatas = new ArrayList<>();

        this.mDescription.clear();
        this.mDescription.addAll(mDesciption);
        this.mTruelyDescription = new ArrayList<>();

        if (showNum > mDatas.size()) {
            hasMore = false;
            this.mTruelyDrawDatas.addAll(mDatas);
            this.mTruelyDescription.addAll(mDesciption);
        } else {
            hasMore = true;
            this.mTruelyDrawDatas.addAll(mDatas.subList(0, showNum));
            this.mTruelyDescription.addAll(mDesciption.subList(0, showNum));
        }

        animator.start();
    }

    public void setBarColor(int color) {
        this.defaultLineColor = color;
    }


}
