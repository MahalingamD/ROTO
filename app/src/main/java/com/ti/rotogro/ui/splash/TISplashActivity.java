package com.ti.rotogro.ui.splash;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ti.rotogro.BuildConfig;
import com.ti.rotogro.R;
import com.ti.rotogro.base.BaseActivity;
import com.ti.rotogro.data.db.AppDatabase;
import com.ti.rotogro.model.VersionDetails;
import com.ti.rotogro.preference.TIPrefs;
import com.ti.rotogro.utils.JustifiedTextView;

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
                     break;
               }
            } else
               callBaseActivity();
         } else
            callBaseActivity();

      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   @Override
   protected void onResume() {
      super.onResume();
   }

   public void termAlert() {

      try {
         final Dialog aDlg = new Dialog( myAppContext, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen );

         DisplayMetrics displaymetrics = new DisplayMetrics();

         myAppContext.getWindowManager().getDefaultDisplay().getMetrics( displaymetrics );

         aDlg.requestWindowFeature( Window.FEATURE_NO_TITLE );
         aDlg.getWindow().setLayout( displaymetrics.widthPixels, displaymetrics.heightPixels );
         aDlg.setContentView( R.layout.alert_dialog_for_trem_alert );
         aDlg.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
         aDlg.setCancelable( false );

         JustifiedTextView aPrideAlertTXT = ( JustifiedTextView ) aDlg.findViewById( R.id.alert_dialog_for_term_TXT );
         aPrideAlertTXT.setText( myAppContext.getResources().getString( R.string.main_term_condition ) );
         final TextView aDeclineTXT = ( TextView ) aDlg.findViewById( R.id.layout_decline_TXT );
         final TextView aAcceptTXT = ( TextView ) aDlg.findViewById( R.id.layout_accept_TXT );
         CheckBox aTermsCheckBX = ( CheckBox ) aDlg.findViewById( R.id.terms_ch );

         aAcceptTXT.setVisibility( View.GONE );

         aTermsCheckBX.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged( CompoundButton buttonView, boolean isChecked ) {

               if( isChecked ) {
                  aAcceptTXT.setVisibility( View.VISIBLE );
                  aDeclineTXT.setVisibility( View.GONE );
                  aAcceptTXT.setTextColor( myAppContext.getResources().getColor( R.color.menu_color_blue ) );
                  aAcceptTXT.setEnabled( true );
               } else {
                  aAcceptTXT.setVisibility( View.GONE );
                  aDeclineTXT.setVisibility( View.VISIBLE );
                  aAcceptTXT.setEnabled( false );
               }
            }
         } );

         aDeclineTXT.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
               TIPrefs.putBoolean( "terms", false );
               aDlg.cancel();
               closeApp();
            }
         } );

         aAcceptTXT.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
               TIPrefs.putBoolean( "terms", true );
               viewSplashCount();
               aDlg.cancel();

            }
         } );

         aDlg.setCanceledOnTouchOutside( false );

         aDlg.show();
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }


   private void closeApp() {
      try {
         TIPrefs.putBoolean( "Login_status", false );
         Intent intent = new Intent( Intent.ACTION_MAIN );
         intent.addCategory( Intent.CATEGORY_HOME );
         intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
         startActivity( intent );
         finish();
         System.exit( 0 );
      } catch( Exception e ) {
         e.printStackTrace();
      }

   }

}
