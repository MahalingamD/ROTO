package com.ti.rotogro.ui.main;

import android.support.v7.app.AppCompatActivity;

import com.ti.rotogro.base.BasePresenter;
import com.ti.rotogro.data.db.AppDatabase;
import com.ti.rotogro.preference.TIPrefs;
import com.ti.rotogro.service.RetrofitInstance;

public class TIMainPresenter extends BasePresenter<TIMainContract.View> implements TIMainContract.Presenter {


   AppDatabase myAppDatabase;
   AppCompatActivity myContext;

   private TIMainContract.View myTiMainView;
   private RetrofitInstance myRetrofitInstance;


   public TIMainPresenter( TIMainActivity aTiMainView, AppCompatActivity aContext ) {
      myTiMainView = aTiMainView;
      myContext = aContext;
      myAppDatabase = AppDatabase.getDatabase( myContext.getApplication() );

      if( this.myRetrofitInstance == null ) {
         myRetrofitInstance = new RetrofitInstance();
      }
   }


   @Override
   public void initPresenter() {

      getView().initActivity();

      getView().defaultScreen();
   }

   @Override
   public void onAboutClick() {
      getView().closeNavigationDrawer();
   }

   @Override
   public void onLanguageClick() {
      getView().closeNavigationDrawer();
      getView().showLanguageFragment();
   }

   @Override
   public void onHomeMenuClick() {
      getView().closeNavigationDrawer();
      getView().openHomeFragment();
   }

   @Override
   public void onNavMenuCreated() {

      if( !isViewAttached() ) {
         return;
      }

      getView().updateAppVersion();
      getView().updateUserName( "" );
      getView().updateUserMobile( TIPrefs.getString( "Mob_number", "" ) );
      getView().updateUserProfilePic( "" );
      getView().updateAppVersion();

   }

   @Override
   public void getLanguage() {
      getView().showProgress();


   }

}
