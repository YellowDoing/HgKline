package cn.hg.huobikline;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class DataTransferHelper {


    public static List<Entry> transfer2Entry(List<CandleEntry> candleEntries){
        List<Entry> entries =new ArrayList<>();
        int j = candleEntries.size();
        for (int i = 0; i < j; i++) {
            CandleEntry candleEntry = candleEntries.get(i);
            entries.add(new Entry(candleEntry.getX(),candleEntry.getY()));
        }
        return entries;
    }


    public static List<BarEntry> transfer2BarEntry(List<CandleEntry> candleEntries){
        List<BarEntry> entries =new ArrayList<>();
        int j = candleEntries.size();
        for (int i = 0; i < j; i++) {
            CandleEntry candleEntry = candleEntries.get(i);
            entries.add(new BarEntry(candleEntry.getX(), candleEntry.getY()));
        }
        return entries;
    }

}
