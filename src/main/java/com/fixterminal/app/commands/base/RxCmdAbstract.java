package com.fixterminal.app.commands.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class RxCmdAbstract {

   RxCommandEnum cmdId;
   public abstract void execute() throws Exception;
//   public void execute(List<RxCmdParameter> cmdParameters) throws Exception
//   {
//      //TODO trow exception
//      System.out.println("ERROR tutaj parametry");
//   }


}
