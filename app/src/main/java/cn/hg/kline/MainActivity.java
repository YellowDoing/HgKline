package cn.hg.kline;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnChartValueSelectedListener {


    private CombinedChart mCombinedChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCombinedChart = findViewById(R.id.chart);
        mCombinedChart.setScaleYEnabled(false); //禁止Y轴方向放大
        mCombinedChart.setAutoScaleMinMaxEnabled(true);
        mCombinedChart.setOnChartValueSelectedListener(this);
        //mCombinedChart.setScale


        XAxis xAxis = mCombinedChart.getXAxis();

        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        YAxis yAxisLeft = mCombinedChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setDrawAxisLine(false);
        yAxisLeft.setDrawLabels(false);


        YAxis yAxisRight = mCombinedChart.getAxisRight();
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

        yAxisRight.setTextColor(Color.LTGRAY);
        //yAxisRight.setD



     /* mCombinedChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return transferLongToDate((long)value);
            }
        });*/


        List<Data> datas = new Gson().fromJson(JsonReader.getJson("data.json", this), new TypeToken<List<Data>>() {
        }.getType());

        List<CandleEntry> candleEntries = new ArrayList<>();
        List<Entry> MA10 = new ArrayList<>();
        List<Entry> MA30 = new ArrayList<>();
        List<Entry> MA5 = new ArrayList<>();


        for (int i = 0; i < datas.size(); i++) {

            Log.d("Ssssssssssssssssss", datas.get(i).toString());

            Data data = datas.get(i);

            candleEntries.add(new CandleEntry(i, data.getHigh(), data.getLow(), data.getOpen(), data.getClose()));

            if (i > 3) {

                MA5.add(new Entry(i, (candleEntries.get(i - 4).getClose() +
                        candleEntries.get(i - 3).getClose() +
                        candleEntries.get(i - 2).getClose() +
                        candleEntries.get(i - 1).getClose() +
                        candleEntries.get(i).getClose()) / 5f
                ));

            }

            if (i > 9) {
                MA10.add(new Entry(i, (candleEntries.get(i - 9).getClose() +
                        candleEntries.get(i - 8).getClose() +
                        candleEntries.get(i - 7).getClose() +
                        candleEntries.get(i - 6).getClose() +
                        candleEntries.get(i - 5).getClose() +
                        candleEntries.get(i - 4).getClose() +
                        candleEntries.get(i - 3).getClose() +
                        candleEntries.get(i - 2).getClose() +
                        candleEntries.get(i - 1).getClose() +
                        candleEntries.get(i).getClose()) / 10f
                ));
            }

            if (i > 28) {
                MA30.add(new Entry(i, (
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
                                candleEntries.get(i).getClose()) / 30f
                ));

            }

        }

        CandleDataSet candleDataSet = new CandleDataSet(candleEntries, "k線圖");
        candleDataSet.setDrawValues(false);
        LineDataSet lineDataSetMA5 = new LineDataSet(MA5, "MA5");
        LineDataSet lineDataSetMA10 = new LineDataSet(MA10, "MA5");
        LineDataSet lineDataSetMA30 = new LineDataSet(MA30, "MA5");


        candleDataSet.setIncreasingColor(Color.parseColor("#03C087"));
        candleDataSet.setDecreasingColor(Color.parseColor("#E66C41"));
        candleDataSet.setNeutralColor(Color.parseColor("#03C087"));

        candleDataSet.setShadowColorSameAsCandle(true);

        lineDataSetMA5.setDrawCircles(false);
        lineDataSetMA10.setDrawCircles(false);
        lineDataSetMA30.setDrawCircles(false);
        lineDataSetMA5.setColor(Color.parseColor("#ff8f59"));
        lineDataSetMA10.setColor(Color.MAGENTA);
        lineDataSetMA5.setLineWidth(1.5f);
        lineDataSetMA10.setLineWidth(1.5f);
        lineDataSetMA30.setLineWidth(1.5f);
        lineDataSetMA5.setDrawValues(false);
        lineDataSetMA10.setDrawValues(false);
        lineDataSetMA30.setDrawValues(false);


        candleDataSet.setDrawIcons(false);
        candleDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
//        set1.setColor(Color.rgb(80, 80, 80));
        candleDataSet.setShadowWidth(1f);
        candleDataSet.setDecreasingPaintStyle(Paint.Style.FILL);
        candleDataSet.setIncreasingPaintStyle(Paint.Style.FILL);

        CandleData candleData = new CandleData(candleDataSet);

        CombinedData combinedData = new CombinedData();
        LineData lineData = new LineData(lineDataSetMA5, lineDataSetMA10, lineDataSetMA30);

        combinedData.setData(candleData);
        combinedData.setData(lineData);


        mCombinedChart.getDescription().setEnabled(false);
        mCombinedChart.getLegend().setEnabled(false);

        mCombinedChart.setData(combinedData);

        mCombinedChart.animateX(1000);

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }


    //long转换类
    private String transferLongToDate(Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(millSec);
        return sdf.format(date);
    }
}
