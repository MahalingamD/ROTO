package com.ti.rotogro.ui.home;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ti.rotogro.R;
import com.ti.rotogro.helper.TIHelper;
import com.ti.rotogro.ui.home.adapter.TIProductRecyclerAdapter;
import com.ti.rotogro.base.APPFragmentManager;
import com.ti.rotogro.base.BaseActivity;
import com.ti.rotogro.base.BaseFragment;
import com.ti.rotogro.data.db.entity.LanguageMaster;
import com.ti.rotogro.data.db.entity.Product;
import com.ti.rotogro.preference.TIPrefs;
import com.ti.rotogro.ui.common.TICommonFragment;
import com.ti.rotogro.ui.product.TIProductFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TIHomeFragment extends BaseFragment implements TIHomeContract.View {


   public static String TAG = TIHomeFragment.class.getSimpleName();

   private TIHomePresenter myTiMainPresenter;
   RecyclerView myProductRecyclerView;
   private int viewHeight = 0;
   private BaseActivity myContext;
   private TIProductRecyclerAdapter myTIProductAdapter;
   List<Product> myProductList;
   APPFragmentManager myFragmentManager;

   public static TIHomeFragment newInstance() {
      return new TIHomeFragment();
   }

   @Override
   public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState ) {
      View aView = inflater.inflate( R.layout.home_fragment, container, false );

      myContext = getBaseActivity();
      myTiMainPresenter = new TIHomePresenter( this, myContext );
      myTiMainPresenter.attachView( this );
      myTiMainPresenter.initPresenter();


      return aView;
   }


   @Override
   public void initFragment() {


   }

   @Override
   public void getViewHeight() {
      try {
         // status bar height
         int statusBarHeight = 0;
         int resourceId = getResources().getIdentifier( "status_bar_height", "dimen", "android" );
         if( resourceId > 0 ) {
            statusBarHeight = getResources().getDimensionPixelSize( resourceId );
         }

         // action bar height
         int actionBarHeight = 0;
         final TypedArray styledAttributes = getActivity().getTheme().obtainStyledAttributes(
                 new int[]{ android.R.attr.actionBarSize }
         );
         actionBarHeight = ( int ) styledAttributes.getDimension( 0, 0 );
         styledAttributes.recycle();

         viewHeight = statusBarHeight + actionBarHeight;

         viewHeight = viewHeight / 2;
      } catch( Resources.NotFoundException e ) {
         e.printStackTrace();
      }
   }

   @Override
   public void setRecyclerViewAdapter( List<Product> aProductList ) {

      hideProgress();
      myProductList = aProductList;
      myTIProductAdapter.updateAdapter( aProductList, viewHeight );
   }


   @Override
   protected void setUp( View aView ) {

      myFragmentManager = new APPFragmentManager( myContext );
      myProductList = new ArrayList<>();

      myProductRecyclerView = aView.findViewById( R.id.fragment_home_product_RECYCLE );

      LinearLayoutManager mLayoutManager = new LinearLayoutManager( myContext );
      myProductRecyclerView.setLayoutManager( mLayoutManager );

      configRecycler();


      myTiMainPresenter.checkVersionUpdate();

      myTiMainPresenter.checkProductUpdate();

      myTiMainPresenter.setProductDetail( TIPrefs.getObject( "language", LanguageMaster.class ) );

      viewHeight = TIHelper.getViewHeight( myContext );


   }

   @Override
   public void onResumeFragment() {

   }

   @Override
   public void onBackPressed() {
      getBaseActivity().exitApp();
   }


   /**
    * RecyclerItem click event listener
    */
   private HomeRecyclerItemClickListener recyclerItemClickListener = new HomeRecyclerItemClickListener() {
      @Override
      public void onItemClick( int position, Product aProduct ) {
         if( position == 0 )
            CallNextFragment( aProduct );
         if( position == 1 )
            CallDefaultFragment( aProduct );
      }

   };

   private void CallDefaultFragment( Product aProduct ) {
      myFragmentManager.updateContent( new TICommonFragment(), TICommonFragment.TAG, null );
   }

   private void CallNextFragment( Product aProduct ) {
      Bundle aBundle = new Bundle();
      aBundle.putSerializable( "Product", aProduct );
      myFragmentManager.updateContent( new TIProductFragment(), TIProductFragment.TAG, aBundle );
   }

   private void configRecycler() {
      GridLayoutManager layoutManager = new GridLayoutManager( myContext, 2 );
      layoutManager.setSpanSizeLookup( new GridLayoutManager.SpanSizeLookup() {
         @Override
         public int getSpanSize( int position ) {
            int aSize = myProductList.size();
            if( aSize == 1 )
               return 2;
            else if( aSize == 2 )
               return 2;
            else {
               return 1;
            }
         }
      } );
      myProductRecyclerView.setLayoutManager( layoutManager );
      myTIProductAdapter = new TIProductRecyclerAdapter( myContext, myProductList, viewHeight, recyclerItemClickListener );
      myProductRecyclerView.setAdapter( myTIProductAdapter );
      myProductRecyclerView.setNestedScrollingEnabled( false );
      myProductRecyclerView.setHasFixedSize( true );
   }

}
