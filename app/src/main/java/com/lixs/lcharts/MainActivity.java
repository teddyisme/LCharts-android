package com.lixs.lcharts;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lixs.charts.BarChart.DragInerfaces;
import com.lixs.charts.BarChart.LBarChartView;
import com.lixs.charts.LineChartView;
import com.lixs.charts.PieChartView;
import com.lixs.charts.RadarChartView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int yellowColor = Color.argb(255, 253, 197, 53);
    private int greenColor = Color.argb(255, 27, 147, 76);
    private int redColor = Color.argb(255, 211, 57, 53);
    private int blueColor = Color.argb(255, 76, 139, 245);

    PieChartView pieChartView;
    RadarChartView radarChartView;
    LineChartView lineChartView;
    LBarChartView LBarChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pieChartView = (PieChartView) findViewById(R.id.pieView);
        radarChartView = (RadarChartView) findViewById(R.id.radarView);
        lineChartView = (LineChartView) findViewById(R.id.lineView);
        LBarChartView = (LBarChartView) findViewById(R.id.frameNewBase);

        initPieDatas();
        initRadarDatas();
        initLineDatas();
        initNewBarDatas();

    }

    private void initNewBarDatas() {
        List<Double> datas = new ArrayList<>();
        List<String> description = new ArrayList<>();

        datas.add(100d);
        datas.add(20d);
        datas.add(40d);
        datas.add(50d);
        datas.add(60d);
        datas.add(200d);

        datas.add(10d);
        datas.add(30d);
        datas.add(40d);
        datas.add(15d);
        datas.add(38d);
        datas.add(60d);
        datas.add(10d);

        description.add("one");
        description.add("two");
        description.add("three");
        description.add("four");
        description.add("five");
        description.add("six");
        description.add("seven");
        description.add("eight");
        description.add("eight1");
        description.add("eight2");
        description.add("eight3");
        description.add("eight4");
        description.add("eight5");

        LBarChartView.setDatas(datas, description);
        LBarChartView.setDragInerfaces(new DragInerfaces() {
            @Override
            public void onEnd() {
            }

            @Override
            public void onStart() {
            }
        });
    }

    private void initLineDatas() {
        List<Double> datas = new ArrayList<>();
        datas.add(100d);
        datas.add(20d);
        datas.add(40d);
        datas.add(50d);
        datas.add(50d);
        datas.add(60d);
        datas.add(60d);
        datas.add(80d);
        datas.add(80d);

        List<String> description = new ArrayList<>();
        description.add("one");
        description.add("two");
        description.add("three");
        description.add("four");
        description.add("five");
        description.add("six");
        description.add("six");
        description.add("six");
        description.add("six");

        lineChartView.setDatas(datas, description);
    }

    private void initRadarDatas() {
        List<Float> datas = new ArrayList<>();
        List<String> description = new ArrayList<>();

        datas.add(0.5f);
        datas.add(0.3f);
        datas.add(0.3f);
        datas.add(0.8f);
        datas.add(1f);
        datas.add(0.4f);

        description.add("one");
        description.add("two");
        description.add("three");
        description.add("four");
        description.add("five");
        description.add("six");

        //点击动画开启
        radarChartView.setCanClickAnimation(true);
        radarChartView.setDatas(datas);
        radarChartView.setPolygonNumbers(6);
        radarChartView.setDescriptions(description);
    }

    private void initPieDatas() {

        List<Float> mRatios = new ArrayList<>();

        List<String> mDescription = new ArrayList<>();

        List<Integer> mArcColors = new ArrayList<>();

        mRatios.add(0.2f);
        mRatios.add(0.3f);
        mRatios.add(0.4f);
        mRatios.add(0.1f);

        mArcColors.add(blueColor);
        mArcColors.add(redColor);
        mArcColors.add(yellowColor);
        mArcColors.add(greenColor);

        mDescription.add("描述一");
        mDescription.add("描述二");
        mDescription.add("描述三");
        mDescription.add("描述四");

        //点击动画开启
        pieChartView.setCanClickAnimation(true);
        pieChartView.setDatas(mRatios, mArcColors, mDescription);
    }


}
