package cn.hg.huobikline;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


/**
 * K线图
 * Created By 黄干 2018-9-27
 */
public class KlineView extends FrameLayout implements OnChartValueSelectedListener {

    private TextView tv_ma5,tv_ma10,tv_ma30;
    private CombinedChart combinedChart;


    private int color_ma5,color_ma10,color_ma30;


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

        setData();

    }

    //设置CombinedChart参数
    private void setCombineChart() {
        combinedChart.setScaleYEnabled(false); //禁止Y轴方向放大
        combinedChart.setAutoScaleMinMaxEnabled(true); //?
        combinedChart.setOnChartValueSelectedListener(this); //图表点击事件
        combinedChart.setMinOffset(5f);
    }

    //设置XAxis参数
    private void setXAxis(){

    }

    //设置YAxis参数
    private void setYAxis(){

    }

    //设置数据参数
    private void setData(){

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
}
