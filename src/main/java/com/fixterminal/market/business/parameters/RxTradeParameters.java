package com.fixterminal.market.business.parameters;

import com.fixterminal.shared.parameters.RxParameterValue;
import com.fixterminal.shared.parameters.RxParemetersMap;
import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxOnOff;
import com.fixterminal.shared.enumerators.RxOrderType;
import lombok.Getter;
import lombok.Setter;

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

    private RxParemetersMap parameterMap =  new RxParemetersMap();

    @Getter
    RxInstrument instrument;



    public RxTradeParameters(RxInstrument instrument){
    //    this.quantity   = Double.valueOf(QUANTITY);
        //     this.type       = RxOrderType.MARKET;
       // this.tif        = RxOrderTimeInForce.DAY;
        this.instrument = instrument;
        initParams(); // TYMCZASOWE
    }


    private void initParams(){

        parameterMap.put(RxTradeParameter.AUTO_TRADE,new RxParameterValue<RxOnOff>(RxOnOff.OFF));
        parameterMap.put(RxTradeParameter.BREAKEVEN_ACTIVATE_DIST,new RxParameterValue<Double>(0.0));
        parameterMap.put(RxTradeParameter.ORDER_TYPE,new RxParameterValue<RxOrderType>(RxOrderType.MARKET));

    }

    //TODO  zmienne poprawiać lub umożliwić ustawianie na poziomie sesji 

   private void printValues(){


   }



    public void setParameterValue(RxTradeParameter tradeParameter, Object value ){
        parameterMap.get(tradeParameter).setValue(value);
    }

   public void setParameterDefaultValue(){

   }

    String paramsStr = "";

    private void setParamStr(String param){
        paramsStr += param;
    }

    @Override
    public String toString() {
        String paramsList = "";

//        RxParameter<?> obPar =  parameterMap.get(RxTradeParameter.AUTO_TRADE);

        paramsStr = "";
        parameterMap.forEach((parameter, rxParameterValue) -> setParamStr( parameter.getSymbol()
        + rxParameterValue.getValue()
         ));


//       List<>  parameterMap.keySet()

        return paramsStr;
    }

    public Double getQuantity() {
      return 1000.0;
    }

    public RxOrderType getType(){
        return  (RxOrderType) (parameterMap.get(RxTradeParameter.ORDER_TYPE)).getValue();
    }

    public Double getStopLossDistance() {
       return 0.0005;
    }

    public Double getBreakeventProfit() {
        return 0.0001;
    }

    public void setAutoTradeOff() {
         parameterMap.get(RxTradeParameter.AUTO_TRADE).setValue(RxOnOff.OFF);
    }
    public void setAutoTradeOn() {
         parameterMap.get(RxTradeParameter.AUTO_TRADE).setValue(RxOnOff.ON);
    }
}
