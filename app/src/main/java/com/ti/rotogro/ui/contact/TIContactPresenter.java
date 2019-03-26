package com.ti.rotogro.ui.contact;

import android.support.v4.app.FragmentActivity;

import com.ti.rotogro.base.BasePresenter;
import com.ti.rotogro.data.db.AppDatabase;
import com.ti.rotogro.data.db.entity.AddressMaster;
import com.ti.rotogro.data.db.entity.CityMaster;
import com.ti.rotogro.data.db.entity.LanguageMaster;
import com.ti.rotogro.data.db.entity.StateMaster;
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
   public void getCityValues( String aStateId, LanguageMaster aLanguage ) {
      List<CityMaster> aCityMasterList = new ArrayList<>();

      switch( aLanguage.Lan_id ) {
         case "1":
            if( aStateId.equals( "4" ) ) {
               aCityMasterList = mAppDatabase.cityMasterDao().getAllSelectedCity( aStateId, aLanguage.Lan_id, "1" );
            } else {
               aCityMasterList = mAppDatabase.cityMasterDao().getAllSelectedCity( aStateId, "2", "1" );
            }
            break;
         case "3":
            if( aStateId.equals( "3" ) || aStateId.equals( "5" ) ) {
               aCityMasterList = mAppDatabase.cityMasterDao().getAllSelectedCity( aStateId, aLanguage.Lan_id, "1" );
            } else {
               aCityMasterList = mAppDatabase.cityMasterDao().getAllSelectedCity( aStateId, "2", "1" );
            }
            break;

         case "4":
            if( aStateId.equals( "2" ) ) {
               aCityMasterList = mAppDatabase.cityMasterDao().getAllSelectedCity( aStateId, aLanguage.Lan_id, "1" );
            } else {
               aCityMasterList = mAppDatabase.cityMasterDao().getAllSelectedCity( aStateId, "2", "1" );
            }
            break;
         default:
            aCityMasterList = mAppDatabase.cityMasterDao().getAllSelectedCity( aStateId, "2", "1" );
            break;
      }

      myTiContractView.setCitySpinner( aCityMasterList );
   }

   @Override
   public void getContactValues( String aStateId, String aCityId, LanguageMaster aLanguage ) {
      List<AddressMaster> aAddressMasterList = new ArrayList<>();


      switch( aLanguage.Lan_id ) {
         case "1":
            if( aStateId.equals( "4" ) ) {
               aAddressMasterList = mAppDatabase.addressMasterDao().getAllSelectedAddress( aStateId, aCityId, aLanguage.Lan_id, "1" );
            } else {
               aAddressMasterList = mAppDatabase.addressMasterDao().getAllSelectedAddress( aStateId, aCityId, "2", "1" );
            }
            break;
         case "3":
            if( aStateId.equals( "3" ) || aStateId.equals( "5" ) ) {
               aAddressMasterList = mAppDatabase.addressMasterDao().getAllSelectedAddress( aStateId, aCityId, aLanguage.Lan_id, "1" );
            } else {
               aAddressMasterList = mAppDatabase.addressMasterDao().getAllSelectedAddress( aStateId, aCityId, "2", "1" );
            }
            break;

         case "4":
            if( aStateId.equals( "2" ) ) {
               aAddressMasterList = mAppDatabase.addressMasterDao().getAllSelectedAddress( aStateId, aCityId, aLanguage.Lan_id, "1" );
            } else {
               aAddressMasterList = mAppDatabase.addressMasterDao().getAllSelectedAddress( aStateId, aCityId, "2", "1" );
            }
            break;
         default:
            aAddressMasterList = mAppDatabase.addressMasterDao().getAllSelectedAddress( aStateId, aCityId, aLanguage.Lan_id, "1" );
            break;
      }

      //  aAddressMasterList = mAppDatabase.addressMasterDao().getAllAddress( aStateId, aCityId, "1" );
      myTiContractView.setRecyclerAdapter( aAddressMasterList );
   }


}
