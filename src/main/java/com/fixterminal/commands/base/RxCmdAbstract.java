package com.fixterminal.commands.base;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class RxCmdAbstract {

   RxCommandsEnum cmdId;
   public abstract void execute() throws Exception;
//   public void execute(List<RxCmdParameter> cmdParameters) throws Exception
//   {
//      //TODO trow exception
//      System.out.println("ERROR tutaj parametry");
//   }


}
