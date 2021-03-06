package com.ti.rotogro.ui.login;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.ti.rotogro.R;
import com.ti.rotogro.base.BaseActivity;
import com.ti.rotogro.data.db.AppDatabase;
import com.ti.rotogro.data.db.entity.LanguageMaster;
import com.ti.rotogro.helper.TIHelper;
import com.ti.rotogro.listener.TPIDenyAlert;
import com.ti.rotogro.model.Response;
import com.ti.rotogro.preference.TIPrefs;
import com.ti.rotogro.ui.login.adapter.LanguageRecyclerAdapter;
import com.ti.rotogro.ui.main.TIMainActivity;
import com.ti.rotogro.utils.AppSignatureHelper;
import com.ti.rotogro.utils.MGGpsLocation;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;

public class TILoginActivity extends BaseActivity implements TILoginContract.View, View.OnClickListener, PermissionResult {

   private AppDatabase myAppDatabase;
   AppCompatActivity myContext;

   RecyclerView myLanguageRecyclerView;
   Button myGetOTPBUT, mySubmitBut, myReSendBUT;
   LinearLayout myLoginLayout;
   LinearLayout myLoginErrorLayout;
   LinearLayout myOTPLayout;

   TextInputEditText myMobileEditText, myOTPEditText;
   TextInputLayout myMobileInputLayout, myOTPInputLayout;

   TILoginPresenter myTiLoginPresenter;

   MGGpsLocation myGpsLocation;

   LanguageMaster myLanguageMaster;
   LanguageRecyclerAdapter mLanguageRecyclerAdapter;

   String myLatitudeStr = "", myLongitudeStr = "";
   int mPosition = 0;

   List<LanguageMaster> myLanguageMasterList;
   RecyclerView.LayoutManager mLayoutManager;

   private SmsBroadcastReceiver myBroadcastReceiver;
   private String myDeviceOS, myDeviceModel, myDeviceMake, myDeviceSize, myDeviceType, myDeviceIMEI;
   private static final int REQUEST_CHECK_SETTINGS = 0x1;
   public static GoogleApiClient mGoogleApiClient;
   private static final String BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED";


   @Override
   protected void attachBaseContext( Context newBase ) {
      if( Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1 ) {
         String aString = TIPrefs.getString( "sel_language", "" );
         newBase = TIHelper.changeLang( newBase, aString );
      }
      super.attachBaseContext( newBase );
   }

   @Override
   protected void onCreate( Bundle savedInstanceState ) {
      super.onCreate( savedInstanceState );

      LanguageMaster aLanguageMaster = TIPrefs.getObject( "language", LanguageMaster.class );
      if( aLanguageMaster != null ) {
        // String aLang = firstTwoChar( aLanguageMaster.getLan_Name() );
         String aString = TIPrefs.getString( "sel_language", "" );
         setLocale( aString );
      }

      setContentView( R.layout.activity_tilogin );
      myContext = this;
      myTiLoginPresenter = new TILoginPresenter( this, myContext );
      myTiLoginPresenter.attachView( this );
      myTiLoginPresenter.initPresenter();

   }


   @Override
   public void initActivity() {

      myAppDatabase = AppDatabase.getDatabase( getApplication() );
      myLanguageRecyclerView = findViewById( R.id.language_recycrer_view );

      AppSignatureHelper appSignatureHelper = new AppSignatureHelper( myContext );
      appSignatureHelper.getAppSignatures();

      myGetOTPBUT = findViewById( R.id.login_get_otp_BUT );
      mySubmitBut = findViewById( R.id.login_submit_BUT );
      myReSendBUT = findViewById( R.id.activity_resend_BTN );

      myMobileInputLayout = findViewById( R.id.login_mobile_TXTINPUT );
      myMobileEditText = findViewById( R.id.login_mobile_EDT );

      myOTPInputLayout = findViewById( R.id.login_otp_TXTINPUT );
      myOTPEditText = findViewById( R.id.login_otp_EDT );

      myLoginLayout = findViewById( R.id.activity_login_layout );
      myOTPLayout = findViewById( R.id.otp_layout );

      myLoginErrorLayout = findViewById( R.id.activity_common_LAY_inflate_no_permission );

      myLanguageMasterList = new ArrayList<>();

      initGoogleAPIClient();

      //set listener
      myGetOTPBUT.setOnClickListener( this );
      mySubmitBut.setOnClickListener( this );
      myReSendBUT.setOnClickListener( this );

      mLayoutManager = new LinearLayoutManager( TILoginActivity.this, LinearLayoutManager.HORIZONTAL, false );
      myLanguageRecyclerView.setLayoutManager( mLayoutManager );

      mLanguageRecyclerAdapter = new LanguageRecyclerAdapter( myContext, myLanguageMasterList, languageRecyclerItemClickListener );
      myLanguageRecyclerView.setAdapter( mLanguageRecyclerAdapter );

      //   getOTPVerification();

      TIPrefs.putString( "LastUpdate", "2019-02-08 11:40:52.810" );

   }


