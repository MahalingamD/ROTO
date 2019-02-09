package com.ti.rotogro.ui.home;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ti.rotogro.BuildConfig;
import com.ti.rotogro.R;
import com.ti.rotogro.base.BaseActivity;
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
import com.ti.rotogro.model.VersionDetails;
import com.ti.rotogro.preference.TIPrefs;
import com.ti.rotogro.service.RetrofitInstance;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TIHomePresenter extends BasePresenter<TIHomeContract.View> implements TIHomeContract.Presenter {


   private TIHomeContract.View myTIHomeView;
   private RetrofitInstance myRetrofitInstance;
   BaseActivity myContext;

   AppDatabase myAppDatabase;
   private List<Product> myProductInfoList;
   private List<BladeTypes> myBladeTypesList;
   private List<BladeDescription> myBladeDescriptionList;
   private List<LanguageMaster> mLanguageMasterList;
   private List<StateMaster> mStateMasterList;
   private List<CityMaster> mCityMasterList;
   private List<AddressMaster> mAddressMasterList;


   public TIHomePresenter( TIHomeFragment aTiHomeView, BaseActivity aContext ) {
      myTIHomeView = aTiHomeView;
      myContext = aContext;
      myAppDatabase = AppDatabase.getDatabase( myContext );

      if( this.myRetrofitInstance == null ) {
         myRetrofitInstance = new RetrofitInstance();
      }
   }


   @Override
   public void initPresenter() {
      getView().initFragment();
      getView().getViewHeight();
      getView().showProgress();

      //  new DownloadFileFromData().execute( "https://www.gstatic.com/webp/gallery3/1.png" );
      // file_download( "http://www.freeimageslive.com/galleries/objects/general/pics/woodenbox0482.jpg" );
   }


   @Override
   public void setProductDetail( LanguageMaster aLanguageid ) {

      getView().showProgress();

      List<Product> aProductList = myAppDatabase.ProductModel().getAllsProducts( aLanguageid.getLan_id(), "1" );

      getView().setRecyclerViewAdapter( aProductList );
   }

   @Override
   public void checkVersionUpdate() {

      if( getView().checkInternet() ) {

         myRetrofitInstance.getAPI()
                 .getAppUpdate( BuildConfig.VERSION_NAME )
                 .enqueue( new Callback<Data>() {
                    @Override
                    public void onResponse( @NonNull Call<Data> call, @NonNull Response<Data> response ) {

                       // getView().hideProgress();
                       Data data = response.body();

                       if( data != null && data.getResponse() != null ) {
                          String aString = data.getResponse().getResponse_code();
                          if( aString.equals( "1" ) ) {
                             VersionDetails aVersionDetails = data.getVersionDetails();

                             if( aVersionDetails != null ) {
                                if( !aVersionDetails.getVersionName().equals( BuildConfig.VERSION_NAME ) ) {

                                   switch( aVersionDetails.getStatus() ) {
                                      case "1":
                                         getView().showUpdateDialog( aVersionDetails.getMessage() );
                                         break;
                                      case "2":
                                         getView().showMandatoryUpdateDialog( aVersionDetails.getMessage() );
                                         break;
                                      default:
                                         break;
                                   }
                                }
                             }
                          }
                       }
                    }

                    @Override
                    public void onFailure( @NonNull Call<Data> call, @NonNull Throwable t ) {
                       try {
                          Log.e( "Error", t.getMessage().toString() );
                          //getView().hideProgress();
                          //  TIHelper.showAlertDialog( myContext, "Something went wrong!" );
                       } catch( Exception e ) {
                          e.printStackTrace();
                       }
                    }
                 } );
      }

   }

   @Override
   public void checkProductUpdate() {
      // TIPrefs.putString( "LastUpdate", "2019-02-04 11:40:52.810" );

      if( getView().checkInternet() ) {
         getView().showProgress();
         myRetrofitInstance.getAPI()
                 .updateProduct( TIPrefs.getString( "LastUpdate", "" ) )
                 .enqueue( new Callback<Data>() {
                    @Override
                    public void onResponse( @NonNull Call<Data> call, @NonNull Response<Data> response ) {

                       try {
                          Data data = response.body();

                          if( data != null && data.getResponse() != null ) {
                             String aString = data.getResponse().getResponse_code();
                             if( aString.equals( "1" ) ) {

                                //Put Login info into preference
                                myProductInfoList = data.getGetProductList().getProductDetails();
                                myBladeTypesList = data.getGetProductList().getBladeTypes();
                                myBladeDescriptionList = data.getGetProductList().getBladeDescription();

                                mLanguageMasterList = data.getGetProductList().getLanguageMaster();

                                mStateMasterList = data.getGetProductList().getStateMaster();
                                mCityMasterList = data.getGetProductList().getCityMaster();
                                mAddressMasterList = data.getGetProductList().getAddressMaster();

                                if( myProductInfoList != null && myProductInfoList.size() > 0 ) {
                                   myAppDatabase.ProductModel().insertAllProduct( myProductInfoList );
                                }

                                if( myBladeTypesList != null && myBladeTypesList.size() > 0 ) {
                                   myAppDatabase.bladeTypesDao().insertAllProduct( myBladeTypesList );
                                }

                                if( myBladeDescriptionList != null && myBladeDescriptionList.size() > 0 ) {
                                   myAppDatabase.bladeDescriptionDao().insertAllProduct( myBladeDescriptionList );
                                }


                               /* if( mLanguageMasterList != null && mLanguageMasterList.size() > 0 ) {
                                   myAppDatabase.languageModel().insertAllLanguage( mLanguageMasterList );
                                }


                                if( mStateMasterList != null && mStateMasterList.size() > 0 ) {
                                   myAppDatabase.stateMasterDao().insertAllState( mStateMasterList );
                                }

                                if( mCityMasterList != null && mCityMasterList.size() > 0 ) {
                                   myAppDatabase.cityMasterDao().insertAllCity( mCityMasterList );
                                }

                                if( mAddressMasterList != null && mAddressMasterList.size() > 0 ) {
                                   myAppDatabase.addressMasterDao().insertAllAddress( mAddressMasterList );
                                }*/
                                LanguageMaster aLanguageMaster = TIPrefs.getObject( "language", LanguageMaster.class );

                                if( aLanguageMaster != null ) {
                                   List<Product> aProductList = myAppDatabase.ProductModel().getAllsProducts( aLanguageMaster.getLan_id(), "1" );
                                   getView().setRecyclerViewAdapter( aProductList );
                                }

                                getView().hideProgress();
                               /* DateFormat df = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss.SSS" );
                                String date = df.format( Calendar.getInstance().getTime() );
                                TIPrefs.putString( "LastUpdate", date );*/
                             }
                          }
                       } catch( Exception e ) {
                          getView().hideProgress();
                          e.printStackTrace();
                       }
                    }

                    @Override
                    public void onFailure( @NonNull Call<Data> call, @NonNull Throwable t ) {
                       try {
                          getView().hideProgress();
                          Log.e( "Error", t.getMessage().toString() );
                          //getView().hideProgress();
                          //  TIHelper.showAlertDialog( myContext, "Something went wrong!" );
                       } catch( Exception e ) {
                          e.printStackTrace();
                       }
                    }
                 } );
      }
   }

   public class DownloadFileFromData extends AsyncTask<String, String, Void> {

      String aFilaName;
      String aSaveFilePath = "";


      /**
       * Before starting background thread
       */
      @Override
      protected void onPreExecute() {
         super.onPreExecute();
      }

      /**
       * Downloading file in background thread
       */
      @Override
      protected Void doInBackground( String... params ) {
         int count;

         try {

            String aPath = params[ 0 ];

            final File aMediaStorageDir = new File( Environment.getExternalStorageDirectory() + File.separator + myContext.getString( R.string.app_name ) );

            if( !aMediaStorageDir.exists() ) {
               boolean mkdirs = aMediaStorageDir.mkdirs();
            }
            URL aUrl = new URL( aPath );

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

         } catch( Exception e ) {
            Log.e( "Error: ", e.getMessage() );
         }

         return null;
      }


      /**
       * After completing background task
       **/
      @Override
      protected void onPostExecute( Void process ) {

         try {
            getView().hideProgress();

         } catch( Exception e ) {
            e.printStackTrace();
         }
      }

   }
}
