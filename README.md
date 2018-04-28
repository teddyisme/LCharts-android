 ## LCharts 

 综合了几种常用图表的绘制，目前支持饼状图，柱状图，雷达图,折线图。之后会慢慢更新，支持更多图表。
-![图表](https://github.com/teddyisme/LCharts/blob/master/gifs/git.gif "录屏" )


### 导入：
```
  implementation 'com.lixs.charts:charts:1.0.4'
```
 >   也可以下载代码进行依赖


### 更新日志:

```
1.0.1
     添加折线图
     支持柱状图和折线图更多数据的左右拖拽
1.0.2
     添加饼状图区域点击
1.0.4
    优化柱状图和折线图
1.0.5
    优化柱状图
```

 #### 简单使用:
 ```

            <com.lixs.charts.PieChartView
                android:id="@+id/pieView"
                android:layout_width="300dp"
                android:layout_height="300dp"
                app:circleStrokeWidth='2dp'
                app:backColor="#ffd9d9d9"/>

            <com.lixs.charts.BarChart.LBarChartView
                android:id="@+id/frameNewBase"
                android:layout_width="350dp"
                android:layout_height="300dp"
                app:barShowNumber="6"
                app:borderColor="@color/colorAccent"
                app:bottomTextSpace="20dp"
                app:dataTextColor="@color/colorPrimaryDark"
                app:dataTextSize="12sp"
                app:descriptionTextColor="@color/colorPrimary"
                app:descriptionTextSize="20sp"
                app:isClickAnimation="true"
                app:labelTextColor="@color/colorPrimary"
                app:labelTextSize="10sp"
                app:leftTextSpace="30dp"
                app:title="柱状图实例"
                app:titleTextColor="@color/colorPrimaryDark"
                app:titleTextSize="20sp"
                app:topTextSpace="50dp" />

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
#### 柱状图
> 属性

attrs | decription
---|---
barShowNumber | 显示的数目
borderColor | 边框颜色
bottomTextSpace | 底部距离
dataTextColor | 柱子上边的字体颜色
dataTextSize | 柱子上边字体大小
descriptionTextColor | 描述文字颜色
descriptionTextSize | 描述文字大小
isClickAnimation | 是否点击动画
labelTextColor | 左边栏文字颜色
 labelTextSize | 左边栏字体大小
 leftTextSpace | 左边距离
 topTextSpace | 顶部距离
 title | 柱状图标题
 titleTextColor | 标题文字颜色
 titleTextSize | 标题文字大小




 ##### 因为工作原因，这个项目停了很长时间。最近有同学在使用学习，故又重新优化代码和效果。新建技术讨论QQ群：776344631

