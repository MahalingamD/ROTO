package com.ti.rotogro.ui.contact;


import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ti.rotogro.R;
import com.ti.rotogro.base.BaseFragment;
import com.ti.rotogro.data.db.entity.AddressMaster;
import com.ti.rotogro.data.db.entity.BladeTypes;
import com.ti.rotogro.data.db.entity.CityMaster;
import com.ti.rotogro.data.db.entity.Product;
import com.ti.rotogro.data.db.entity.StateMaster;
import com.ti.rotogro.helper.TIHelper;
import com.ti.rotogro.model.ContactDetail;
import com.ti.rotogro.model.State;

import com.ti.rotogro.ui.contact.adapter.TICitySpinnerAdapter;
import com.ti.rotogro.ui.contact.adapter.TIContactRecyclerAdapter;
import com.ti.rotogro.ui.contact.adapter.TIStateSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple TIContactFragment subclass.
 */
public class TIContactFragment extends BaseFragment implements TIContactContract.View, View.OnClickListener {

   public static String TAG = TIContactFragment.class.getSimpleName();

   private TIContactPresenter myTiContactPresenter;
   private TIStateSpinnerAdapter myStateAdapter;
   private TICitySpinnerAdapter mCitySpinnerAdapter;
   private TIContactRecyclerAdapter myContactAdapter;

   List<AddressMaster> mAddressMasterList;
   String mSelectedState;
   String mSelectedCity;
   LinearLayout mNoDataLayout;
   LinearLayout mStateLayout;
   LinearLayout mCityLayout;
   private BladeTypes myBladeTypes;
   TextView mProductDetailTxt;


   public TIContactFragment() {
      // Required empty public constructor
   }

   private FragmentActivity myContext;

   Spinner myStateSpinner, myCitySpinner;
   RecyclerView myContactRecyclerView;

   @Override
   public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
      // Inflate the layout for this fragment
      View aView = inflater.inflate( R.layout.fragment_ticontact, container, false );

      getBundle();

      myTiContactPresenter = new TIContactPresenter( this, myContext );
      myTiContactPresenter.attachView( this );
      myTiContactPresenter.initPresenter();
      return aView;
   }


   private void getBundle() {
      Bundle aBundle = getArguments();
      if( aBundle != null ) {
         myBladeTypes = ( BladeTypes ) aBundle.getSerializable( "PassType" );
      }
   }

   @Override
   protected void setUp( View aView ) {

      myContext = getActivity();
      mAddressMasterList = new ArrayList<>();

      mNoDataLayout = aView.findViewById( R.id.nodata_layout );
      mProductDetailTxt = aView.findViewById( R.id.product_contact_detail );

      mStateLayout = aView.findViewById( R.id.state_spinner_layout );
      mCityLayout = aView.findViewById( R.id.city_spinner_layout );

      myStateSpinner = aView.findViewById( R.id.fragment_state_spinner );
      myCitySpinner = aView.findViewById( R.id.fragment_city_spinner );
      myContactRecyclerView = aView.findViewById( R.id.fragment_contact_recyclerView );

      RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( myContext, LinearLayoutManager.VERTICAL, false );
      myContactRecyclerView.setLayoutManager( layoutManager );

      myContactAdapter = new TIContactRecyclerAdapter( myContext, mAddressMasterList, recyclerItemClickListener );
      myContactRecyclerView.setAdapter( myContactAdapter );

      myTiContactPresenter.getStateValues();

      mStateLayout.setOnClickListener( this );
      mCityLayout.setOnClickListener( this );

      mProductDetailTxt.setText( myBladeTypes.getBladeTypeName() );
   }

   @Override
   public void onResumeFragment() {

   }

   @Override
   public void onBackPressed() {

   }

   @Override
   public void setStateSpinner( List<StateMaster> aStateMasterList ) {
      hideProgress();
      Resources res = getResources();
      myStateAdapter = new TIStateSpinnerAdapter( myContext, R.layout.spinner_rows, aStateMasterList, res );
      myStateSpinner.setAdapter( myStateAdapter );
      myStateSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected( AdapterView<?> adapterView, View view, int i, long l ) {
            StateMaster stateMaster = ( StateMaster ) adapterView.getItemAtPosition( i );
            mSelectedState = stateMaster.getStateId();
            myTiContactPresenter.getCityValues( mSelectedState );
         }

         @Override
         public void onNothingSelected( AdapterView<?> adapterView ) {
         }
      } );
   }

   @Override
   public void setCitySpinner( List<CityMaster> aCityMasterList ) {
      hideProgress();
      if( aCityMasterList != null && aCityMasterList.size() > 0 ) {
         mNoDataLayout.setVisibility( View.GONE );
         myContactRecyclerView.setVisibility( View.VISIBLE );
      } else {
         TIHelper.showAlertDialog( myContext, myContext.getResources().getString( R.string.label_nocity ) );
         mNoDataLayout.setVisibility( View.VISIBLE );
         myContactRecyclerView.setVisibility( View.GONE );
      }

      Resources res = getResources();
      mCitySpinnerAdapter = new TICitySpinnerAdapter( myContext, R.layout.spinner_rows, aCityMasterList, res );
      myCitySpinner.setAdapter( mCitySpinnerAdapter );

      myCitySpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected( AdapterView<?> adapterView, View view, int i, long l ) {
            CityMaster cityMaster = ( CityMaster ) adapterView.getItemAtPosition( i );
            mSelectedCity = cityMaster.getCityId();
            myTiContactPresenter.getContactValues( mSelectedState, mSelectedCity );
         }

         @Override
         public void onNothingSelected( AdapterView<?> adapterView ) {

         }
      } );
   }


   @Override
   public void initActivity() {
   }

   @Override
   public void setRecyclerAdapter( List<AddressMaster> aAddressMasterList ) {

      if( aAddressMasterList != null && aAddressMasterList.size() > 0 ) {
         mNoDataLayout.setVisibility( View.GONE );
         myContactRecyclerView.setVisibility( View.VISIBLE );
         myContactAdapter.updateAdapter( aAddressMasterList );
      } else {
         mNoDataLayout.setVisibility( View.VISIBLE );
         myContactRecyclerView.setVisibility( View.GONE );
      }
   }


   /**
    * RecyclerItem click event listener
    */
   private RecyclerItemClickListener recyclerItemClickListener = aContactDetail -> {
      Intent intent = new Intent( Intent.ACTION_CALL );
      intent.setData( Uri.parse( "tel:" + aContactDetail.getMobileNumber() ) );
      startActivity( intent );
   };


   @Override
   public void onClick( View view ) {
      switch( view.getId() ) {
         case R.id.state_spinner_layout:
            myStateSpinner.performClick();
            break;

         case R.id.city_spinner_layout:
            myCitySpinner.performClick();
            break;
      }
   }
}
