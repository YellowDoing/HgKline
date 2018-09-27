package cn.hg.huobikline;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
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

    private TextView tv_ma5,tv_ma10,tv_ma30;
    private CombinedChart combinedChart;
    private List<CandleEntry> candleEntries = new ArrayList<>();
    private List<Entry> ma10Entries = new ArrayList<>();
    private List<Entry> ma30Entries = new ArrayList<>();
    private List<Entry> ma5Entries = new ArrayList<>();

    private boolean isDrawValue = false;
    private float maLineWidth,maTextSize;
    private int increasingColor,decreasingColor,neutralColor;
    private int color_ma5,color_ma10,color_ma30,yAxisTextColor;



    public KlineView(@NonNull Context context) {
        super(context);
        init(null);
    }

    public KlineView(@NonNull Context context,  @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public KlineView(@NonNull Context context,@Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){

        LayoutInflater.from(getContext()).inflate(R.layout.view_kline,this,true);

        tv_ma5 = findViewById(R.id.tv_ma5);
        tv_ma10 = findViewById(R.id.tv_ma10);
        tv_ma30 = findViewById(R.id.tv_ma30);
        combinedChart = findViewById(R.id.chart);

        initParam(attrs);

        setCombineChart();

        setXAxis();

        setYAxis();

        setParam();
    }

    private void setParam() {
        tv_ma5.setTextColor(color_ma5);
        tv_ma10.setTextColor(color_ma10);
        tv_ma30.setTextColor(color_ma30);

        tv_ma5.setTextSize(maTextSize);
        tv_ma10.setTextSize(maTextSize);
        tv_ma30.setTextSize(maTextSize);
    }

    //设置CombinedChart参数
    private void setCombineChart() {
        combinedChart.setScaleYEnabled(false); //禁止Y轴方向放大
        combinedChart.setAutoScaleMinMaxEnabled(true); //?
        combinedChart.setOnChartValueSelectedListener(this); //图表点击事件
        combinedChart.setMinOffset(5f); //设置边距
    }

    //设置XAxis参数
    private void setXAxis(){
        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setDrawAxisLine(false); //不画线
        xAxis.setDrawLabels(false); //不画字
        xAxis.setDrawGridLines(false); //不画网格
    }

    //设置YAxis参数
    private void setYAxis(){
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

        yAxisRight.setTextColor(yAxisTextColor);
    }

    /**
     * 设置分时图
     */
    public void  setMinuteDta(){

    }

    public void setData(List<CandleEntry> data){
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
            ma30Entries.add(new Entry(i, (
                    candleEntries.get(i - 29).getClose() +
                            candleEntries.get(i - 28).getClose() +
                            candleEntries.get(i - 27).getClose() +
                            candleEntries.get(i - 26).getClose() +
                            candleEntries.get(i - 25).getClose() +
                            candleEntries.get(i - 24).getClose() +
                            candleEntries.get(i - 23).getClose() +
                            candleEntries.get(i - 22).getClose() +
                            candleEntries.get(i - 21).getClose() +
                            candleEntries.get(i - 20).getClose() +
                            candleEntries.get(i - 19).getClose() +
                            candleEntries.get(i - 18).getClose() +
                            candleEntries.get(i - 17).getClose() +
                            candleEntries.get(i - 16).getClose() +
                            candleEntries.get(i - 15).getClose() +
                            candleEntries.get(i - 14).getClose() +
                            candleEntries.get(i - 13).getClose() +
                            candleEntries.get(i - 12).getClose() +
                            candleEntries.get(i - 11).getClose() +
                            candleEntries.get(i - 10).getClose() +
                            candleEntries.get(i - 9).getClose() +
                            candleEntries.get(i - 8).getClose() +
                            candleEntries.get(i - 7).getClose() +
                            candleEntries.get(i - 6).getClose() +
                            candleEntries.get(i - 5).getClose() +
                            candleEntries.get(i - 4).getClose() +
                            candleEntries.get(i - 3).getClose() +
                            candleEntries.get(i - 2).getClose() +
                            candleEntries.get(i - 1).getClose() +
                            candleEntries.get(i).getClose()) / 30f));
        }
    }

    private void addMA10Data(int i) {
        if (i > 9) {
            ma10Entries.add(new Entry(i, (candleEntries.get(i - 9).getClose() +
                    candleEntries.get(i - 8).getClose() +
                    candleEntries.get(i - 7).getClose() +
                    candleEntries.get(i - 6).getClose() +
                    candleEntries.get(i - 5).getClose() +
                    candleEntries.get(i - 4).getClose() +
                    candleEntries.get(i - 3).getClose() +
                    candleEntries.get(i - 2).getClose() +
                    candleEntries.get(i - 1).getClose() +
                    candleEntries.get(i).getClose()) / 10f));
        }
    }

    private void addMA5Data(int i) {
        if (i > 3) { ma5Entries.add(new Entry(i, (candleEntries.get(i - 4).getClose() +
                    candleEntries.get(i - 3).getClose() +
                    candleEntries.get(i - 2).getClose() +
                    candleEntries.get(i - 1).getClose() +
                    candleEntries.get(i).getClose()) / 5f)); }
    }

    //初始化变量
    private void initParam(AttributeSet attrs) {
        if (attrs != null){


        }
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
