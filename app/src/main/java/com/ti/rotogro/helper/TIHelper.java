package com.ti.rotogro.helper;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.ti.rotogro.R;
import com.ti.rotogro.listener.TPIDenyAlert;
import com.ti.rotogro.utils.TIDCNetworkManager;

import java.util.Locale;

public class TIHelper {

   public static String TAG = TIHelper.class.getSimpleName();

   public static boolean checkInternet( Context aContext ) {
      return TIDCNetworkManager.isInternetOnCheck( aContext );
   }

   public static void showDenyPermissionAlert( final Activity aContext, String aContent, final TPIDenyAlert aTRIDenyAlert ) {
      new MaterialDialog.Builder( aContext )
              .content( aContent )
              .title( aContext.getResources().getString( R.string.app_name ) )
              .backgroundColorRes( R.color.white )
              .positiveColorRes( R.color.colorPrimary )
              .negativeColorRes( R.color.colorPrimary )
              .contentColorRes( R.color.black )
              .positiveText( "Yes" )
              .cancelable( true )
              .negativeText( "No" )
              .callback( new MaterialDialog.ButtonCallback() {
                 @Override
                 public void onPositive( MaterialDialog dialog ) {
                    try {
                       try {
                          aTRIDenyAlert.onClick();
                          dialog.dismiss();
                       } catch( ActivityNotFoundException e ) {
                          e.printStackTrace();
                       }
                    } catch( Exception e ) {
                       e.printStackTrace();
                    }
                 }

                 @Override
                 public void onNegative( MaterialDialog dialog ) {
                    super.onNegative( dialog );
                    try {
                       try {
                          TIHelper.showPermissionErrorLayout( ( AppCompatActivity ) aContext,
                                  aContext.getResources().getString( R.string.message_permission_denied ),
                                  R.drawable.ic_permission, true );
                       } catch( ActivityNotFoundException e ) {
                          e.printStackTrace();
                       }
                    } catch( Exception e ) {
                       e.printStackTrace();
                    }
                 }
              } )
              .show();
   }


