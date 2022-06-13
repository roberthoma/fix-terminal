package com.fixterminal.market.business.trade.actions;

import com.fixterminal.market.business.parameters.RxTradeParameters;
import com.fixterminal.market.business.trade.orders.RxOrdersManager;
import com.fixterminal.shared.enumerators.RxPositionDirection;
import com.fixterminal.shared.market.RxMonitorDataVO;
import com.fixterminal.shared.orders.RxOrderEntity;
import com.fixterminal.shared.positions.RxPosition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//TODO Trailing stop dla pozycji w statusie CANCEL

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class RxActionStopLossController {


    RxActions actions;

    RxOrdersManager ordersManager;

    RxPosition position;
    RxOrderEntity orderSL;
    RxTradeParameters tradeParameters;
    RxMonitorDataVO data;

    @Autowired
    RxActionStopLossController(RxActions actions, RxOrdersManager ordersManager){
       log.info("Init : RxActionStopLossController");
       this.actions = actions;
       this.ordersManager = ordersManager;
    }


    public void controlAction(RxMonitorDataVO data, RxTradeParameters tradeParameters) {
        this.position = data.position;
        this.orderSL  = ordersManager.getStopLossOrder(tradeParameters.getInstrument());
        this.tradeParameters = tradeParameters;
        this.data = data;

        if (data.isOpenPosition()) {
            try {

                if (orderSL == null) {
                    actions.actionSetStopLoss(tradeParameters.getInstrument());  //TODO Czasami wyprzedzam raport i tworzą się dwa zlecenia. SL :(
                } else {
                    checkStopLossQuantity();
                    checkBreakeven();
                    checkTrailingStop();
                }

            //TODO ELESE jeżeli niema otw pozycji . to sprawdź i usuń istniejące StopLoss

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void checkTrailingStop() {
       double newTrailingSLPrice;
       if (!tradeParameters.isTrailingStopOn()){
          return;
       }

       if (position.getMarketDistance() > tradeParameters.getTrailingStopActivateDistance()){

           if( position.getDirection().compareTo(RxPositionDirection.LONG)==0){
              newTrailingSLPrice = data.marketDataCalcBaseVO.bidBestPrice  - tradeParameters.getTrailingStopFollowDistance();
              if (newTrailingSLPrice > orderSL.getPrice())
              {
                actions.updateStopLossPrice(orderSL, newTrailingSLPrice);
              }
               return;
           }

           if( position.getDirection().compareTo(RxPositionDirection.SHORT)==0){
               newTrailingSLPrice = data.marketDataCalcBaseVO.offerBestPrice + tradeParameters.getTrailingStopFollowDistance();
               if (newTrailingSLPrice < orderSL.getPrice())
               {
                   actions.updateStopLossPrice(orderSL, newTrailingSLPrice);
               }
               return;
           }

           System.out.println( ">>>>>>>> BAD or not set position direction");

       }

    }

    private void checkStopLossQuantity() {
        if (orderSL.getQuantity().compareTo(data.position.getQuantity()) != 0) {
            System.out.println(">>>       StopLoss other QUANTITY >>>> ");
            System.out.println(">>> position.getQuantity()=" + position.getQuantity());
            System.out.println(">>> orderSL.getQuantityOrdered()=" + orderSL.getQuantity());
            actions.updateStopLossQuantity(orderSL, position.getQuantity());
        }
    }

    private void checkBreakeven() {
        if (   !isStopLossProfitable()
                && tradeParameters.isBreakevenOn())
        {
            if (position.getMarketDistance() > tradeParameters.getBreakeventActivateDistance()
            ) {
                actions.updateStopLossToBreakevent(data, tradeParameters);
            }
        }

    }

    private boolean isStopLossProfitable() {
        if (RxPositionDirection.LONG.compareTo( position.getDirection())==0){
            return orderSL.getPrice() >= position.getEntryPrice();
        }
        return position.getEntryPrice()  >= orderSL.getPrice();
    }
}
