package cn.hg.huobikline;

import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class DataTransferHelper {


    public static List<Entry> transfer2Entry(List<CandleEntry> candleEntries){
        List<Entry> entries =new ArrayList<>();

        for (int i = 0; i < candleEntries.size(); i++) {
            CandleEntry candleEntry = candleEntries.get(i);
            entries.add(new Entry(candleEntry.getX(),candleEntry.getY()));
        }

        return entries;
    }

}
