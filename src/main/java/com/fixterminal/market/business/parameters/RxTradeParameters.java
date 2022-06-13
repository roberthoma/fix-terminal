package com.fixterminal.market.business.parameters;

import com.fixterminal.shared.parameters.RxParameterValue;
import com.fixterminal.shared.parameters.RxParemetersMap;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxOnOff;
import com.fixterminal.shared.enumerators.RxOrderType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

//TODO klasa abstracyjan z parametrami i opcjami
//TODO Metoda copy kopiowanie paramerów do nowej instancji
//up
//down
//set_value
//default value
//current_value
//
//
//Ponadto parametry zapisywane pod konkretny instrumen
// marametry mogą być manippulowane przez menagera i manulanie przez tradera(osobe fizyczną)

@Setter
@Getter
public class RxTradeParameters {

    private RxParemetersMap parameterMap = new RxParemetersMap();



    public RxTradeParameters(RxInstrument instrument) {
        // this.tif        = RxOrderTimeInForce.DAY;
        initParams(instrument); // TYMCZASOWE
    }


    private void initParams(RxInstrument instrument) {

        parameterMap.put(RxTradeParameterType.INSTRUMENT, new RxParameterValue<RxInstrument>(instrument));
        parameterMap.put(RxTradeParameterType.MARKET_DATA_REQUEST, new RxParameterValue<RxOnOff>(RxOnOff.OFF));
        parameterMap.put(RxTradeParameterType.AUTO_TRADE, new RxParameterValue<RxOnOff>(RxOnOff.OFF));
        parameterMap.put(RxTradeParameterType.AUTO_BREAKEVEN, new RxParameterValue<RxOnOff>(RxOnOff.OFF));
        parameterMap.put(RxTradeParameterType.AUTO_TRAILING, new RxParameterValue<RxOnOff>(RxOnOff.OFF));

        parameterMap.put(RxTradeParameterType.BREAKEVEN_ACTIVATE_DIST, new RxParameterValue<Double>(0.0));
        parameterMap.put(RxTradeParameterType.ORDER_TYPE, new RxParameterValue<RxOrderType>(RxOrderType.MARKET));
        parameterMap.put(RxTradeParameterType.STOP_LOSS_DISTANCE, new RxParameterValue<Double>(0.0));
        parameterMap.put(RxTradeParameterType.QUANTITY, new RxParameterValue<Double>(0.0));
        parameterMap.put(RxTradeParameterType.BREAKEVEN_PROFIT, new RxParameterValue<Double>(0.0));
        parameterMap.put(RxTradeParameterType.TRAILING_STOP_ACTIVATE_DIST, new RxParameterValue<Double>(0.0));
        parameterMap.put(RxTradeParameterType.TRAILING_STOP_FOLLOW_DIST, new RxParameterValue<Double>(0.0));

    }

    //TODO  zmienne poprawiać lub umożliwić ustawianie na poziomie sesji 

    private void printValues() {


    }


    public RxInstrument getInstrument(){
        return   (RxInstrument) (parameterMap.get(RxTradeParameterType.INSTRUMENT)).getValue();
    }

    public boolean isMarketDataRequest(){
        return   RxOnOff.ON.equals((parameterMap.get(RxTradeParameterType.MARKET_DATA_REQUEST)).getValue());
    }

    public void setParameterValue(RxTradeParameterType tradeParameter, Object value) {
        parameterMap.get(tradeParameter).setValue(value);
    }

    public void setParameterDefaultValue() {

    }

    String paramsStr = "";

    private void setParamStr(String param) {
        paramsStr += param + "\n";
    }


    public Double getQuantity() {
        return   (Double) (parameterMap.get(RxTradeParameterType.QUANTITY)).getValue();
    }

    public RxOrderType getType() {
        return (RxOrderType) (parameterMap.get(RxTradeParameterType.ORDER_TYPE)).getValue();
    }

    public Double getStopLossDistance() {
        return   (Double) (parameterMap.get(RxTradeParameterType.STOP_LOSS_DISTANCE)).getValue();
    }

    public Double getBreakeventProfit() {
        return   (Double) (parameterMap.get(RxTradeParameterType.BREAKEVEN_PROFIT)).getValue();

    }

    public Double getBreakeventActivateDistance() {
       return   (Double) (parameterMap.get(RxTradeParameterType.BREAKEVEN_ACTIVATE_DIST)).getValue();
    }

    public void setAutoTradeOff() {
        parameterMap.get(RxTradeParameterType.AUTO_TRADE).setValue(RxOnOff.OFF);
    }

    public void setAutoTradeOn() {
        parameterMap.get(RxTradeParameterType.AUTO_TRADE).setValue(RxOnOff.ON);
    }

    public boolean isAutoTradeOn() {
        return   RxOnOff.ON.equals((parameterMap.get(RxTradeParameterType.AUTO_TRADE)).getValue());
    }



    public Map<String, String> toStringMap() {
        return parameterMap.toStringMap();
    }

    public boolean isBreakevenOn() {
        return   RxOnOff.ON.equals((parameterMap.get(RxTradeParameterType.AUTO_BREAKEVEN)).getValue());
    }

    public boolean isTrailingStopOn() {
        return   RxOnOff.ON.equals((parameterMap.get(RxTradeParameterType.AUTO_TRAILING)).getValue());
    }

    public Double getTrailingStopActivateDistance() {
        return   (Double) (parameterMap.get(RxTradeParameterType.TRAILING_STOP_ACTIVATE_DIST)).getValue();
    }
    public Double getTrailingStopFollowDistance() {
        return   (Double) (parameterMap.get(RxTradeParameterType.TRAILING_STOP_FOLLOW_DIST)).getValue();
    }


    @Override
    public String toString() {
        paramsStr = "";
        parameterMap.forEach((parameter, rxParameterValue) -> setParamStr(parameter.getSymbol()
                + rxParameterValue.getValue()
        ));
        return paramsStr;
    }

}