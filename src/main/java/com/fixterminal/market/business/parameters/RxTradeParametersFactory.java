package com.fixterminal.market.business.parameters;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxOnOff;
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
      String fileName =  "config/"+instrument.getConfigFileName();
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

      tradeParameters.setParameterValue(RxTradeParameterType.AUTO_TRADE,
              RxOnOff.valueOf(prop.getProperty("AUTO_TRADE", RxOnOff.OFF.toString())));

      tradeParameters.setParameterValue(RxTradeParameterType.AUTO_BREAKEVEN,
              RxOnOff.valueOf(prop.getProperty("AUTO_BREAKEVEN", RxOnOff.OFF.toString())));

      tradeParameters.setParameterValue(RxTradeParameterType.AUTO_TRAILING,
              RxOnOff.valueOf(prop.getProperty("AUTO_TRAILING", RxOnOff.OFF.toString())));


      tradeParameters.setParameterValue(RxTradeParameterType.BREAKEVEN_ACTIVATE_DIST,
              Double.valueOf(  prop.getProperty("BREAKEVEN_ACTIVATE_DIST")));

      tradeParameters.setParameterValue(RxTradeParameterType.STOP_LOSS_DISTANCE,
              Double.valueOf(  prop.getProperty("STOP_LOSS_DISTANCE")));

      tradeParameters.setParameterValue(RxTradeParameterType.QUANTITY,
              Double.valueOf(  prop.getProperty("QUANTITY")));

      tradeParameters.setParameterValue(RxTradeParameterType.BREAKEVEN_PROFIT,
              Double.valueOf(  prop.getProperty("BREAKEVEN_PROFIT")));

      tradeParameters.setParameterValue(RxTradeParameterType.TRAILING_STOP_ACTIVATE_DIST,
              Double.valueOf(  prop.getProperty("TRAILING_STOP_ACTIVATE_DIST")));

      tradeParameters.setParameterValue(RxTradeParameterType.TRAILING_STOP_FOLLOW_DIST,
              Double.valueOf(  prop.getProperty("TRAILING_STOP_FOLLOW_DIST")));



      return tradeParameters;
  }


}
