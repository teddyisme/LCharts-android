package com.lixs.charts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lixs.charts.Base.LBaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * pieChart
 * Created by lxs on 2016/6/29.
 */
public class PieChartView extends LBaseView {
    private float mRadius = 0f;

    private Paint mCirclePaint;
    private Paint mArcPaint;

    private int mCircleStrokeWidth;

    private RectF arcRect;

    private List<Float> mRatios;
    private List<String> mDescription;
    private List<Integer> mArcColors;

    private PieClickListener pieClickListener;

    private int lineColor = -1;

    private int defaultBackColor = Color.argb(255, 217, 217, 217);

    public PieChartView(Context context) {
        this(context, null);
    }


    public PieChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.pieCharts);
        lineColor = t.getColor(R.styleable.pieCharts_pieLineColor, -1);
        defaultBackColor = t.getColor(R.styleable.pieCharts_backColor, defaultBackColor);
        mCircleStrokeWidth = (int) t.getDimension(R.styleable.pieCharts_circleStrokeWidth, 4);
        t.recycle();

        mRatios = new ArrayList<>();
        mArcColors = new ArrayList<>();
        mDescription = new ArrayList<>();

        mCirclePaint = new Paint();
        mCirclePaint.setColor(defaultBackColor);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setStrokeWidth(mCircleStrokeWidth);
        mCirclePaint.setAntiAlias(true);

        mArcPaint = new Paint();
        mArcPaint.setStyle(Paint.Style.FILL);
        mArcPaint.setAntiAlias(true);

        arcRect = new RectF();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getMeasuredWidth() > getHeight()) {
            mWidth = getMeasuredHeight();
        } else {
            mWidth = getMeasuredWidth();
        }

        int mPadding = (int) (mWidth / 4);

        mRadius = mWidth / 2 - mPadding + mCircleStrokeWidth;

        arcRect.set(mWidth / 2 - mRadius + mCircleStrokeWidth,
                mWidth / 2 - mRadius + mCircleStrokeWidth,
                mWidth / 2 + mRadius - mCircleStrokeWidth,
                mWidth / 2 + mRadius - mCircleStrokeWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawDescription(canvas);

        canvas.scale(scale, scale, mWidth / 2, mWidth / 2);

        canvas.drawCircle(mWidth / 2, mWidth / 2, mRadius, mCirclePaint);

        drawArc(canvas);

    }


    private void drawArc(Canvas canvas) {

        float drawArc = 360 * scale;

        for (int i = 0; i < mRatios.size(); i++) {

            mArcPaint.setColor(mArcColors.get(i));

            canvas.drawArc(arcRect, getRatioSum(i) * drawArc, mRatios.get(i) * drawArc, true, mArcPaint);

        }
    }


    private void drawDescription(Canvas canvas) {

        for (int i = 0; i < mRatios.size(); i++) {

            if (lineColor != -1) {
                mArcPaint.setColor(lineColor);
            } else {
                mArcPaint.setColor(mArcColors.get(i));
            }

            canvas.save();

            canvas.translate(mWidth / 2, mWidth / 2);

            canvas.rotate(getRatioHalfSumDegrees(i));

            canvas.drawLine(mRadius + dp2px(4), 0, mRadius + dp2px(40), 0, mArcPaint);

            drawDicatelines(canvas, i);

            canvas.restore();
        }

    }

    private void drawDicatelines(Canvas canvas, int i) {
        canvas.save();

        canvas.translate(mRadius + dp2px(40), 0);

        float ro = getRatioHalfSumDegrees(i);

        canvas.rotate(-ro);

        mArcPaint.setTextSize(dp2px(8));

        if ((ro > 270 && ro < 360) || (ro > 0 && ro < 90)) {

            canvas.drawLine(0, 0, dp2px(12), 0, mArcPaint);

            mArcPaint.setColor(mArcColors.get(i));

            canvas.drawText(mDescription.get(i), dp2px(13), -dp2px(4), mArcPaint);

            canvas.drawText(String.valueOf(Math.floor(mRatios.get(i) * scale * 100)) + "%", dp2px(13), dp2px(8), mArcPaint);

        } else {

            canvas.drawLine(0, 0, -dp2px(12), 0, mArcPaint);

            mArcPaint.setColor(mArcColors.get(i));

            canvas.drawText(mDescription.get(i), -dp2px(32), -dp2px(4), mArcPaint);

            canvas.drawText(String.valueOf(Math.floor(mRatios.get(i) * scale * 100)) + "%", -dp2px(32), dp2px(8), mArcPaint);

        }

        canvas.restore();
    }

    private float getRatioSum(int j) {
        float sum = 0;
        for (int i = 0; i < j; i++) {
            sum += mRatios.get(i);
        }
        return sum;
    }


    private float getRatioHalfSumDegrees(int j) {
        float sum = getRatioSum(j);
        sum += (mRatios.get(j) / 2);
        return sum * 360;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (pieClickListener != null) {
            double k = (event.getY() - mWidth / 2) / (event.getX() - mWidth / 2);
            int angle = 0;
            if (event.getX() > mWidth / 2 && event.getY() > mWidth / 2) {
                angle = (int) Math.toDegrees(Math.atan(k));
            } else if (event.getX() < mWidth / 2 && event.getY() > mWidth / 2 || (event.getX() < mWidth / 2 && event.getY() < mWidth / 2)) {
                angle = 180 + (int) Math.toDegrees(Math.atan(k));
            } else if (event.getX() > mWidth / 2 && event.getY() < mWidth / 2) {
                angle = 360 + (int) Math.toDegrees(Math.atan(k));
            }
            for (int i = 1; i < mRatios.size() + 1; i++) {
                if (angle < getRatioSum(i) * 360 && angle > getRatioSum(i - 1) * 360) {
                    pieClickListener.perPieClick(mRatios.get(i - 1), i);
                }
            }
        } else if (canClickAnimation) {
            if (canClickAnimation) {
                animator.start();
            }
        }

        return false;
    }

    public void setPieClickListener(PieClickListener pieClickListener) {
        this.pieClickListener = pieClickListener;
    }

    private void setRatiosData(List<Float> data) {
        this.mRatios = data;
    }

    private void setRatiosColors(List<Integer> colors) {
        this.mArcColors = colors;
    }

    private void setRatiosDescriptions(List<String> descriptions) {
        this.mDescription = descriptions;
    }


    public void setBackColor(int backColor) {
        this.defaultBackColor = backColor;
    }


    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }


    public void setDatas(List<Float> data,
                         List<Integer> colors,
                         List<String> descriptions) {
        if (data != null && data.size() > 0
                && colors != null && colors.size() > 0
                && descriptions != null && descriptions.size() > 0) {
            setRatiosData(data);
            setRatiosColors(colors);
            setRatiosDescriptions(descriptions);
            animator.start();
        }
    }


    public interface PieClickListener {

        void perPieClick(Float ratio, Integer i);
    }
}
