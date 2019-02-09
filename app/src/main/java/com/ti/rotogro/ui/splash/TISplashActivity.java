package com.ti.rotogro.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.ti.rotogro.BuildConfig;
import com.ti.rotogro.R;
import com.ti.rotogro.base.BaseActivity;
import com.ti.rotogro.data.db.AppDatabase;
import com.ti.rotogro.model.VersionDetails;

public class TISplashActivity extends BaseActivity implements TISplashContract.View {

   private AppCompatActivity myAppContext;
   private TISplashPresenter myPresenter;
   int DELAY_TIME_FOR_SPLASH_SCREEN = 3000;
   AppDatabase myAppDatabase;


   @Override
   protected void onCreate( Bundle savedInstanceState ) {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_splash );

      myAppContext = TISplashActivity.this;

      myPresenter = new TISplashPresenter( this, myAppContext );
      myPresenter.attachView( this );
      myPresenter.initPresenter();

   }

   @Override
   public void viewSplashCount() {
      Handler aHoldScreen = new Handler();
      aHoldScreen.postDelayed( new Runnable() {

         public void run() {
            if( checkInternet() ) {
               myPresenter.checkAppUpdate( BuildConfig.VERSION_NAME );
            } else
               callBaseActivity();

         }
      }, DELAY_TIME_FOR_SPLASH_SCREEN );
   }

   @Override
   public void initActivity() {
      myAppDatabase = AppDatabase.getDatabase( getApplication() );
   }


   @Override
   public void appUpdate( VersionDetails versionDetails ) {
      callLocalServerFunction( versionDetails );
   }


   public void callLocalServerFunction( VersionDetails myVersionCheck ) {
      try {
         if( myVersionCheck != null ) {
            if( !myVersionCheck.getVersionName().equals( BuildConfig.VERSION_NAME ) ) {
               switch( myVersionCheck.getStatus() ) {
                  case "1":
                     showUpdateDialog( myVersionCheck.getMessage() );
                     break;
                  case "2":
                     showMandatoryUpdateDialog( myVersionCheck.getMessage() );
                     break;
                  default:
                     callBaseActivity();
                     // showUpdateDialog( myVersionCheck.getMessage() );
                     break;
               }
            } else {
               callBaseActivity();
            }
         } else {
            callBaseActivity();
         }
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   @Override
   protected void onResume() {
      super.onResume();
      //  viewSplashCount();
   }


}
