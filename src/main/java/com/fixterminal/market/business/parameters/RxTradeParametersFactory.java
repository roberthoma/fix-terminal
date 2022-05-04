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

      tradeParameters.setParameterValue(RxTradeParameter.AUTO_TRADE,
              Double.valueOf(  prop.getProperty("BREAKEVEN_ACTIVATE_DIST")));



//      BREAKEVEN_ACTIVATE_DIST=0.0002
//      BREAKEVEN_PROFIT=0.00006
//      STOP_LOSS_DISTANCE=0.0005
//      QUANTITY=1000
//      MOVE_DISTANCE=1000




      return tradeParameters;
  }


}
