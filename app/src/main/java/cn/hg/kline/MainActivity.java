package cn.hg.kline;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hg.huobikline.KlineView;

public class MainActivity extends AppCompatActivity{


    private KlineView mKlineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mKlineView = findViewById(R.id.k_line_view);

        List<Data> datas = new Gson().fromJson(JsonReader.getJson("data.json", this), new TypeToken<List<Data>>() {
        }.getType());
        List<CandleEntry> candleEntries = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            Data data = datas.get(i);
            candleEntries.add(new CandleEntry(i, data.getHigh(), data.getLow(), data.getOpen(), data.getClose()));
        }
        mKlineView.setMinuteData(candleEntries);
    }


    //long转换类
    private String transferLongToDate(Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(millSec);
        return sdf.format(date);
    }
}
