package cn.hg.kline;

public class Data {


    /**
     * symbol : BTCUSDT
     * Date : 1537673400000
     * High : 46066.7841782949
     * Open : 46027.680239777
     * Low : 45960.27715476
     * Close : 46041.3850994175
     * vol : 295.75119148
     */

    private String symbol;
    private long Date;
    private float High;
    private float Open;
    private float Low;
    private float Close;
    private float vol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getDate() {
        return Date;
    }

    public void setDate(long date) {
        Date = date;
    }

    public float getHigh() {
        return High;
    }

    public void setHigh(float high) {
        High = high;
    }

    public float getOpen() {
        return Open;
    }

    public void setOpen(float open) {
        Open = open;
    }

    public float getLow() {
        return Low;
    }

    public void setLow(float low) {
        Low = low;
    }

    public float getClose() {
        return Close;
    }

    public void setClose(float close) {
        Close = close;
    }

    public float getVol() {
        return vol;
    }

    public void setVol(float vol) {
        this.vol = vol;
    }

    @Override
    public String toString() {
        return "Data{" +
                "symbol='" + symbol + '\'' +
                ", Date=" + Date +
                ", High=" + High +
                ", Open=" + Open +
                ", Low=" + Low +
                ", Close=" + Close +
                ", vol=" + vol +
                '}';
    }
}
