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

    @Getter
    RxInstrument instrument;


    public RxTradeParameters(RxInstrument instrument) {
        // this.tif        = RxOrderTimeInForce.DAY;
        this.instrument = instrument;
        initParams(); // TYMCZASOWE
    }


    private void initParams() {

        parameterMap.put(RxTradeParameter.AUTO_TRADE, new RxParameterValue<RxOnOff>(RxOnOff.OFF));
        parameterMap.put(RxTradeParameter.AUTO_BREAKEVEN, new RxParameterValue<RxOnOff>(RxOnOff.OFF));
        parameterMap.put(RxTradeParameter.BREAKEVEN_ACTIVATE_DIST, new RxParameterValue<Double>(0.0));
        parameterMap.put(RxTradeParameter.ORDER_TYPE, new RxParameterValue<RxOrderType>(RxOrderType.MARKET));
        parameterMap.put(RxTradeParameter.STOP_LOSS_DISTANCE, new RxParameterValue<Double>(0.0));
        parameterMap.put(RxTradeParameter.QUANTITY, new RxParameterValue<Double>(0.0));
        parameterMap.put(RxTradeParameter.BREAKEVEN_PROFIT, new RxParameterValue<Double>(0.0));

    }

    //TODO  zmienne poprawiać lub umożliwić ustawianie na poziomie sesji 

    private void printValues() {


    }


    public void setParameterValue(RxTradeParameter tradeParameter, Object value) {
        parameterMap.get(tradeParameter).setValue(value);
    }

    public void setParameterDefaultValue() {

    }

    String paramsStr = "";

    private void setParamStr(String param) {
        paramsStr += param + "\n";
    }

    @Override
    public String toString() {

//        RxParameter<?> obPar =  parameterMap.get(RxTradeParameter.AUTO_TRADE);

        paramsStr = "";
        parameterMap.forEach((parameter, rxParameterValue) -> setParamStr(parameter.getSymbol()
                + rxParameterValue.getValue()
        ));


//       List<>  parameterMap.keySet()

        return paramsStr;
    }

    public Double getQuantity() {
        return   (Double) (parameterMap.get(RxTradeParameter.QUANTITY)).getValue();
    }

    public RxOrderType getType() {
        return (RxOrderType) (parameterMap.get(RxTradeParameter.ORDER_TYPE)).getValue();
    }

    public Double getStopLossDistance() {
        return   (Double) (parameterMap.get(RxTradeParameter.STOP_LOSS_DISTANCE)).getValue();
    }

    public Double getBreakeventProfit() {
        return   (Double) (parameterMap.get(RxTradeParameter.BREAKEVEN_PROFIT)).getValue();

    }

    public Double getBreakeventActivateDistance() {
       return   (Double) (parameterMap.get(RxTradeParameter.BREAKEVEN_ACTIVATE_DIST)).getValue();
    }

    public void setAutoTradeOff() {
        parameterMap.get(RxTradeParameter.AUTO_TRADE).setValue(RxOnOff.OFF);
    }

    public void setAutoTradeOn() {
        parameterMap.get(RxTradeParameter.AUTO_TRADE).setValue(RxOnOff.ON);
    }

    public Map<String, String> toStringMap() {
        return parameterMap.toStringMap();
    }
}