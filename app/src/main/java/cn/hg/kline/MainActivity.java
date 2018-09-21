package cn.hg.kline;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private CombinedChart mCombinedChart,mCombinedChart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCombinedChart = findViewById(R.id.chart);
        mCombinedChart2 = findViewById(R.id.chart2);

        List<CandleEntry> candleEntries = new ArrayList<>();
        List<Entry> lineEntries2= new ArrayList<>();
        List<Entry> lineEntries3 = new ArrayList<>();
        List<Entry> lineEntries1 = new ArrayList<>();

        for (int i = 0; i < 50;i++){

            float mult = (50 + 1);
            float val = (float) (Math.random() * 40) + mult;

            float high = (float) (Math.random() * 9) + 8f;
            float low = (float) (Math.random() * 9) + 8f;

            float open = (float) (Math.random() * 6) + 1f;
            float close = (float) (Math.random() * 6) + 1f;

            boolean even = i % 2 == 0;

            candleEntries.add(new CandleEntry(
                    i, val + high,
                    val - low,
                    even ? val + open : val - open,
                    even ? val - close : val + close
            ));

            lineEntries1.add(new Entry(i,100));
            lineEntries2.add(new Entry(i,150));
            lineEntries3.add(new Entry(i,(high + low)/2));
        }

        CandleDataSet candleDataSet = new CandleDataSet(candleEntries, "k線圖");



        candleDataSet.setDrawIcons(false);
        candleDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
//        set1.setColor(Color.rgb(80, 80, 80));
        candleDataSet.setShadowColor(Color.DKGRAY);
        candleDataSet.setShadowWidth(0.7f);
        candleDataSet.setDecreasingColor(Color.RED);
        candleDataSet.setDecreasingPaintStyle(Paint.Style.FILL);
        candleDataSet.setIncreasingColor(Color.rgb(122, 242, 84));
        candleDataSet.setIncreasingPaintStyle(Paint.Style.FILL);
        candleDataSet.setNeutralColor(Color.BLUE);

        CandleData candleData = new CandleData(candleDataSet);


        CombinedData combinedData = new CombinedData();




        LineDataSet lineDataSet1 = new LineDataSet(lineEntries1,"1");
        LineDataSet lineDataSet2 = new LineDataSet(lineEntries2,"2");
        LineDataSet lineDataSet3 = new LineDataSet(lineEntries3,"3");
        LineData lineData = new LineData(lineDataSet1,lineDataSet2,lineDataSet3);


        combinedData.setData(candleData);
        combinedData.setData(lineData);

        mCombinedChart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        mCombinedChart.getDescription().setEnabled(false);
        mCombinedChart.getLegend().setEnabled(false);

        mCombinedChart.setData(combinedData);
        mCombinedChart2.setData(combinedData);

        mCombinedChart.animateX(1000);
        mCombinedChart2.animateX(1000);
        mCombinedChart2.setY

    }
}
