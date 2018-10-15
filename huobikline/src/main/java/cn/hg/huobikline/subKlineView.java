package cn.hg.huobikline;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 副图 5种图形模式
 * vol 成交量指标
 * macd 异同移动平均线
 * kdj 随机指标
 * rsi 相对强弱指标
 * wr  威廉指标
 */
public class subKlineView extends FrameLayout implements OnChartValueSelectedListener {

    private final int MODE_VOL = 1;
    private final int MODE_MACD = 2;
    private final int MODE_KDJ = 3;
    private final int MODE_RSI = 4;
    private final int MODE_WR = 5;

    private List<CandleEntry> datas = new ArrayList<>();

    private int current_mode;
    private TextView text_1,text_2,text_3,text_4;
    private CombinedChart mCombinedChart;


    public subKlineView(@NonNull Context context) {
        super(context);
        init();
    }

    public subKlineView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public subKlineView( @NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_kline_sub,this,false);
        mCombinedChart = findViewById(R.id.chart);

        text_1 = findViewById(R.id.text_1);
        text_2 = findViewById(R.id.text_2);
        text_3 = findViewById(R.id.text_3);
        text_4 = findViewById(R.id.text_4);

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        switch (current_mode){
            case MODE_VOL:

                break;
        }
    }

    @Override
    public void onNothingSelected() {

    }

    public void setVolData(List<CandleEntry> datas){
        this.datas = datas;
        current_mode = MODE_VOL;
    }

    public void setMacdData(List<CandleEntry> datas){
        this.datas = datas;
        current_mode = MODE_MACD;
    }

    public void setKdjData(List<CandleEntry> datas){
        this.datas = datas;
        current_mode = MODE_KDJ;
    }

    public void setRsiData(List<CandleEntry> datas){
        this.datas = datas;
        current_mode = MODE_RSI;
    }

    public void setWrData(List<CandleEntry> datas){
        this.datas = datas;
        current_mode = MODE_WR;
    }

}
