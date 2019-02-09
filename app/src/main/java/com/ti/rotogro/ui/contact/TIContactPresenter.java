package com.ti.rotogro.ui.contact;

import android.support.v4.app.FragmentActivity;

import com.ti.rotogro.base.BasePresenter;
import com.ti.rotogro.data.db.AppDatabase;
import com.ti.rotogro.data.db.entity.AddressMaster;
import com.ti.rotogro.data.db.entity.CityMaster;
import com.ti.rotogro.data.db.entity.StateMaster;
import com.ti.rotogro.model.ContactDetail;
import com.ti.rotogro.model.State;
import com.ti.rotogro.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

public class TIContactPresenter extends BasePresenter<TIContactContract.View> implements TIContactContract.Presenter {


   private TIContactContract.View myTiContractView;
   private RetrofitInstance myRetrofitInstance;
   AppDatabase mAppDatabase;
   FragmentActivity myContext;

   TIContactPresenter( TIContactContract.View aTiContractView, FragmentActivity aContext ) {
      myTiContractView = aTiContractView;
      myContext = aContext;

      mAppDatabase = AppDatabase.getDatabase( myContext );

      if( this.myRetrofitInstance == null ) {
         myRetrofitInstance = new RetrofitInstance();
      }
   }

   @Override
   public void initPresenter() {

      getView().initActivity();

      getView().showProgress();
   }

   @Override
   public void getStateValues() {
      List<StateMaster> aStateMasters = new ArrayList<>();

      aStateMasters = mAppDatabase.stateMasterDao().getAllState( "1" );

      myTiContractView.setStateSpinner( aStateMasters );
   }

   @Override
   public void getCityValues( String aStateId ) {
      List<CityMaster> aCityMasterList = new ArrayList<>();

      aCityMasterList = mAppDatabase.cityMasterDao().getAllCity( aStateId, "1" );

      myTiContractView.setCitySpinner( aCityMasterList );
   }

   @Override
   public void getContactValues( String aStateId, String aCityId ) {
      List<AddressMaster> aAddressMasterList = new ArrayList<>();

      aAddressMasterList = mAppDatabase.addressMasterDao().getAllAddress( aStateId, aCityId, "1" );
      myTiContractView.setRecyclerAdapter( aAddressMasterList );
   }


}
