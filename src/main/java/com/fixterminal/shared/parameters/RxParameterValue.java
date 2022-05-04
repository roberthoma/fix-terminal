package com.fixterminal.shared.parameters;

import lombok.Getter;
import lombok.Setter;

public  class RxParameterValue<T>  {
   @Getter
   protected T value;
   @Getter
   @Setter
   T defaultValue;



   public RxParameterValue(T defaultValue ){
       this.defaultValue = defaultValue;
       this.value = defaultValue;
   }


    public void setValue(Object value) {
        try {
            this.value = (T)value;
        }
        catch (Exception e){
            //TODO Obsługa błedów
            System.out.println(">>>>>>ERROR>>>>>>>");
            e.printStackTrace();
            System.out.println("<<<<<<<<<<<<>>>>>>>>>>>>>");

        }
    }

}
