package com.fixterminal.market.business.parameters;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxOnOff;
import com.fixterminal.shared.enumerators.RxOrderType;
import com.fixterminal.shared.parameters.RxParameterValue;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;



@Service
public class RxTradeParametersFactory {


  public static RxTradeParameters create(RxInstrument instrument){
      RxTradeParameters tradeParameters = new RxTradeParameters(instrument);
      Properties prop = new Properties();
//
      String fileName =  "config/eurusd.properties";
      InputStream is = null;
      try{
          is = new FileInputStream(fileName);
       } catch (
    FileNotFoundException ex) {
            ex.printStackTrace();
        }

      try {
          prop.load(is);
      }
      catch (Exception e){
          System.out.println( e);
      }

      tradeParameters.setParameterValue(RxTradeParameter.AUTO_TRADE,
              RxOnOff.valueOf(prop.getProperty("AUTO_TRADE", RxOnOff.OFF.toString())));

      tradeParameters.setParameterValue(RxTradeParameter.BREAKEVEN_ACTIVATE_DIST,
              Double.valueOf(  prop.getProperty("BREAKEVEN_ACTIVATE_DIST")));

      tradeParameters.setParameterValue(RxTradeParameter.STOP_LOSS_DISTANCE,
              Double.valueOf(  prop.getProperty("STOP_LOSS_DISTANCE")));

      tradeParameters.setParameterValue(RxTradeParameter.QUANTITY,
              Double.valueOf(  prop.getProperty("QUANTITY")));

      tradeParameters.setParameterValue(RxTradeParameter.BREAKEVEN_PROFIT,
              Double.valueOf(  prop.getProperty("BREAKEVEN_PROFIT")));



      return tradeParameters;
  }


}
