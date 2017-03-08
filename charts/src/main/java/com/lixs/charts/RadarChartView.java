package com.lixs.charts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.lixs.charts.Base.LBaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * radarChart
 * Created by lxs on 2016/7/4.
 */
public class RadarChartView extends LBaseView implements View.OnClickListener {
    private float mPadding;

    private Paint mBorderPaint;
    private Paint mTextPaint;
    private Paint mDatasPaint;

    private int defaultBorderColor = Color.argb(255, 217, 217, 217);
    private int defaultDataBackgroundColor = Color.argb(100, 153, 204, 255);

    private int defaultTextColor = Color.GRAY;

    private float mMaxRadius;

    private int PolygonNumber;

    private int ClassNumber = 3;

    private int mDescriptionTextSize = 16;

    private List<String> mDescriptions;

    private List<Float> mDatas;

    private boolean canClickAnimator = true;

    private float rx;
    private float ry;

    public RadarChartView(Context context) {
        this(context, null);
    }

    public RadarChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.radarCharts);

        mDescriptionTextSize = (int) t.getDimension(R.styleable.radarCharts_itemTextSize, 16);
        defaultTextColor = t.getColor(R.styleable.radarCharts_itemTextColor, defaultTextColor);
        defaultDataBackgroundColor = t.getColor(R.styleable.radarCharts_dataBackColor, defaultDataBackgroundColor);
        PolygonNumber = t.getInteger(R.styleable.radarCharts_polygonNumber, 0);
        ClassNumber = t.getInteger(R.styleable.radarCharts_classNumber, ClassNumber);

        t.recycle();

        mDescriptions = new ArrayList<>();
        mDatas = new ArrayList<>();

        initPaint();
    }

    private void initPaint() {
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(defaultBorderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(dp2px(1));

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(defaultTextColor);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(mDescriptionTextSize);

        mDatasPaint = new Paint();
        mDatasPaint.setAntiAlias(true);
        mDatasPaint.setColor(defaultDataBackgroundColor);
        mDatasPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setOnClickListener(this);
        if (getMeasuredWidth() > getHeight())
            mWidth = getMeasuredHeight();
        else
            mWidth = getMeasuredWidth();

        mPadding = mWidth / 7;

        mMaxRadius = mWidth / 2 - mPadding;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (PolygonNumber > 0 && PolygonNumber == mDatas.size() && mDatas.size() == mDescriptions.size()) {

            canvas.translate(mWidth / 2, mWidth / 2);

            drawFrame(canvas);

            drawDescriptions(canvas);

            drawDataArea(canvas);
        }
    }

    private void drawDataArea(Canvas canvas) {
        Path path = new Path();

        path.moveTo(mMaxRadius * mDatas.get(0) * scale, 0);

        for (int i = 0; i < PolygonNumber; i++) {

            float point_x = (float) (mMaxRadius * getCos(i * 360 / PolygonNumber) * mDatas.get(i)) * scale;
            float point_y = (float) (mMaxRadius * getSin(i * 360 / PolygonNumber) * mDatas.get(i)) * scale;

            path.lineTo(point_x, point_y);
        }

        path.close();
        canvas.drawPath(path, mDatasPaint);
    }

    private void drawFrame(Canvas canvas) {
        Path path = new Path();

        for (int j = 1; j < ClassNumber + 1; j++) {

            path.moveTo(mMaxRadius * j / ClassNumber, 0);

            for (int i = 0; i < PolygonNumber; i++) {

                float point_x = (float) (j * mMaxRadius * getCos(i * 360 / PolygonNumber) / ClassNumber);
                float point_y = (float) (j * mMaxRadius * getSin(i * 360 / PolygonNumber) / ClassNumber);

                path.lineTo(point_x, point_y);

                canvas.drawLine(point_x, point_y, -point_x, -point_y, mBorderPaint);

            }

            path.close();

        }

        canvas.drawPath(path, mBorderPaint);
    }

    private void drawDescriptions(Canvas canvas) {


        for (int i = 0; i < PolygonNumber; i++) {

            float ra = 360 * i / PolygonNumber;

            canvas.save();
            canvas.rotate(ra);

            canvas.save();
            canvas.translate(mMaxRadius, 0);
            canvas.rotate(-ra);

            if (ra == 0) {
                rx = mDescriptionTextSize / 2;
                ry = dp2px(3);
            } else if (ra > 270 && ra <= 360) {
                rx = dp2px(2);
                ry = -mDescriptionTextSize / 3;
            } else if (ra > 0 && ra < 90) {
                rx = dp2px(2);
                ry = mDescriptionTextSize;
            } else if (ra == 90) {
                rx = -mTextPaint.measureText(mDescriptions.get(i)) / 2;
                ry = mDescriptionTextSize;
            } else if (ra > 90 && ra < 180) {
                rx = -mTextPaint.measureText(mDescriptions.get(i)) * 1.2f;
                ry = mDescriptionTextSize / 2;
            } else if (ra == 270) {
                rx = -mTextPaint.measureText(mDescriptions.get(i)) / 2;
                ry = -mDescriptionTextSize / 3;
            } else {
                rx = -mTextPaint.measureText(mDescriptions.get(i)) * 1.2f;
                ry = 0;
            }

            canvas.drawText(mDescriptions.get(i), rx, ry, mTextPaint);

            canvas.restore();
            canvas.restore();
        }

    }

    private double getCos(float angle) {
        return Math.cos(Math.toRadians(angle));
    }

    private double getSin(float angle) {
        return Math.sin(Math.toRadians(angle));
    }


    public void setPolygonNumbers(int polygonNumber) {
        this.PolygonNumber = polygonNumber;
    }

    public void setClassNumbers(int classNumber) {
        this.ClassNumber = classNumber;
    }

    public void setDescriptions(List<String> descriptions) {
        this.mDescriptions = descriptions;
    }

    public void setDescriptionTextSize(int size) {
        this.mDescriptionTextSize = dp2px(size);
    }

    public void setDescriptionTextColor(int color) {
        this.defaultTextColor = color;
    }

    public void setDefaultDataBackgroundColor(int color) {
        this.defaultDataBackgroundColor = color;
    }


    public void setDatas(List<Float> datas) {
        this.mDatas = datas;
        animator.start();
    }

    @Override
    public void onClick(View v) {
        if (canClickAnimator) animator.start();
    }

    public void setCanClickAnimator(boolean can) {
        this.canClickAnimator = can;
    }
}
