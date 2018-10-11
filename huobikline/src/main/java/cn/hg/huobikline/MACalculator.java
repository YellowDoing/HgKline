package cn.hg.huobikline;

import com.github.mikephil.charting.data.CandleEntry;

import java.util.List;

class MACalculator {

    static float calculate(int j, int size, List<CandleEntry> candleEntries) {
        float result = 0;
        for (int i = 0; i < size; i++) {
            result = result + candleEntries.get(j - i).getClose();
        }
        return result / ((float) size);
    }
}
