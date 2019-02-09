package com.ti.rotogro.ui.productdetail;

import android.support.v4.app.FragmentActivity;

import com.ti.rotogro.R;
import com.ti.rotogro.base.BasePresenter;
import com.ti.rotogro.data.db.AppDatabase;
import com.ti.rotogro.data.db.entity.BladeDescription;
import com.ti.rotogro.data.db.entity.BladeTypes;
import com.ti.rotogro.data.db.entity.Product;
import com.ti.rotogro.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TIProductDetailPresenter extends BasePresenter<TIProductDetailContract.View> implements TIProductDetailContract.Presenter {


   private TIProductDetailContract.View myTiProductView;
   private RetrofitInstance myRetrofitInstance;
   FragmentActivity myContext;
   AppDatabase myAppDatabase;

   TIProductDetailPresenter( TIProductDetailContract.View aTiProductDetailView, FragmentActivity aContext ) {
      myTiProductView = aTiProductDetailView;
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
   public void setProductDetail( String aBladeType ) {
      getView().showProgress();
      List<BladeDescription> aBladeDescriptions = myAppDatabase.bladeDescriptionDao().getAllBladeDescriptions( aBladeType, "1" );
      getView().setViewPagerAdapter( aBladeDescriptions );
   }

   @Override
   public void getBladeTypes( String aProductId ) {
      List<BladeTypes> aProductList = myAppDatabase.bladeTypesDao().getAllProducts( aProductId, "1" );
      getView().setBladeTypeDetails( aProductList );
   }


}