   /* Initiate Google API Client  */
   public void initGoogleAPIClient() {
      try {
         //Without Google API Client Auto Location Dialog will not work
         mGoogleApiClient = new GoogleApiClient.Builder( TILoginActivity.this )
                 .addApi( LocationServices.API ).build();
         mGoogleApiClient.connect();
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }


   private void getGpsDetailInfo() {
      try {
         LocationManager manager = ( LocationManager ) myContext.getSystemService( Context.LOCATION_SERVICE );
         if( manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            myGpsLocation = new MGGpsLocation( myContext );
            myLatitudeStr = String.valueOf( myGpsLocation.getLatitude() );
            myLongitudeStr = String.valueOf( myGpsLocation.getLongitude() );
         } else {
            if( checkInternet() ) {
               //  showSettingsAlert();
            }
         }
      } catch( Exception e ) {
         e.printStackTrace();
      }

      Log.e( "XXXXXXXXXX", " " + myLatitudeStr + " " + myLongitudeStr );
   }


   @Override
   public void setRecyclerViewAdapter( List<LanguageMaster> aLanguageMasterList ) {
      try {
         hideProgress();
         myLanguageMasterList = aLanguageMasterList;
         LanguageMaster aLanguage = TIPrefs.getObject( "language", LanguageMaster.class );
         if( aLanguage != null ) {
            for( int i = 0; i < myLanguageMasterList.size(); i++ ) {
               LanguageMaster bLanguage = myLanguageMasterList.get( i );
               if( bLanguage.Lan_id.equals( aLanguage.getLan_id() ) ) {
                  bLanguage.setSelect( true );
                  myLanguageMaster = aLanguage;
                  mPosition = i;
                  TIPrefs.putObject( "language", myLanguageMaster );
               }
            }
         } else {
            for( int i = 0; i < myLanguageMasterList.size(); i++ ) {
               LanguageMaster bLanguage = myLanguageMasterList.get( i );
               if( bLanguage.Lan_Name.toLowerCase().contains( "english" ) ) {
                  bLanguage.setSelect( true );
                  myLanguageMaster = bLanguage;
                  mPosition = i;
                  TIPrefs.putObject( "language", myLanguageMaster );
               }
            }
         }

         mLanguageRecyclerAdapter.updateAdapter( aLanguageMasterList );

         final Handler handler = new Handler();
         handler.postDelayed( new Runnable() {
            @Override
            public void run() {
               myLanguageRecyclerView.smoothScrollToPosition( mPosition );
            }
         }, 10 );

      } catch( Exception e ) {
         e.printStackTrace();
      }


   }

   @Override
   public void callNextActivity() {
      TIPrefs.putString( "Mob_number", myMobileEditText.getText().toString() );
      startActivity( new Intent( myContext, TIMainActivity.class ) );
      this.finish();
   }

   @Override
   public void onOTPResult( Response aResponse ) {
      myOTPLayout.setVisibility( View.VISIBLE );
      myGetOTPBUT.setVisibility( View.GONE );
      mySubmitBut.setVisibility( View.VISIBLE );
      Log.e( "OTP", aResponse.getOtp() );
      myOTPEditText.setText( aResponse.getOtp() );
   }


   @Override
   public void onClick( View view ) {
      switch( view.getId() ) {
         case R.id.login_get_otp_BUT: {
            validateMobileNumber();
            break;
         }
         case R.id.login_submit_BUT: {
            if( myMobileEditText.getText() != null && myMobileEditText.getText().length() == 10 ) {
               if( myOTPEditText.getText() != null && myOTPEditText.getText().length() == 5 ) {
                  if( myLanguageMaster != null ) {
                     showProgress();
                     getDeviceDetail();

                     Map<String, String> params = new HashMap<>();
                     params.put( "Mobile_Number", myMobileEditText.getText().toString() );
                     params.put( "Otp", myOTPEditText.getText().toString() );
                     params.put( "dev_OS", myDeviceOS );
                     params.put( "dev_Model", URLEncoder.encode( myDeviceModel ) );
                     params.put( "dev_make", URLEncoder.encode( myDeviceMake ) );
                     params.put( "dev_Size", myDeviceSize );
                     params.put( "dev_type", myDeviceType );
                     params.put( "dev_IMEI", myDeviceIMEI );
                     params.put( "Latitude", myLatitudeStr );
                     params.put( "Longitude", myLongitudeStr );

                     myTiLoginPresenter.verifyOTP( params );
                  } else {
                     showToast( myContext.getResources().getString( R.string.label_select_language ) );
                  }
               } else {
                  myOTPInputLayout.setErrorEnabled( true );
                  myOTPInputLayout.setError( myContext.getResources().getString( R.string.label_erroe_valid_otp ) );
               }
            } else {
               myMobileInputLayout.setErrorEnabled( true );
               myMobileInputLayout.setError( myContext.getResources().getString( R.string.label_error_valid_mobile_number ) );
            }
            break;
         }
         case R.id.activity_resend_BTN: {
            validateMobileNumber();
            break;
         }
      }
   }

   public void validateMobileNumber() {
      try {
         if( myMobileEditText.getText() != null && myMobileEditText.getText().length() == 10 ) {
            showProgress();
            myMobileInputLayout.setErrorEnabled( false );

            if( myLatitudeStr != null && myLatitudeStr.equals( "0.0" ) )
               getGpsDetailInfo();

            myTiLoginPresenter.sendOTP( myMobileEditText.getText().toString() );
         } else {
            myMobileInputLayout.setErrorEnabled( true );
            myMobileInputLayout.setError( myContext.getResources().getString( R.string.label_error_valid_mobile_number ) );
         }
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   @Override
   public void getDeviceDetail() {
      try {
         myDeviceOS = "" + Build.VERSION.SDK_INT;
         myDeviceModel = Build.MODEL;
         myDeviceMake = Build.MANUFACTURER;
         myDeviceSize = "" + TIHelper.getDeviceWidth( myContext ) + "x" + TIHelper.getDeviceHeight( myContext );
         myDeviceType = TIHelper.isTabletDevice( myContext );

         TelephonyManager telephonyManager = ( TelephonyManager ) getSystemService( Context.TELEPHONY_SERVICE );
         if( ActivityCompat.checkSelfPermission( this, Manifest.permission.READ_PHONE_STATE ) != PackageManager.PERMISSION_GRANTED ) {
            return;
         }
         myDeviceIMEI = telephonyManager.getDeviceId();
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }


   /**
    * RecyclerItem click event listener
    */
   private LanguageRecyclerItemClickListener languageRecyclerItemClickListener = new LanguageRecyclerItemClickListener() {
      @Override
      public void onItemClick( int position, LanguageMaster aLanguageMaster ) {
         try {
            myLanguageMaster = aLanguageMaster;
            TIPrefs.putObject( "language", aLanguageMaster );
            String aLang;
            switch( myLanguageMaster.getLan_id() ) {
               case "1":
                  //aLang = firstTwoChar( myLanguageMaster.getLan_Name() );
                  aLang = "ta";
                  setLocale( aLang );
                  break;
               case "2":
                  aLang = "en";
                 // aLang = firstTwoChar( myLanguageMaster.getLan_Name() );
                  setLocale( aLang );
                  break;
               case "3":
                 // aLang = firstTwoChar( myLanguageMaster.getLan_Name() );
                  aLang = "te";
                  setLocale( aLang );
                  break;
               case "4":
                 // aLang = firstTwoChar( myLanguageMaster.getLan_Name() );
                  aLang = "hi";
                  setLocale( aLang );
                  break;
               default:
                  setLocale( "en" );
                  break;
            }

            refresh();
         } catch( Exception e ) {
            e.printStackTrace();
         }
      }
   };

   private void refresh() {
      Intent intent = getIntent();
      overridePendingTransition( 0, 0 );
      intent.addFlags( Intent.FLAG_ACTIVITY_NO_ANIMATION );
      finish();
      overridePendingTransition( 0, 0 );
      startActivity( intent );
   }


   public String firstTwoChar( String str ) {
      return str.length() < 2 ? str : str.substring( 0, 2 ).toLowerCase();
   }


   public void setLocale( String lang ) {
      try {
         TIPrefs.putString( "sel_language", lang );
         String country = "IN";
         Locale locale = new Locale( lang, country );

         Resources activityRes = getResources();
         Configuration activityConf = activityRes.getConfiguration();

         activityConf.setLocale( locale );
         activityRes.updateConfiguration( activityConf, activityRes.getDisplayMetrics() );

         Resources applicationRes = getApplicationContext().getResources();
         Configuration applicationConf = applicationRes.getConfiguration();
         applicationConf.setLocale( locale );
         applicationRes.updateConfiguration( applicationConf, applicationRes.getDisplayMetrics() );
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }


   private void getOTPVerification() {
      SmsRetrieverClient client = SmsRetriever.getClient( this /* context */ );


      Task<Void> task = client.startSmsRetriever();

      task.addOnSuccessListener( new OnSuccessListener<Void>() {
         @Override
         public void onSuccess( Void aVoid ) {
            myBroadcastReceiver = new SmsBroadcastReceiver();
            IntentFilter filter = new IntentFilter( SmsRetriever.SMS_RETRIEVED_ACTION );
            registerReceiver( myBroadcastReceiver, filter );
         }
      } );


      task.addOnFailureListener( new OnFailureListener() {
         @Override
         public void onFailure( @NonNull Exception e ) {
            Log.e( "Error", "Fail to start SmsRetriever" );
         }
      } );

   }


   @Override
   protected void onResume() {
      super.onResume();
      askPermission();
      if( checkPermissionGranted() ) {
         myLoginLayout.setVisibility( View.VISIBLE );
         getDeviceDetail();
         checkPermissions();
         getGpsDetailInfo();
      }
      registerReceiver( gpsLocationReceiver, new IntentFilter( BROADCAST_ACTION ) );//Register broadcast receiver to check the status of GPS
   }


   /* Check Location Permission for Marshmallow Devices */
   private void checkPermissions() {
      try {
         if( Build.VERSION.SDK_INT >= 23 ) {
            if( ContextCompat.checkSelfPermission( TILoginActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION )
                    != PackageManager.PERMISSION_GRANTED ) {
               // requestLocationPermission();
            } else
               showSettingDialog();
         } else
            showSettingDialog();
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   /* Show Location Access Dialog */
   private void showSettingDialog() {
      try {
         LocationRequest locationRequest = LocationRequest.create();
         locationRequest.setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );//Setting priotity of Location request to high
         locationRequest.setInterval( 30 * 1000 );
         locationRequest.setFastestInterval( 5 * 1000 );//5 sec Time interval for location update
         LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                 .addLocationRequest( locationRequest );

         builder.setAlwaysShow( true ); //this is the key ingredient to show dialog always when GPS is off

         PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings( mGoogleApiClient, builder.build() );
         result.setResultCallback( new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult( @NonNull LocationSettingsResult result ) {
               final Status status = result.getStatus();
               final LocationSettingsStates state = result.getLocationSettingsStates();
               switch( status.getStatusCode() ) {
                  case LocationSettingsStatusCodes.SUCCESS:
                     // All location settings are satisfied. The client can initialize location
                     // requests here.
                     break;
                  case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                     // Location settings are not satisfied. But could be fixed by showing the user
                     // retailer_radio_select dialog.
                     try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        status.startResolutionForResult( TILoginActivity.this, REQUEST_CHECK_SETTINGS );
                     } catch( IntentSender.SendIntentException e ) {
                        e.printStackTrace();
                        // Ignore the error.
                     }
                     break;
                  case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                     // Location settings are not satisfied. However, we have no way to fix the
                     // settings so we won't show the dialog.
                     break;
               }
            }
         } );
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   private void askPermission() {
      try {
         askCompactPermissions( new String[]{
                 PermissionUtils.Manifest_READ_PHONE_STATE,
                 PermissionUtils.Manifest_READ_EXTERNAL_STORAGE,
                 PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE,
                 PermissionUtils.Manifest_ACCESS_COARSE_LOCATION,
                 PermissionUtils.Manifest_ACCESS_FINE_LOCATION
         }, this );
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   public boolean checkPermissionGranted() {
      boolean isGranted = false;
      try {
         isGranted = isPermissionsGranted( myContext, new String[]{ PermissionUtils.Manifest_READ_PHONE_STATE,
                 PermissionUtils.Manifest_READ_EXTERNAL_STORAGE, PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE,
                 PermissionUtils.Manifest_ACCESS_COARSE_LOCATION, PermissionUtils.Manifest_ACCESS_FINE_LOCATION } );
      } catch( Exception e ) {
         e.printStackTrace();
      } finally {
         if( isGranted )
            TIHelper.showPermissionErrorLayout( myContext,
                    myContext.getResources().getString( R.string.message_permission_denied ),
                    R.drawable.ic_permission, false );
      }
      return isGranted;
   }

   @Override
   public void permissionGranted() {
      myLoginLayout.setVisibility( View.VISIBLE );
   }

   @Override
   public void permissionDenied() {
      try {
         TIHelper.showDenyPermissionAlert( myContext,
                 myContext.getResources().getString( R.string.message_permission_denied ),
                 new TPIDenyAlert() {
                    @Override
                    public void onClick() {
                       askPermission();
                    }
                 } );
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   @Override
   public void permissionForeverDenied() {
      try {
         myLoginLayout.setVisibility( View.GONE );
         TIHelper.showPermissionErrorLayout( myContext,
                 myContext.getResources().getString( R.string.message_permission_denied ),
                 R.drawable.ic_permission, true );
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   public class SmsBroadcastReceiver extends BroadcastReceiver {

      @Override
      public void onReceive( Context context, Intent intent ) {

         try {
            if( SmsRetriever.SMS_RETRIEVED_ACTION.equals( intent.getAction() ) ) {
               Bundle extras = intent.getExtras();
               Status status = ( Status ) extras.get( SmsRetriever.EXTRA_STATUS );

               switch( status.getStatusCode() ) {
                  case CommonStatusCodes.SUCCESS:
                     myOTPLayout.setVisibility( View.VISIBLE );
                     // Get SMS message contents
                     String aMessage = ( String ) extras.get( SmsRetriever.EXTRA_SMS_MESSAGE );
                     if( aMessage.contains( "ROTOGRO OTP" ) ) {
                        String[] msgsplit = aMessage.split( ":" );
                        String aSTR = msgsplit[ 1 ].trim();
                        String[] aMsg = aSTR.split( "\\s+" );
                        myOTPEditText.setText( aMsg[ 0 ].trim() );
                     }
                     break;
                  case CommonStatusCodes.TIMEOUT:
                     TIHelper.showAlertDialog( myContext, "OTP TIMEOUT Press Resend Button" );
                     break;
               }
            }
         } catch( Exception e ) {
            e.printStackTrace();
         }
      }
   }


   //Run on UI
   private Runnable sendUpdatesToUI = new Runnable() {
      public void run() {
         showSettingDialog();
      }
   };

   /* Broadcast receiver to check status of GPS */
   private BroadcastReceiver gpsLocationReceiver = new BroadcastReceiver() {

      @Override
      public void onReceive( Context context, Intent intent ) {
         try {
            //If Action is Location
            if( intent.getAction().matches( BROADCAST_ACTION ) ) {
               LocationManager locationManager = ( LocationManager ) context.getSystemService( Context.LOCATION_SERVICE );
               //Check if GPS is turned ON or OFF
               if( locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                  Log.e( "About GPS", "GPS is Enabled in your device" );
               } else {
                  //If GPS turned OFF show Location Dialog
                  new Handler().postDelayed( sendUpdatesToUI, 10 );
                  // showSettingDialog();
                  Log.e( "About GPS", "GPS is Disabled in your device" );
               }

            }
         } catch( Exception e ) {
            e.printStackTrace();
         }
      }
   };


   @Override
   protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
      try {
         switch( requestCode ) {
            case REQUEST_CHECK_SETTINGS:
               switch( resultCode ) {
                  case RESULT_OK:
                     Log.e( "Settings", "Result OK" );
                     getGpsDetailInfo();
                     break;
                  case RESULT_CANCELED:
                     Log.e( "Settings", "Result Cancel" );
                     break;
               }
               break;
         }
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   @Override
   protected void onDestroy() {
      super.onDestroy();

      if( gpsLocationReceiver != null )
         unregisterReceiver( gpsLocationReceiver );

      if( myBroadcastReceiver != null )
         unregisterReceiver( myBroadcastReceiver );
   }
}
