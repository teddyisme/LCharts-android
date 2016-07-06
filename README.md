#LCharts
###综合了几种常用图表的绘制，目前支持饼状图，柱状图，雷达图。之后会慢慢更新，支持更多图表。
-![图表](https://github.com/teddyisme/LCharts/blob/master/gifs/c.gif "录屏")
####项目可以随便用，随便改，只要star一下让我知道有多少人在用就可以了。
--
##简单使用：
    compile 'com.lixs.charts:charts:1.0.0'
    也可以下载代码进行依赖
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
 ```
--
不管是有需求还是批评指教都欢迎来沟通交流：QQ群:462697846
