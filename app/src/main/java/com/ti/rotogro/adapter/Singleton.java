package com.ti.rotogro.adapter;

public class Singleton {

   private static Singleton aSingleton;


   //private constructor.
   private Singleton() {
   }

   public static Singleton getInstance() {
      if( aSingleton == null ) { //if there is no instance available... create new one
         aSingleton = new Singleton();
      }
      return aSingleton;
   }

}
