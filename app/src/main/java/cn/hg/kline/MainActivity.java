package cn.hg.kline;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.github.mikephil.charting.data.CandleEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

import cn.hg.huobikline.KlineView;
import cn.hg.huobikline.SubKlineView;


public class MainActivity extends AppCompatActivity{


    private KlineView mKlineView; //主图
    private SubKlineView mSubKlineView; //幅图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mKlineView = findViewById(R.id.k_line_view);
        mSubKlineView = findViewById(R.id.sub_view);

        //生成一组假数据
        List<Data> datas = new Gson().fromJson(JsonReader.getJson("data.json", this), new TypeToken<List<Data>>() {
        }.getType());
        final List<CandleEntry> candleEntries = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            Data data = datas.get(i);
            candleEntries.add(new CandleEntry(i, data.getHigh(), data.getLow(), data.getOpen(), data.getClose()));
        }

        mKlineView.setData(candleEntries);
        mSubKlineView.setVolData(candleEntries);

        //分时图
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKlineView.setMinuteData(candleEntries);
            }
        });

        //K线图
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKlineView.setData(candleEntries);
            }
        });

        //vol
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSubKlineView.setVolData(candleEntries);
            }
        });

        //macd
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSubKlineView.setMacdData(candleEntries);
            }
        });

        //kdj
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSubKlineView.setKdjData(candleEntries);
            }
        });

        //rsi
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSubKlineView.setRsiData(candleEntries);
            }
        });

        //wr
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSubKlineView.setWrData(candleEntries);
            }
        });

    }
}
