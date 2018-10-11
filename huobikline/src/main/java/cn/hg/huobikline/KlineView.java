package cn.hg.huobikline;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;


/**
 * K线图
 * Created By 黄干 2018-9-27
 */
public class KlineView extends FrameLayout implements OnChartValueSelectedListener {

    private boolean isMinute = false; //是否是分時圖
    private TextView tv_ma5, tv_ma10, tv_ma30;
    private CombinedChart combinedChart;
    private List<CandleEntry> candleEntries = new ArrayList<>();
    private List<CandleEntry> minuteEntries = new ArrayList<>();
    private List<Entry> ma10Entries = new ArrayList<>();
    private List<Entry> ma30Entries = new ArrayList<>();
    private List<Entry> ma5Entries = new ArrayList<>();
    private List<Entry> ma60mEntries = new ArrayList<>();

    private boolean isDrawValue = false;

    private float maLineWidth,
            maTextSize;


    private int increasingColor,
            decreasingColor,
            neutralColor,
            color_ma5,
            color_ma10,
            color_ma30,
            color_ma60m,
            yAxisTextColor;


    public KlineView(@NonNull Context context) {
        super(context);
        init();
    }

    public KlineView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KlineView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        LayoutInflater.from(getContext()).inflate(R.layout.view_kline, this, true);

        tv_ma5 = findViewById(R.id.tv_ma5);
        tv_ma10 = findViewById(R.id.tv_ma10);
        tv_ma30 = findViewById(R.id.tv_ma30);
        combinedChart = findViewById(R.id.chart);

        initParam();

        setCombineChart();

        setXAxis();

