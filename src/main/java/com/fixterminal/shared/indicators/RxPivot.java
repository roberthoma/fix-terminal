package com.fixterminal.shared.indicators;


import com.fixterminal.shared.enumerators.RxPivotType;

import java.time.LocalDateTime;

public class RxPivot {


    public int id;
    public double price;
    public LocalDateTime dateTime;
    public RxPivotType type;


    //TODO Oczekiwane piwoty ...
    //TODO informacja przy jakich parametrach był wygreowany ndany pivot , mowa to o mindiff
}
