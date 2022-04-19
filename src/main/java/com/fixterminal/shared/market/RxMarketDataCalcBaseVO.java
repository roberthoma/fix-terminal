package com.fixterminal.shared.market;

import java.time.LocalDateTime;

public class RxMarketDataCalcBaseVO {


    public LocalDateTime dateTime;

    public double offerBestPrice =0;
    public double bidBestPrice =0;

    public double spread=0;

    public int bidCounter = 0;
    public int offerCounter = 0;

    public double bidSize = 0;
    public double offerSize = 0;

    public double bidRange = 0;
    public double offerRange = 0;

    public void reset(){
        dateTime = null;
        offerBestPrice =0;
        bidBestPrice =0;
        spread=0;
        bidCounter = 0;
        offerCounter = 0;
        bidSize = 0;
        offerSize = 0;
        bidRange = 0;
        offerRange = 0;

    }

    @Override
    public String toString() {
        return "------------------------------" +"\n"+
                "  dateTime=" + dateTime + "\n"+
                ", offerBestPrice=" + offerBestPrice + "\n"+
                ", bidBestPrice=" + bidBestPrice + "\n"+
                ", spread=" + spread +  "\n"+
                ", bidCounter=" + bidCounter + "\n"+
                ", offerCounter=" + offerCounter + "\n"+
                ", bidSize=" + bidSize + "\n"+
                ", offerSize=" + offerSize + "\n"+
                ", bidRange=" + bidRange + "\n"+
                ", offerRange=" + offerRange +"\n"+
                "---------------------------------";
    }
}

