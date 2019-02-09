package com.ti.rotogro.ui.splash;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.ti.rotogro.base.BasePresenter;
import com.ti.rotogro.helper.TIHelper;
import com.ti.rotogro.model.Data;
import com.ti.rotogro.model.VersionDetails;
import com.ti.rotogro.service.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TISplashPresenter extends BasePresenter<TISplashContract.View> implements TISplashContract.Presenter {


   private TISplashContract.View myTiSplashView;
   private RetrofitInstance myRetrofitInstance;
   AppCompatActivity myAppContext;


   public TISplashPresenter( TISplashActivity tiSplashActivity, AppCompatActivity aAppContext ) {
      myTiSplashView = tiSplashActivity;
      myAppContext = aAppContext;

      if( this.myRetrofitInstance == null ) {
         myRetrofitInstance = new RetrofitInstance();
      }
   }

   @Override
   public void initPresenter() {

      getView().initActivity();
      getView().viewSplashCount();

   }

   @Override
   public void checkAppUpdate( String aVersionName ) {
      if( getView().checkInternet() ) {

         myRetrofitInstance.getAPI()
                 .getAppUpdate( aVersionName )
                 .enqueue( new Callback<Data>() {
                    @Override
                    public void onResponse( @NonNull Call<Data> call, @NonNull Response<Data> response ) {

                       getView().hideProgress();
                       Data data = response.body();

                       if( data != null && data.getResponse() != null ) {
                          String aString = data.getResponse().getResponse_code();
                          if( aString.equals( "1" ) ) {
                             VersionDetails aVersionDetails = data.getVersionDetails();
                             getView().appUpdate( aVersionDetails );
                          } else {
                             getView().callBaseActivity();
                          }
                       }
                    }

                    @Override
                    public void onFailure( @NonNull Call<Data> call, @NonNull Throwable t ) {
                       try {
                          getView().hideProgress();
                          TIHelper.showAlertDialog( myAppContext, "Something went wrong!" );
                       } catch( Exception e ) {
                          e.printStackTrace();
                       }
                    }
                 } );

      }
   }

}