        setYAxis();
    }

    private void setParam() {
        tv_ma5.setTextColor(color_ma5);
        tv_ma10.setTextColor(color_ma10);
        tv_ma30.setTextColor(color_ma30);

        tv_ma5.setTextSize(TypedValue.COMPLEX_UNIT_SP, maTextSize);
        tv_ma10.setTextSize(TypedValue.COMPLEX_UNIT_SP, maTextSize);
        tv_ma30.setTextSize(TypedValue.COMPLEX_UNIT_SP, maTextSize);
    }

    //设置CombinedChart参数
    private void setCombineChart() {
        combinedChart.setScaleYEnabled(false); //禁止Y轴方向放大
        combinedChart.setAutoScaleMinMaxEnabled(true); //?
        combinedChart.setOnChartValueSelectedListener(this); //图表点击事件
        combinedChart.setMinOffset(5f); //设置边距
        combinedChart.setNoDataText(""); //无数据显示
        combinedChart.getLegend().setEnabled(false); //禁止顯示圖例


        combinedChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                if (isMinute) { //分時圖
                    // e.get
                } else {
                    setMaData(e);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void setMaData(Entry e) {



        int j = candleEntries.indexOf(e);
        if (j == -1) {
            int j5 = ma5Entries.indexOf(e);
            if (j5 == -1) {
                int j10 = ma10Entries.indexOf(e);
                if (j10 == -1) { //点击的ma30
                    int j30 = ma30Entries.indexOf(e);
                    tv_ma30.setText("MA30:" + e.getY());
                    tv_ma10.setText("MA10:" + ma10Entries.get(j30 + 20).getY());
                    tv_ma5.setText("MA5:" + ma5Entries.get(j30 + 25).getY());
                } else{ //点击的ma10
                    tv_ma5.setText("MA5:" + ma5Entries.get(j10+5).getY());
                    tv_ma10.setText("MA10:" + e.getY());
                    if (j10 > 19)
                        tv_ma30.setText("MA30:" + ma30Entries.get(j10 - 20
                        ).getY());
                    else
                        tv_ma30.setText("");
                }
            } else{  //点击的ma5
                tv_ma5.setText("MA5:" + e.getY());
                if (j5 > 4)
                    tv_ma10.setText("MA10:" + ma10Entries.get(j5 - 5).getY());
                else
                    tv_ma10.setText("");

                if (j5 > 24)
                    tv_ma30.setText("MA30:" + ma30Entries.get(j5 - 25).getY());
                else
                    tv_ma30.setText("");
            }
        } else { //点击的蜡烛

            if (j > 3)
                tv_ma5.setText("MA5:" + ma5Entries.get(j - 4).getY());
            else
                tv_ma5.setText("");

            if (j > 8)
                tv_ma10.setText("MA10:" + ma10Entries.get(j - 9).getY());
            else
                tv_ma10.setText("");

            if (j > 28)
                tv_ma30.setText("MA30:" + ma30Entries.get(j - 29).getY());
            else
                tv_ma30.setText("");
        }
    }

    //设置XAxis参数
    private void setXAxis() {
        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setDrawAxisLine(false); //不画线
        xAxis.setDrawLabels(false); //不画字
        xAxis.setDrawGridLines(false); //不画网格
    }

    //设置YAxis参数
    private void setYAxis() {
        YAxis yAxisLeft = combinedChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(false); //不画网格
        yAxisLeft.setDrawAxisLine(false);//不画线
        yAxisLeft.setDrawLabels(false);  //不画字

        YAxis yAxisRight = combinedChart.getAxisRight();
        yAxisRight.setDrawGridLines(false); //不画线
        yAxisRight.setDrawAxisLine(false); //不画网格
        yAxisRight.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART); //文字显示在内部
        yAxisRight.setYOffset(0f); //文字靠边
        yAxisRight.setXOffset(0f); //文字靠边
        //sadasd
        //sdfdsfse

        yAxisRight.setTextColor(yAxisTextColor);
    }

    /**
     * 设置分时图
     */
    public void setMinuteData(List<CandleEntry> data) {
        isMinute = true;
        minuteEntries = data;

        List<Entry> minutesData = DataTransferHelper.transfer2Entry(data);

        if (minuteEntries.size() > 59){
            for (int i = 59; i < minuteEntries.size(); i++) {
                CandleEntry entry = minuteEntries.get(i);
                ma60mEntries.add(new Entry(entry.getX(),MACalculator.calculate(i,60,minuteEntries)));
            }
        }

        LineDataSet dataSet = new LineDataSet(minutesData, "");
        dataSet.setColor(Color.parseColor("#66b3ff")); //折线颜色
        dataSet.setDrawFilled(true); //内部填充
        dataSet.setLineWidth(1f); //折线的宽度
        dataSet.setDrawCircles(false); //不画圆
        dataSet.setDrawValues(false); //不显示数据
        combinedChart.getDescription().setEnabled(false); //禁用描述
        combinedChart.getLegend().setEnabled(false); //禁用图例

        LineDataSet lineDataSetMA60 = new LineDataSet(ma60mEntries, "");
        lineDataSetMA60.setDrawCircles(false); //ma60不画圆
        lineDataSetMA60.setColor(color_ma60m); //ma60颜色
        lineDataSetMA60.setLineWidth(maLineWidth);//ma60线的宽度
        lineDataSetMA60.setDrawValues(false);//不显示数据



        LineData lineData = new LineData(dataSet,lineDataSetMA60);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        combinedChart.setData(combinedData);
        combinedChart.animateX(800);

    }

    public void setData(List<CandleEntry> data) {
        isMinute = false;
        setParam();

        candleEntries = data;
        ma5Entries.clear();
        ma10Entries.clear();
        ma30Entries.clear();

        for (int i = 0; i < data.size(); i++) {

            addMA5Data(i);

            addMA10Data(i);

            addMA30Data(i);
        }


        CandleDataSet candleDataSet = new CandleDataSet(candleEntries, "");
        candleDataSet.setDrawValues(isDrawValue); //是否显示值  默认false
        LineDataSet lineDataSetMA5 = new LineDataSet(ma5Entries, "");
        LineDataSet lineDataSetMA10 = new LineDataSet(ma10Entries, "");
        LineDataSet lineDataSetMA30 = new LineDataSet(ma30Entries, "");


        candleDataSet.setIncreasingColor(increasingColor);
        candleDataSet.setDecreasingColor(decreasingColor);
        candleDataSet.setNeutralColor(neutralColor);
        candleDataSet.setShadowColorSameAsCandle(true);

        //禁止画圆
        lineDataSetMA5.setDrawCircles(false);
        lineDataSetMA10.setDrawCircles(false);
        lineDataSetMA30.setDrawCircles(false);


        //平均線顏色
        lineDataSetMA5.setColor(color_ma5);
        lineDataSetMA10.setColor(color_ma10);
        lineDataSetMA30.setColor(color_ma30);


        //平均线的宽度
        lineDataSetMA5.setLineWidth(maLineWidth);
        lineDataSetMA10.setLineWidth(maLineWidth);
        lineDataSetMA30.setLineWidth(maLineWidth);

        //是否画平均线的值
        lineDataSetMA5.setDrawValues(false);
        lineDataSetMA10.setDrawValues(false);
        lineDataSetMA30.setDrawValues(false);


        candleDataSet.setDrawIcons(false);
        // TODO: 2018/9/27 Y轴
        candleDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        candleDataSet.setShadowWidth(1f);
        candleDataSet.setDecreasingPaintStyle(Paint.Style.FILL);
        candleDataSet.setIncreasingPaintStyle(Paint.Style.FILL);

        CandleData candleData = new CandleData(candleDataSet);
        CombinedData combinedData = new CombinedData();
        LineData lineData = new LineData(lineDataSetMA5, lineDataSetMA10, lineDataSetMA30);

        combinedData.setData(candleData);
        combinedData.setData(lineData);


        combinedChart.getDescription().setEnabled(false); //禁用描述
        combinedChart.getLegend().setEnabled(false); //禁用图例

        combinedChart.setData(combinedData);
        combinedChart.animateX(1000);
    }

    private void addMA30Data(int i) {
        if (i > 28) {
            ma30Entries.add(new Entry(candleEntries.get(i).getX(), MACalculator.calculate(i,30,candleEntries)));
        }
    }



    private void addMA10Data(int i) {
        if (i > 8) {
            ma10Entries.add(new Entry(candleEntries.get(i).getX(), MACalculator.calculate(i,10,candleEntries)));
        }
    }

    private void addMA5Data(int i) {
        if (i > 3) {
            ma5Entries.add(new Entry(candleEntries.get(i).getX(), MACalculator.calculate(i,5,candleEntries)));
        }
    }

    //初始化变量
    private void initParam() {

        maLineWidth = DimensUtil.px2dp(getContext(), 2);
        increasingColor = Color.parseColor("#03C087");
        decreasingColor = Color.parseColor("#E66C41");
        neutralColor = Color.parseColor("#03C087");
        color_ma5 = Color.parseColor("#F6DC93");
        color_ma10 = Color.parseColor("#60D0BF");
        color_ma30 = Color.parseColor("#CA91FC");
        color_ma60m = Color.parseColor("#F6DC93");
        yAxisTextColor = Color.parseColor("#667FA0");
        maTextSize = 10;

    }


    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    /********************* getter and setter ******************/

    public List<CandleEntry> getCandleEntries() {
        return candleEntries;
    }


    public int getColor_ma5() {
        return color_ma5;
    }

    public void setColor_ma5(int color_ma5) {
        this.color_ma5 = color_ma5;
    }

    public int getColor_ma10() {
        return color_ma10;
    }

    public void setColor_ma10(int color_ma10) {
        this.color_ma10 = color_ma10;
    }

    public int getColor_ma30() {
        return color_ma30;
    }

    public void setColor_ma30(int color_ma30) {
        this.color_ma30 = color_ma30;
    }

    public int getyAxisTextColor() {
        return yAxisTextColor;
    }

    public void setyAxisTextColor(int yAxisTextColor) {
        this.yAxisTextColor = yAxisTextColor;
    }

    public boolean isDrawValue() {
        return isDrawValue;
    }

    public void setDrawValue(boolean drawValue) {
        isDrawValue = drawValue;
    }

    public int getIncreasingColor() {
        return increasingColor;
    }

    public void setIncreasingColor(int increasingColor) {
        this.increasingColor = increasingColor;
    }

    public int getDecreasingColor() {
        return decreasingColor;
    }

    public void setDecreasingColor(int decreasingColor) {
        this.decreasingColor = decreasingColor;
    }

    public int getNeutralColor() {
        return neutralColor;
    }

    public void setNeutralColor(int neutralColor) {
        this.neutralColor = neutralColor;
    }

    public float getMaLineWidth() {
        return maLineWidth;
    }

    public void setMaLineWidth(float maLineWidth) {
        this.maLineWidth = maLineWidth;
    }

    public float getMaTextSize() {
        return maTextSize;
    }

    public void setMaTextSize(float maTextSize) {
        this.maTextSize = maTextSize;
    }
}