   public static void showPermissionErrorLayout( final AppCompatActivity aContext, String aPermissionMsg, int aImageId,
                                                 boolean aStatus ) {
      try {
         if( aContext == null )
            return;

         LinearLayout aNoPermissionLay = ( LinearLayout ) aContext
                 .findViewById( R.id.activity_common_LAY_inflate_no_permission );

         if( aStatus ) {
            aNoPermissionLay.setVisibility( View.VISIBLE );
         } else {
            aNoPermissionLay.setVisibility( View.GONE );
            return;
         }

         TextView aContentTXT = ( TextView ) aNoPermissionLay
                 .findViewById( R.id.layout_inflate_no_internet_TXT_give_access );


         ImageView aNoDataIMG = ( ImageView ) aNoPermissionLay
                 .findViewById( R.id.layout_inflate_server_no_data_IMG );

         ImageView aPermissionIMG = ( ImageView ) aNoPermissionLay
                 .findViewById( R.id.layout_inflate_server_no_permission_IMG );

         final Animation animation = new AlphaAnimation( 1, 0 );
         animation.setDuration( 1000 );
         animation.setInterpolator( new LinearInterpolator() );
         animation.setRepeatCount( Animation.INFINITE );
         animation.setRepeatMode( Animation.REVERSE );
         aNoDataIMG.startAnimation( animation );

         aContentTXT.setText( aPermissionMsg );

         aPermissionIMG.setImageResource( aImageId );

         TextView aTapToTryTXT = ( TextView ) aNoPermissionLay
                 .findViewById( R.id.layout_inflate_no_permission_TXT_content );

         aTapToTryTXT.setText( Html.fromHtml( aContext.getResources().getString( R.string.label_tap_to_retry_permission ) ) );

         aTapToTryTXT.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
               try {
                  startInstalledAppDetailsActivity( aContext );
               } catch( Exception e ) {
                  e.printStackTrace();
               }
            }
         } );

         aTapToTryTXT.setVisibility( View.VISIBLE );

      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   private static void startInstalledAppDetailsActivity( final Activity context ) {
      if( context == null ) {
         return;
      }
      final Intent i = new Intent();
      i.setAction( Settings.ACTION_APPLICATION_DETAILS_SETTINGS );
      i.addCategory( Intent.CATEGORY_DEFAULT );
      i.setData( Uri.parse( "package:" + context.getPackageName() ) );
      i.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
      i.addFlags( Intent.FLAG_ACTIVITY_NO_HISTORY );
      i.addFlags( Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS );
      context.startActivity( i );
   }


   public static void loadImage( Context aContext, final ImageView aImageView, String aImageUrlStr, int aDefaultImage, int aErrorImage ) {
      try {
         ImageLoader imageLoader = ImageLoader.getInstance();
         imageLoader.init( ImageLoaderConfiguration.createDefault( aContext ) );
         DisplayImageOptions options = new DisplayImageOptions.Builder()
                 .cacheInMemory( false )
                 .cacheOnDisk( false )
                 .imageScaleType( ImageScaleType.EXACTLY )
                 .resetViewBeforeLoading( true )
                 .showImageForEmptyUri( ContextCompat.getDrawable( aContext, aDefaultImage ) )
                 .showImageOnFail( ContextCompat.getDrawable( aContext, aErrorImage ) )
                 .showImageOnLoading( ContextCompat.getDrawable( aContext, aDefaultImage ) )
                 .build();


         //download and display image from url
         imageLoader.displayImage( aImageUrlStr, aImageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted( String s, View view ) {

            }

            @Override
            public void onLoadingFailed( String s, View view, FailReason failReason ) {

            }

            @Override
            public void onLoadingComplete( String s, View view, Bitmap bitmap ) {
               //   Log.d(TAG, "onLoadingComplete: " + s);

            }


            @Override
            public void onLoadingCancelled( String s, View view ) {

            }
         } );
      } catch( OutOfMemoryError | Exception e ) {
         e.printStackTrace();
      }

   }


   /**
    * To check tablet screen or mobile screen
    *
    * @return String
    */
   public static String isTabletDevice( FragmentActivity aContext ) {
      boolean aTabletSize = false;
      try {
         aTabletSize = aContext.getResources().getBoolean( R.bool.isTablet );
      } catch( Exception e ) {
         e.printStackTrace();
         Log.e( TAG, e.getCause().toString() );
         aTabletSize = false;
      }

      if( aTabletSize )
         return "Tablet";
      else
         return "Mobile";

   }


   public static String getDeviceHeight( AppCompatActivity aContext ) {
      DisplayMetrics displaymetrics = new DisplayMetrics();
      aContext.getWindowManager().getDefaultDisplay().getMetrics( displaymetrics );
      return String.valueOf( displaymetrics.heightPixels );
   }

   public static String getDeviceWidth( AppCompatActivity aContext ) {
      DisplayMetrics displaymetrics = new DisplayMetrics();
      aContext.getWindowManager().getDefaultDisplay().getMetrics( displaymetrics );
      return String.valueOf( displaymetrics.widthPixels );
   }


   /**
    * @param aMessage aMessage
    */
   public static void showAlertDialog( final AppCompatActivity aContext, String aMessage ) {
      try {
         AlertDialog.Builder builder = new AlertDialog.Builder( aContext );
         builder.setMessage( aMessage )
                 .setTitle( aContext.getString( R.string.app_name ) )
                 .setCancelable( false )
                 .setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int id ) {
                       dialog.dismiss();
                    }
                 } );

         AlertDialog alert = builder.create();
         alert.show();
         // Change the buttons color in dialog
         Button pbutton = alert.getButton( DialogInterface.BUTTON_POSITIVE );
         pbutton.setTextColor( ContextCompat.getColor( aContext, R.color.black ) );
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }


   /**
    * @param aMessage aMessage
    */
   public static void showAlertDialog( final FragmentActivity aContext, String aMessage ) {
      try {
         AlertDialog.Builder builder = new AlertDialog.Builder( aContext );
         builder.setMessage( aMessage )
                 .setTitle( aContext.getString( R.string.app_name ) )
                 .setCancelable( false )
                 .setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int id ) {
                       dialog.dismiss();
                    }
                 } );

         AlertDialog alert = builder.create();
         alert.show();
         // Change the buttons color in dialog
         Button pbutton = alert.getButton( DialogInterface.BUTTON_POSITIVE );
         pbutton.setTextColor( ContextCompat.getColor( aContext, R.color.black ) );
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }


   public static int getViewHeight( FragmentActivity aContext ) {
      int viewHeight = 0;
      try {

         // status bar height
         int statusBarHeight = 0;
         int resourceId = aContext.getResources().getIdentifier( "status_bar_height", "dimen", "android" );
         if( resourceId > 0 ) {
            statusBarHeight = aContext.getResources().getDimensionPixelSize( resourceId );
         }

         // action bar height
         int actionBarHeight = 0;
         final TypedArray styledAttributes = aContext.getTheme().obtainStyledAttributes(
                 new int[]{ android.R.attr.actionBarSize }
         );
         actionBarHeight = ( int ) styledAttributes.getDimension( 0, 0 );
         styledAttributes.recycle();

         viewHeight = statusBarHeight + actionBarHeight;

         viewHeight = viewHeight / 2;
      } catch( Resources.NotFoundException e ) {
         e.printStackTrace();
      }
      return viewHeight;
   }


   public static ContextWrapper changeLang( Context context, String lang_code ) {
      Locale sysLocale;

      Resources rs = context.getResources();
      Configuration config = rs.getConfiguration();

      if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ) {
         sysLocale = config.getLocales().get( 0 );
      } else {
         sysLocale = config.locale;
      }

      if( !lang_code.equals( "" ) && !sysLocale.getLanguage().equals( lang_code ) ) {
         String country = "IN";
         Locale locale = new Locale( lang_code, country );

         //Locale locale = new Locale( lang_code );
         Locale.setDefault( locale );
         if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ) {
            config.setLocale( locale );
         } else {
            config.locale = locale;
         }
         if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ) {
            context = context.createConfigurationContext( config );
         } else {
            context.getResources().updateConfiguration( config, context.getResources().getDisplayMetrics() );
         }
      }


      return new ContextWrapper( context );
   }
}
