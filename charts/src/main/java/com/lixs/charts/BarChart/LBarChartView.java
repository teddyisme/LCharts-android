package com.lixs.charts.BarChart;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.lixs.charts.R;
import com.lixs.charts.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author XinSheng
 */
public class LBarChartView extends FrameLayout {
    protected int defaultBorderColor = Color.argb(255, 217, 217, 217);
    protected int titleTextColor = Color.argb(255, 217, 217, 217);
    protected int labelTextColor;
    protected int mTitleTextSize = 42;
    protected int mLabelTextSize = 20;
    protected String mTitle;
    private int mWidth;
    private int mHeight;
    private int mLeftTextSpace;
    private int mBottomTextSpace;
    private int mTopTextSpace;
    protected Paint mBorderLinePaint;
    private Double maxData;

    private List<Double> mDatas;

    /**
     * 备注文本画笔
     */
    private Paint mTextPaint;
    /**
     * 标题文本画笔
     */
    private Paint mTitleTextPaint;

    private BarChart barChartView;

    public LBarChartView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public LBarChartView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LBarChartView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LBarChartView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    private void init(Context context, AttributeSet attrs) {
        mDatas = new ArrayList<>();

        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.barCharts);
        defaultBorderColor = t.getColor(R.styleable.barCharts_borderColor, defaultBorderColor);
        titleTextColor = t.getColor(R.styleable.barCharts_titleTextColor, Color.GRAY);
        mTitleTextSize = (int) t.getDimension(R.styleable.barCharts_titleTextSize, mTitleTextSize);
        mLabelTextSize = (int) t.getDimension(R.styleable.barCharts_labelTextSize, mLabelTextSize);
        labelTextColor = t.getColor(R.styleable.barCharts_labelTextColor, Color.GRAY);

        mLeftTextSpace = (int) t.getDimension(R.styleable.barCharts_leftTextSpace, 30);
        mBottomTextSpace = (int) t.getDimension(R.styleable.barCharts_bottomTextSpace, 20);
        mTopTextSpace = (int) t.getDimension(R.styleable.barCharts_topTextSpace, 50);
        mTitle = t.getString(R.styleable.barCharts_title);
        t.recycle();

        mBorderLinePaint = generatePaint();
        mBorderLinePaint.setColor(defaultBorderColor);
        mBorderLinePaint.setStrokeWidth(Utils.dp2px(context, 1));

        mTextPaint = generatePaint();
        mTextPaint.setColor(labelTextColor);
        mTextPaint.setTextSize(mLabelTextSize);

        mTitleTextPaint = generatePaint();
        mTitleTextPaint.setColor(titleTextColor);
        mTitleTextPaint.setTextSize(mTitleTextSize);

        barChartView = new BarChart(context, attrs);
        LayoutParams parames = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        parames.setMargins(mLeftTextSpace, mTopTextSpace, mLeftTextSpace, 0);
        barChartView.setLayoutParams(parames);
        barChartView.setBootomDrawPadding(mBottomTextSpace);
        barChartView.setLeftDrawPadding(mLeftTextSpace);
        barChartView.setTopDrawPadding(mTopTextSpace);
        addView(barChartView);
    }

    private Paint generatePaint() {
        Paint m = new Paint();
        m.setAntiAlias(true);
        m.setStyle(Paint.Style.STROKE);
        return m;
    }

    private void setMaxData() {
        this.maxData = Collections.max(mDatas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (mTitle != null) {
            canvas.drawText(mTitle, mWidth / 2 - mTitleTextPaint.measureText(mTitle) / 2,
                    mTopTextSpace - mTitleTextSize, mTitleTextPaint);
        }

        canvas.translate(mLeftTextSpace, mHeight - mBottomTextSpace);

        canvas.drawLine(0, 0, 0, -mHeight + mTopTextSpace + mBottomTextSpace, mBorderLinePaint);

        canvas.drawText("0", -mTextPaint.measureText("0") - Utils.dp2px(getContext(), 2), 0, mTextPaint);

        canvas.drawText(String.valueOf(maxData / 2),
                -mLeftTextSpace,
                (-mHeight + mBottomTextSpace + mTopTextSpace) / 2,
                mTextPaint);

        canvas.drawText(String.valueOf(Math.round(maxData * 1.05)),
                -mLeftTextSpace / 2 - mTextPaint.measureText(String.valueOf(Math.round(maxData * 1.05))) / 2,
                -mHeight + mBottomTextSpace + mTopTextSpace,
                mTextPaint);
    }


    public void setDatas(List<Double> mDatas, List<String> mDesciption,boolean isAnimation) {
        this.mDatas = mDatas;
        setMaxData();
        barChartView.setDatas(mDatas, mDesciption,isAnimation);
    }

    public void setDragInerfaces(DragInerfaces dragInerfaces) {
        barChartView.setDragInerfaces(dragInerfaces);
    }


    public void addEndMoreData(List<Double> mDatas, List<String> mDesciption) {
        barChartView.addEndMoreData(mDatas, mDesciption);
    }

//    public void addStartMoreData(List<Double> mDatas, List<String> mDesciption) {
//        barChartView.addStartMoreData(mDatas,mDesciption);
//    }

}
