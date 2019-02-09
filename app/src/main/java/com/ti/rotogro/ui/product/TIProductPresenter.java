package com.ti.rotogro.ui.product;

import android.support.v4.app.FragmentActivity;

import com.ti.rotogro.base.BasePresenter;
import com.ti.rotogro.data.db.AppDatabase;
import com.ti.rotogro.data.db.entity.BladeTypes;
import com.ti.rotogro.data.db.entity.Product;
import com.ti.rotogro.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

public class TIProductPresenter extends BasePresenter<TIProductContract.View> implements TIProductContract.Presenter {


   private TIProductContract.View myTiProductView;
   private RetrofitInstance myRetrofitInstance;
   FragmentActivity myContext;
   AppDatabase myAppDatabase;

   TIProductPresenter( TIProductContract.View aTiProductView, FragmentActivity aContext ) {
      myTiProductView = aTiProductView;
      myContext = aContext;
      myAppDatabase = AppDatabase.getDatabase( myContext );

      if( this.myRetrofitInstance == null ) {
         myRetrofitInstance = new RetrofitInstance();
      }
   }


   @Override
   public void initPresenter() {

   }

   @Override
   public void setProductDetail( String aProductId ) {
      getView().showProgress();

      List<BladeTypes> aProductList = myAppDatabase.bladeTypesDao().getAllProducts( aProductId, "1" );

      getView().setViewPagerAdapter( aProductList );
   }


}
