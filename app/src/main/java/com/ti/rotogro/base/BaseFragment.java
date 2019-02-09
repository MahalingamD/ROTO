package com.ti.rotogro.base;

/**
 * Created by root on 17/4/17.
 */

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.ti.rotogro.R;
import com.ti.rotogro.helper.TIHelper;

public abstract class BaseFragment extends Fragment implements BaseView {

   private BaseActivity activity;

   Context myContext;

   protected abstract void setUp( View view );


   @Override
   public void onCreate( @Nullable Bundle savedInstanceState ) {
      super.onCreate( savedInstanceState );
      setHasOptionsMenu( false );
   }

   @Override
   public void onAttach( Context context ) {
      super.onAttach( context );
      if( context instanceof BaseActivity ) {
         this.activity = ( BaseActivity ) context;

      }
   }


   @Override
   public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
      super.onViewCreated( view, savedInstanceState );
      myContext = getActivity();
      setUp( view );
   }

   public BaseActivity getBaseActivity() {
      return activity;
   }

   @Override
   public void onDetach() {
      activity = null;
      super.onDetach();
   }

   @Override
   public String getStringById( int id ) {
      return getString( id );
   }

   @Override
   public String getStringById( int id, String param1 ) {
      return activity.getStringById( id, param1 );
   }

   @Override
   public String getStringById( int id, String param1, String param2 ) {
      return activity.getStringById( id, param1, param2 );
   }

   @Override
   public void showProgress() {
      if( activity != null ) {
         activity.showProgress();
      }
   }

   @Override
   public void hideProgress() {
      if( activity != null ) {
         activity.hideProgress();
      }
   }

   @Override
   public void showToast( String errorMessage ) {
      if( activity != null ) {
         activity.showToast( errorMessage );
      }
   }


   @Override
   public void callBaseActivity() {

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
            }
         } );

         AlertDialog alert = builder.create();
         alert.show();
         // Change the buttons color in dialog
         Button pbutton = alert.getButton( DialogInterface.BUTTON_POSITIVE );
         pbutton.setTextColor( ContextCompat.getColor( myContext, R.color.colorPrimary ) );
         Button nbutton = alert.getButton( DialogInterface.BUTTON_NEGATIVE );
         nbutton.setTextColor( ContextCompat.getColor( myContext, R.color.colorAccent ) );
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   @Override
   public void showMandatoryUpdateDialog( String aMessage ) {
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
                 } );

         AlertDialog alert = builder.create();
         alert.show();
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }


   @Override
   public void launchMarket() {
      try {
         Uri uriData = Uri.parse( "market://details?id=" + myContext.getPackageName() );
         Intent goToMarket = new Intent( Intent.ACTION_VIEW, uriData );
         try {
            startActivity( goToMarket );
            getBaseActivity().finish();
         } catch( ActivityNotFoundException e ) {
            showToast( getString( R.string.not_launch ) );
         }

      } catch( Exception e ) {
         e.printStackTrace();
      }
   }


   public abstract void onResumeFragment();


   public abstract void onBackPressed();

   @Override
   public boolean checkInternet() {
      return TIHelper.checkInternet( getActivity() );
   }
}
