package com.ti.rotogro.ui.login;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ti.rotogro.R;
import com.ti.rotogro.base.BasePresenter;
import com.ti.rotogro.data.db.AppDatabase;
import com.ti.rotogro.data.db.entity.AddressMaster;
import com.ti.rotogro.data.db.entity.BladeDescription;
import com.ti.rotogro.data.db.entity.BladeTypes;
import com.ti.rotogro.data.db.entity.CityMaster;
import com.ti.rotogro.data.db.entity.LanguageMaster;
import com.ti.rotogro.data.db.entity.Product;
import com.ti.rotogro.data.db.entity.StateMaster;
import com.ti.rotogro.helper.TIHelper;
import com.ti.rotogro.model.Data;
import com.ti.rotogro.preference.TIPrefs;
import com.ti.rotogro.service.RetrofitInstance;
import com.ti.rotogro.utils.MGProgressDialog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TILoginPresenter extends BasePresenter<TILoginContract.View> implements TILoginContract.Presenter {


   private TILoginContract.View myTiLoginView;
   private RetrofitInstance myRetrofitInstance;
   AppCompatActivity myContext;
   private AppDatabase myAppDatabase;

   private List<Product> myProductInfoList;
   private List<BladeTypes> myBladeTypesList;
   private List<BladeDescription> myBladeDescriptionList;

   private List<LanguageMaster> mLanguageMasterList;

   private List<StateMaster> mStateMasterList;
   private List<CityMaster> mCityMasterList;
   private List<AddressMaster> mAddressMasterList;

   public TILoginPresenter( TILoginContract.View tiLoginView, AppCompatActivity aContext ) {
      myTiLoginView = tiLoginView;
      myContext = aContext;
      myAppDatabase = AppDatabase.getDatabase( myContext.getApplication() );

      if( this.myRetrofitInstance == null ) {
         myRetrofitInstance = new RetrofitInstance();
      }
   }

   @Override
   public void initPresenter() {

      getView().initActivity();

      getLanguage();

   }

   @Override
   public void getLanguage() {
      try {
         if( getView().checkInternet() ) {

            getView().showProgress();

            myRetrofitInstance.getAPI()
                    .getAllLanguage( "GetlanguageList" )
                    .enqueue( new Callback<Data>() {
                       @Override
                       public void onResponse( @NonNull Call<Data> call, @NonNull Response<Data> response ) {

                          getView().hideProgress();
                          Data data = response.body();

                          if( data != null && data.getResponse() != null ) {
                             if( data.getResponse().getResponse_code().equals( "1" ) ) {
                                List<LanguageMaster> aList = data.getGetMasterList().getLanguageMaster();
                                if( aList != null && aList.size() > 0 ) {
                                   myAppDatabase.languageModel().deleteAll();
                                   myAppDatabase.languageModel().insertAllLanguage( aList );
                                   myTiLoginView.setRecyclerViewAdapter( myAppDatabase.languageModel().getAllLanguages( 1 ) );
                                }
                             } else {
                                TIHelper.showAlertDialog( myContext, data.getResponse().getResponse_message() );
                             }
                          }
                       }

                       @Override
                       public void onFailure( @NonNull Call<Data> call, @NonNull Throwable t ) {
                          try {
                             getView().hideProgress();
                             TIHelper.showAlertDialog( myContext, "Something went wrong!" );
                          } catch( Exception e ) {
                             e.printStackTrace();
                          }
                       }
                    } );
         } else {
            TIHelper.showAlertDialog( myContext, myContext.getString( R.string.alert_message_check_internet ) );
         }
      } catch( Exception e ) {
         e.printStackTrace();
      }

      //
   }


   private void deleteRecursive( File fileOrDirectory ) {
      try {
         if( fileOrDirectory.isDirectory() )
            for( File child : fileOrDirectory.listFiles() )
               deleteRecursive( child );

         fileOrDirectory.delete();
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   @Override
   public void sendOTP( String aMobileNumber ) {
      try {


         //File dir = new File( Environment.getExternalStorageDirectory() + File.separator + myContext.getString( R.string.app_name ) );
        // deleteRecursive( dir );

         if( getView().checkInternet() ) {
            getView().showProgress();
            myRetrofitInstance.getAPI()
                    .getOTP( "ValidateUser", aMobileNumber )
                    .enqueue( new Callback<Data>() {
                       @Override
                       public void onResponse( @NonNull Call<Data> call, @NonNull Response<Data> response ) {

                          getView().hideProgress();
                          Data data = response.body();

                          if( data != null && data.getResponse() != null ) {
                             if( data.getResponse().getResponse_code().equals( "1" ) )
                                myTiLoginView.onOTPResult( data.getResponse() );
                             else {
                                Log.e( "Error code", data.getResponse().getResponse_code() );
                                Log.e( "Error message", data.getResponse().getResponse_message() );
                                TIHelper.showAlertDialog( myContext, data.getResponse().getResponse_message() );
                             }
                          }
                       }

                       @Override
                       public void onFailure( @NonNull Call<Data> call, @NonNull Throwable t ) {
                          try {
                             getView().hideProgress();
                             Log.e( "Error message", t.getMessage().toString() );
                             TIHelper.showAlertDialog( myContext, "Something went wrong!" );
                          } catch( Exception e ) {
                             e.printStackTrace();
                          }
                       }
                    } );
         } else {
            TIHelper.showAlertDialog( myContext, myContext.getString( R.string.alert_message_check_internet ) );
         }
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   @Override
   public void verifyOTP( Map<String, String> params ) {
      try {
         if( getView().checkInternet() ) {
            getView().showProgress();
            myRetrofitInstance.getAPI()
                    .getResult( "ValidateOtp", params )
                    .enqueue( new Callback<Data>() {
                       @Override
                       public void onResponse( @NonNull Call<Data> call, @NonNull Response<Data> response ) {

                          getView().hideProgress();
                          Data data = response.body();

                          Log.e( "URL", response.raw().request().url().toString() );


                          if( data != null && data.getResponse() != null ) {
                             if( data.getResponse().getResponse_code().equals( "1" ) ) {

                                //Put Login info into preference
                                myProductInfoList = data.getGetProductList().getProductDetails();
                                myBladeTypesList = data.getGetProductList().getBladeTypes();
                                myBladeDescriptionList = data.getGetProductList().getBladeDescription();

                                mLanguageMasterList = data.getGetProductList().getLanguageMaster();

                                mStateMasterList = data.getGetProductList().getStateMaster();
                                mCityMasterList = data.getGetProductList().getCityMaster();
                                mAddressMasterList = data.getGetProductList().getAddressMaster();

                                if( myProductInfoList != null && myProductInfoList.size() > 0 ) {
                                   myAppDatabase.ProductModel().deleteAll();
                                   myAppDatabase.ProductModel().insertAllProduct( myProductInfoList );
                                }

                                if( myBladeTypesList != null && myBladeTypesList.size() > 0 ) {
                                   myAppDatabase.bladeTypesDao().deleteAll();
                                   myAppDatabase.bladeTypesDao().insertAllProduct( myBladeTypesList );
                                }

                                if( myBladeDescriptionList != null && myBladeDescriptionList.size() > 0 ) {
                                   myAppDatabase.bladeDescriptionDao().deleteAll();
                                   myAppDatabase.bladeDescriptionDao().insertAllProduct( myBladeDescriptionList );
                                }


                                if( mLanguageMasterList != null && mLanguageMasterList.size() > 0 ) {
                                   myAppDatabase.languageModel().deleteAll();
                                   myAppDatabase.languageModel().insertAllLanguage( mLanguageMasterList );
                                }


                                if( mStateMasterList != null && mStateMasterList.size() > 0 ) {
                                   myAppDatabase.stateMasterDao().deleteAll();
                                   myAppDatabase.stateMasterDao().insertAllState( mStateMasterList );
                                }

                                if( mCityMasterList != null && mCityMasterList.size() > 0 ) {
                                   myAppDatabase.cityMasterDao().deleteAll();
                                   myAppDatabase.cityMasterDao().insertAllCity( mCityMasterList );
                                }

                                if( mAddressMasterList != null && mAddressMasterList.size() > 0 ) {
                                   myAppDatabase.addressMasterDao().deleteAll();
                                   myAppDatabase.addressMasterDao().insertAllAddress( mAddressMasterList );
                                }

                                TIPrefs.putBoolean( "Login_status", true );
                                getView().callNextActivity();

                             } else
                                TIHelper.showAlertDialog( myContext, data.getResponse().getResponse_message() );

                          }
                       }

                       @Override
                       public void onFailure( @NonNull Call<Data> call, @NonNull Throwable t ) {
                          try {
                             getView().hideProgress();
                             TIHelper.showAlertDialog( myContext, "Something went wrong!" );
                          } catch( Exception e ) {
                             e.printStackTrace();
                          }
                       }
                    } );
         } else {
            TIHelper.showAlertDialog( myContext, myContext.getString( R.string.alert_message_check_internet ) );
         }
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }


   public class DownloadFileFromData extends AsyncTask<List<Product>, String, List<Product>> {

      String aFilaName;
      String aSaveFilePath = "";

      private MGProgressDialog myProgressDialog;

      /**
       * Before starting background thread
       */
      @Override
      protected void onPreExecute() {
         super.onPreExecute();
         myProgressDialog = new MGProgressDialog( myContext );
         myProgressDialog.setMessage( myContext.getString( R.string.label_loading ) );
         myProgressDialog.setCancelable( false );
         // getView().showProgress();
      }

      @SafeVarargs
      @Override
      protected final List<Product> doInBackground( List<Product>... lists ) {
         int count;
         List<Product> myProductList = new ArrayList<>();
         try {

            List<Product> aPath = lists[ 0 ];

            final File aMediaStorageDir = new File( Environment.getExternalStorageDirectory() + File.separator + myContext.getString( R.string.app_name ) );

            if( !aMediaStorageDir.exists() ) {
               boolean mkdirs = aMediaStorageDir.mkdirs();
            }


            if( isCancelled() )
               return null;

            for( Product aProduct : aPath ) {

               URL aUrl = new URL( aProduct.getProdImageUrl() );

               String aUrlPath = aUrl.toString();
               aFilaName = aUrlPath.substring( aUrlPath.lastIndexOf( '/' ) + 1 );
               aSaveFilePath = aMediaStorageDir + File.separator + aFilaName;

               File file = new File( aSaveFilePath );
               if( !file.exists() ) {
                  URLConnection conection = aUrl.openConnection();
                  conection.connect();

                  // getting file length
                  int lenghtOfFile = conection.getContentLength();
                  // input stream to read file - with 8k buffer
                  InputStream input = new BufferedInputStream( aUrl.openStream(), 8192 );
                  // Output stream to write file
                  OutputStream output = new FileOutputStream( aSaveFilePath );

                  byte data[] = new byte[ 1024 ];
                  long total = 0;
                  while( ( count = input.read( data ) ) != -1 ) {
                     total += count;
                     // writing data to file
                     output.write( data, 0, count );
                  }

                  // flushing output
                  output.flush();
                  // closing streams
                  output.close();
                  input.close();
               }
               aProduct.setProductLocalPath( aSaveFilePath );
               myProductList.add( aProduct );

            }

         } catch( Exception e ) {
            Log.e( "Error: ", e.getMessage() );

         }
         return myProductList;
      }


      /**
       * After completing background task
       **/
      @Override
      protected void onPostExecute( List<Product> aResult ) {
         try {
            myAppDatabase.ProductModel().deleteAll();
            myAppDatabase.ProductModel().insertAllProduct( aResult );
            // getView().hideProgress();
            myProgressDialog.dismiss();
            // getView().callBaseActivity();


         } catch( Exception e ) {
            e.printStackTrace();
         }
      }

   }

   public class DownloadBladeImage extends AsyncTask<List<BladeTypes>, String, List<BladeTypes>> {

      String aFilaName;
      String aSaveFilePath = "";
      private MGProgressDialog myProgressDialog;

      /**
       * Before starting background thread
       */
      @Override
      protected void onPreExecute() {
         super.onPreExecute();
         myProgressDialog = new MGProgressDialog( myContext );
         myProgressDialog.setMessage( "loading" );
         myProgressDialog.setCancelable( false );
         getView().showProgress();
      }

      @SafeVarargs
      @Override
      protected final List<BladeTypes> doInBackground( List<BladeTypes>... lists ) {
         int count;
         List<BladeTypes> myProductList = new ArrayList<>();

         try {

            List<BladeTypes> aPath = lists[ 0 ];

            final File aMediaStorageDir = new File( Environment.getExternalStorageDirectory() + File.separator + myContext.getString( R.string.app_name ) );

            if( !aMediaStorageDir.exists() ) {
               boolean mkdirs = aMediaStorageDir.mkdirs();
            }


            if( isCancelled() )
               return null;

            for( BladeTypes aProduct : aPath ) {
               List<String> ImageList = new ArrayList<>();
               ImageList.add( aProduct.getBladeImage1() );
               ImageList.add( aProduct.getBladeImage2() );
               ImageList.add( aProduct.getBladeImage3() );


               for( int i = 0; i < ImageList.size(); i++ ) {

                  URL aUrl1 = new URL( ImageList.get( i ) );

                  String aUrlPath = aUrl1.toString();
                  aFilaName = aUrlPath.substring( aUrlPath.lastIndexOf( '/' ) + 1 );
                  aSaveFilePath = aMediaStorageDir + File.separator + aFilaName;

                  File file = new File( aSaveFilePath );
                  if( !file.exists() ) {
                     URLConnection conection = aUrl1.openConnection();
                     conection.connect();

                     // getting file length
                     int lenghtOfFile = conection.getContentLength();
                     // input stream to read file - with 8k buffer
                     InputStream input = new BufferedInputStream( aUrl1.openStream(), 8192 );
                     // Output stream to write file
                     OutputStream output = new FileOutputStream( aSaveFilePath );

                     byte data[] = new byte[ 1024 ];
                     long total = 0;
                     while( ( count = input.read( data ) ) != -1 ) {
                        total += count;
                        // writing data to file
                        output.write( data, 0, count );
                     }

                     // flushing output
                     output.flush();
                     // closing streams
                     output.close();
                     input.close();
                  }
                  if( i == 0 )
                     aProduct.setLocalBladeImage1( aSaveFilePath );
                  if( i == 1 )
                     aProduct.setLocalBladeImage2( aSaveFilePath );
                  if( i == 2 )
                     aProduct.setLocalBladeImage3( aSaveFilePath );
               }

               myProductList.add( aProduct );
            }

         } catch( Exception e ) {
            Log.e( "Error: ", e.getMessage() );

         }
         return myProductList;
      }


      /**
       * After completing background task
       **/
      @Override
      protected void onPostExecute( List<BladeTypes> aResult ) {
         try {
            myAppDatabase.bladeTypesDao().deleteAll();
            myAppDatabase.bladeTypesDao().insertAllProduct( aResult );
            myProgressDialog.dismiss();
            TIPrefs.putBoolean( "Login_status", true );
            getView().callBaseActivity();


         } catch( Exception e ) {
            e.printStackTrace();
         }
      }

   }

}
