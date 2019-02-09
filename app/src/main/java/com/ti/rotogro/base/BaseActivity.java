package com.ti.rotogro.base;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.ti.rotogro.R;
import com.ti.rotogro.helper.TIHelper;
import com.ti.rotogro.preference.TIPrefs;
import com.ti.rotogro.ui.login.TILoginActivity;
import com.ti.rotogro.ui.main.TIMainActivity;
import com.ti.rotogro.utils.MGProgressDialog;

import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;


/**
 * Created by root on 17/4/17.
 */

public abstract class BaseActivity extends ActivityManagePermission implements BaseView {

   private MGProgressDialog mProgressDialog;
   private static long myBackPressed;

   Context myContext;

   public void Init() {

      myContext = this;
   }

   @Override
   protected void onCreate( Bundle savedInstanceState ) {
      super.onCreate( savedInstanceState );
      mProgressDialog = new MGProgressDialog( BaseActivity.this );
      Init();

   }

   @Override
   protected void onRestart() {
      super.onRestart();

   }

   @Override
   public void showProgress() {
      if( mProgressDialog != null ) {
         mProgressDialog.setMessage( "Loading" );
         mProgressDialog.setCancelable( false );
         mProgressDialog.show();
      } else {
         showToast( "showProgress is null" );
      }
   }

   @Override
   public void hideProgress() {
      if( mProgressDialog != null ) {
         mProgressDialog.dismiss();
      }
   }

   @Override
   protected void onPause() {
      super.onPause();
   }


   @Override
   public void showToast( String errorMessage ) {
      Toast.makeText( this, errorMessage, Toast.LENGTH_SHORT ).show();
   }

   @Override
   public String getStringById( int id ) {
      return getString( id );
   }

   @Override
   public String getStringById( int id, String param1 ) {
      return getString( id, param1 );
   }

   @Override
   public String getStringById( int id, String param1, String param2 ) {
      return getString( id, param1, param2 );
   }


   public void hideKeyboard() {
      try {
         View view = this.getCurrentFocus();
         if( view != null ) {
            InputMethodManager imm = ( InputMethodManager )
                    getSystemService( Context.INPUT_METHOD_SERVICE );
            imm.hideSoftInputFromWindow( view.getWindowToken(), 0 );
         }
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }


   public void exitApp() {
      try {
         if( myBackPressed + 2000 > System.currentTimeMillis() ) {

            Intent aIntent = new Intent( Intent.ACTION_MAIN );
            aIntent.addCategory( Intent.CATEGORY_HOME );
            aIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity( aIntent );
            finish();
         } else {
            Toast.makeText( getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT ).show();
            myBackPressed = System.currentTimeMillis();
         }
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }


   @Override
   public void callBaseActivity() {
      Boolean aBool = TIPrefs.getBoolean( "Login_status", false );
      Intent aIntent;

      if( aBool )
         aIntent = new Intent( myContext, TIMainActivity.class );
      else
         aIntent = new Intent( myContext, TILoginActivity.class );

      startActivity( aIntent );
      this.finish();
   }

   @Override
   public void showUpdateDialog( String aMessage ) {
      try {
         AlertDialog.Builder builder = new AlertDialog.Builder( myContext );
         builder.setMessage( aMessage )
                 .setTitle( myContext.getResources().getString( R.string.app_name ) )
                 .setCancelable( false )
                 .setPositiveButton( R.string.label_update, new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int id ) {
                       dialog.dismiss();
                       launchMarket();
                    }
                 } ).setNegativeButton( R.string.label_later, new DialogInterface.OnClickListener() {
            public void onClick( DialogInterface dialog, int id ) {
               dialog.dismiss();
               callBaseActivity();
            }
         } );

         AlertDialog alert = builder.create();
         alert.show();
         // Change the buttons color in dialog
         Button pbutton = alert.getButton( DialogInterface.BUTTON_POSITIVE );
         pbutton.setTextColor( ContextCompat.getColor( myContext, R.color.black ) );
         Button nbutton = alert.getButton( DialogInterface.BUTTON_NEGATIVE );
         nbutton.setTextColor( ContextCompat.getColor( myContext, R.color.black ) );
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   @Override
   public void showMandatoryUpdateDialog( String aMessage ) {
      try {
         AlertDialog.Builder builder = new AlertDialog.Builder( myContext );
         builder.setMessage( aMessage )
                 .setTitle( BaseActivity.this.getResources().getString( R.string.app_name ) )
                 .setCancelable( false )
                 .setPositiveButton( R.string.label_update, new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int id ) {
                       dialog.dismiss();
                       launchMarket();
                    }
                 } );

         AlertDialog alert = builder.create();
         alert.show();
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   @Override
   public boolean checkInternet() {
      return TIHelper.checkInternet( this );
   }


   /**
    * Launch the Play store Market
    */
   public void launchMarket() {
      try {
         Uri uriData = Uri.parse( "market://details?id=" + myContext.getPackageName() );
         Intent goToMarket = new Intent( Intent.ACTION_VIEW, uriData );
         try {
            startActivity( goToMarket );
            finish();
         } catch( ActivityNotFoundException e ) {
            showToast( getString( R.string.not_launch ) );
         }

      } catch( Exception e ) {
         e.printStackTrace();
      }
   }


}
