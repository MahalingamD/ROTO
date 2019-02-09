package com.ti.rotogro.base;

/**
 * Created by root on 17/4/17.
 */
public interface BaseView {

   void showProgress();

   void hideProgress();

   void showToast( String errorMessage );

   void showUpdateDialog( String aMessage );

   void showMandatoryUpdateDialog( String aMessage );

   void launchMarket();

   void callBaseActivity();

   boolean checkInternet();


   String getStringById( int id );

   String getStringById( int id, String param1 );

   String getStringById( int id, String param1, String param2 );

}