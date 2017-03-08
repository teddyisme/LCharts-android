#LCharts
###综合了几种常用图表的绘制，目前支持饼状图，柱状图，雷达图,折线图。之后会慢慢更新，支持更多图表。
-![图表](https://github.com/teddyisme/LCharts/blob/master/gifs/c.gif "录屏")
![mahua](https://github.com/teddyisme/LCharts-android/blob/master/gifs/line.png)
##简单使用：
    compile 'com.lixs.charts:charts:1.0.1'
    也可以下载代码进行依赖
    
##更新日志:
   ###1.0.1  
            添加折线图
            支持柱状图和折线图更多数据的左右拖拽
            
   ###1.0.2###
            添加饼状图区域点击
            
   ```javascript
            <com.lixs.charts.PieChartView
                android:id="@+id/pieView"
                android:layout_width="300dp"
                android:layout_height="300dp"
                app:circleStrokeWidth='2dp'
                app:backColor="#ffd9d9d9"/>


            <com.lixs.charts.BarChartView
                android:id="@+id/barView"
                android:layout_width="350dp"
                android:layout_height="300dp"
                app:titleTextSize="20dp"
                app:labelTextSize="10dp"
                app:title="柱状图示例1"/>

            <com.lixs.charts.RadarChartView
                android:id="@+id/radarView"
                android:layout_width="300dp"
                android:layout_height="300dp"
                app:itemTextColor="@color/colorAccent"
                app:itemTextSize="16dp"
                app:dataBackColor="@color/t_blue"
                app:polygonNumber="8"
                app:classNumber="3"/>
                
            <com.lixs.charts.LineChartView
                android:id="@+id/lineView"
                android:layout_width="350dp"
                android:layout_height="300dp"
                app:labelTextSize="10dp"
                app:title="折线图示例1"
                app:titleTextSize="20dp" />
                
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
    ```

### 项目可以随便用，随便改，只要star一下让我知道有多少人在用就可以了。
