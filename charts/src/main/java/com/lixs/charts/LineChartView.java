package com.lixs.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;

import com.lixs.charts.Base.FramBase;

import java.util.List;

/**
 * lineChart
 * Created by lxs on 2016/7/6.
 */
public class LineChartView extends FramBase {
    private Paint mPointPaint;
    private int defaultPointColor = Color.RED;
    private int defaultDataLineColor = Color.RED;

    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initPaint();
    }

    private void initPaint() {
        mTitlePaint = new Paint();
        mTitlePaint.setAntiAlias(true);
        mTitlePaint.setColor(Color.GRAY);
        mTitlePaint.setStyle(Paint.Style.STROKE);
        mTitlePaint.setTextSize(mTitleTextSize);

        mBorderLinePaint = new Paint();
        mBorderLinePaint.setColor(defaultBorderColor);
        mBorderLinePaint.setStyle(Paint.Style.STROKE);
        mBorderLinePaint.setStrokeWidth(dp2px(1));
        mBorderLinePaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.GRAY);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(mLabelTextSize);

        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);

        mPointPaint.setTextSize(mLabelTextSize);
        mPointPaint.setStrokeWidth(dp2px(2));

        mDataLinePaint = new Paint();
        mDataLinePaint.setAntiAlias(true);
        mDataLinePaint.setColor(defaultDataLineColor);
        mDataLinePaint.setStyle(Paint.Style.STROKE);
        mDataLinePaint.setStrokeWidth(dp2px(2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDataLines(canvas);
    }

    private void drawDataLines(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0.5f * mBorderLandLength / showNum, (float) (mBorderVerticalLength * 0.95f / maxData * mTruelyDrawDatas.get(0)));
        for (int i = 0; i < (showNum > mTruelyDrawDatas.size() ? mTruelyDrawDatas.size() : showNum); i++) {
            float x = (i + 0.5f) * mBorderLandLength / showNum;
            float y = (float) (mBorderVerticalLength * 0.95f / maxData * mTruelyDrawDatas.get(i));
            path.lineTo(x, y);

            if (mTruelyDescription.get(i) != null)
                canvas.drawText(mTruelyDescription.get(i),
                        x - mTextPaint.measureText(mTruelyDescription.get(i)) / 2,
                        mPadding + mTextPaint.measureText("0"),
                        mTextPaint);
        }
        canvas.drawPath(path, mDataLinePaint);

        for (int i = 0; i < (showNum > mTruelyDrawDatas.size() ? mTruelyDrawDatas.size() : showNum); i++) {
            PointF pointF = getPoint(i);
            mPointPaint.setColor(defaultPointColor);
            mPointPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(pointF.x, pointF.y, dp2px(4), mPointPaint);

            mPointPaint.setColor(Color.WHITE);
            mPointPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(pointF.x, pointF.y, dp2px(4), mPointPaint);

            String d = String.valueOf(mTruelyDrawDatas.get(i));
            if (i > 0 && getPoint(i - 1).y <= pointF.y) {
                canvas.drawText(d, pointF.x - mTextPaint.measureText(d) / 2, pointF.y + dp2px(14), mTextPaint);
            } else {
                canvas.drawText(d, pointF.x - mTextPaint.measureText(d) / 2, pointF.y - dp2px(10), mTextPaint);
            }

        }

    }

    private PointF getPoint(int i) {
        return new PointF((i + 0.5f) * mBorderLandLength / showNum, (float) (mBorderVerticalLength * 0.95f / maxData * mTruelyDrawDatas.get(i)));
    }


    public void setDatas(List<Double> mDatas, List<String> mDesciption) {
        this.mDatas = mDatas;
        this.mDescription = mDesciption;

        if (showNum > mDatas.size()) {
            this.mTruelyDrawDatas.addAll(mDatas);
            this.mTruelyDescription.addAll(mDesciption);
        } else {
            this.mTruelyDrawDatas.addAll(mDatas.subList(0, showNum));
            this.mTruelyDescription.addAll(mDesciption.subList(0, showNum));
        }

//        animator.start();
        postInvalidate();
    }

    @Override
    public void setShowNum(int showNum) {
        this.showNum = showNum;
    }

}
