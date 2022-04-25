package com.fixterminal.strategies.business.parameters;

import com.fixterminal.shared.dictionaries.instruments.RxInstrument;
import com.fixterminal.shared.enumerators.RxOnOff;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;



public class RxTradeParametersFactory {

  public static RxTradeParameters create(RxInstrument instrument){
      RxTradeParameters tradeParameters = new RxTradeParameters();
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


      tradeParameters.setAUTO_TRADE(RxOnOff.valueOf(prop.getProperty("AUTO_TRADE", RxOnOff.OFF.toString())));
      tradeParameters.setAUTO_BREAKEVEN(RxOnOff.valueOf(prop.getProperty("AUTO_BREAKEVEN", RxOnOff.OFF.toString())));
      tradeParameters.setBREAKEVEN_ACTIVATE_DIST(Double.parseDouble(prop.getProperty("BREAKEVEN_ACTIVATE_DIST")));

//      BREAKEVEN_ACTIVATE_DIST=0.0002
//      BREAKEVEN_PROFIT=0.00006
//      STOP_LOSS_DISTANCE=0.0005
//      QUANTITY=1000
//      MOVE_DISTANCE=1000




      return tradeParameters;
  }


}
