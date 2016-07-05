package com.lixs.charts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 柱状图
 * Created by lxs on 2016/7/1.
 */
public class BarChartView extends LBaseView implements View.OnClickListener {

    private float mBorderLandLength = 0f;
    private float mBorderVerticalLength = 0f;

    private int mPadding = 0;

    private Paint mBorderLinePaint;
    private Paint mDataLinePaint;
    private Paint mTextPaint;
    private Paint mTitlePaint;

    private int defaultBorderColor = Color.argb(255, 217, 217, 217);
    private int defaultLineColor = Color.argb(255, 74, 134, 232);

    private int mTitleTextSize = 22;
    private int mLabelTextSize = 20;

    private List<Double> mDatas;
    private List<String> mDescription;

    private String mTitle;

    private Double maxData = 0d;

    public BarChartView(Context context) {
        this(context, null);
    }

    public BarChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public BarChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOnClickListener(this);
        mDatas = new ArrayList<>();
        mDescription = new ArrayList<>();

        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.barCharts);

        defaultBorderColor = t.getColor(R.styleable.barCharts_borderColor, defaultBorderColor);
        defaultLineColor = t.getColor(R.styleable.barCharts_lineColor, defaultLineColor);
        mTitleTextSize = (int) t.getDimension(R.styleable.barCharts_titleTextSize, mTitleTextSize);
        mLabelTextSize = (int) t.getDimension(R.styleable.barCharts_labelTextSize, mLabelTextSize);
        mTitle = t.getString(R.styleable.barCharts_title);

        t.recycle();
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

        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();

        mPadding = dp2px(8);
        mBorderLandLength = mWidth - mPadding * 2;
        mBorderVerticalLength = -mHeight * 4 / 5;

        setDataLineWidth();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawFrame(canvas);

        drawDataLines(canvas);

    }


    private void drawDataLines(Canvas canvas) {
        float perBarW = mBorderLandLength / mDatas.size();
        for (int i = 0; i < mDatas.size(); i++) {
            float x = (i + 0.5f) * perBarW;
            float y = (float) (mBorderVerticalLength * 0.95f / maxData * mDatas.get(i));

            canvas.drawLine(x, 0, x, y * scale, mDataLinePaint);

            String perData = String.valueOf(Math.round(scale < 1 ? Math.round(mDatas.get(i) * scale) : mDatas.get(i)));


            canvas.drawText(perData,
                    x - mTextPaint.measureText(perData) / 2,
                    y * scale - dp2px(4),
                    mTextPaint);


            if (mDescription.get(i) != null)
                canvas.drawText(mDescription.get(i),
                        x - mTextPaint.measureText(mDescription.get(i)) / 2,
                        mPadding,
                        mTextPaint);

        }
    }

    private void drawFrame(Canvas canvas) {
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

    private void setMaxData() {
        this.maxData = Collections.max(mDatas);
    }

    private void setDataLineWidth() {
        if (mDatas != null && mDatas.size() > 0) {
            mDataLinePaint.setStrokeWidth(mBorderLandLength / (mDatas.size() * 2));
        }
    }

    @Override
    public void onClick(View v) {
        animator.start();
    }

    /**
     * 设置图标标题
     *
     * @param title 标题
     */
    public void setBarTitle(String title) {
        this.mTitle = title;
    }

    /**
     * 设置数据
     *
     * @param mDatas 数据列表
     */
    public void setDatas(List<Double> mDatas, List<String> mDesciption) {
        this.mDatas.clear();

        this.mDatas.addAll(mDatas);

        this.mDescription.clear();

        this.mDescription.addAll(mDesciption);

        setMaxData();

        animator.start();
    }

    /**
     * 设置柱子颜色
     *
     * @param color 柱子颜色
     */
    public void setBarColor(int color) {
        this.defaultLineColor = color;
    }

    /**
     * 设置边框颜色
     *
     * @param color 边框颜色
     */
    public void setBorderColor(int color) {
        this.defaultBorderColor = color;
    }


}
