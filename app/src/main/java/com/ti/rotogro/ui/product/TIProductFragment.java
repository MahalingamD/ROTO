package com.ti.rotogro.ui.product;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ti.rotogro.R;
import com.ti.rotogro.base.APPFragmentManager;
import com.ti.rotogro.base.BaseFragment;
import com.ti.rotogro.data.db.entity.BladeTypes;
import com.ti.rotogro.data.db.entity.Product;
import com.ti.rotogro.utils.RotateDownPageTransformer;

import java.util.ArrayList;
import java.util.List;


public class TIProductFragment extends BaseFragment implements TIProductContract.View {

   public static String TAG = TIProductFragment.class.getSimpleName();

   private TIProductPresenter myTiProductPresenter;

   private ViewPager mViewPager;
   private int viewHeight = 0;
   private FragmentActivity myContext;
   List<Product> myProductList;
   APPFragmentManager myFragmentManager;

   View mIncludedLayout;

   Product myProduct;
   String myProductId = "";


   @Override
   public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
      // Inflate the layout for this fragment
      View aView = inflater.inflate( R.layout.fragment_tiproduct, container, false );

      myContext = getActivity();
      getBundle();
      myTiProductPresenter = new TIProductPresenter( this, myContext );
      myTiProductPresenter.attachView( this );
      myTiProductPresenter.initPresenter();

      return aView;
   }


   private void getBundle() {
      try {
         Bundle aBundle = getArguments();
         if( aBundle != null ) {
            Bundle args = getArguments();
            myProduct = ( Product ) args.getSerializable( "Product" );
            if( myProduct != null ) {
               myProductId = myProduct.getProductId();
            }
         }
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   @Override
   protected void setUp( View aView ) {
      myContext = getActivity();
      myFragmentManager = new APPFragmentManager( myContext );
      myProductList = new ArrayList<>();

      mIncludedLayout = aView.findViewById( R.id.inflate_empty );
      mViewPager = aView.findViewById( R.id.viewpager );
      mViewPager.setOffscreenPageLimit( 3 );
      mViewPager.setPageMargin( 40 );

      myTiProductPresenter.setProductDetail( myProductId );
   }

   @Override
   public void onResumeFragment() {
   }

   @Override
   public void onBackPressed() {
   }


   @Override
   public void initFragment() {
   }

   @Override
   public void setViewPagerAdapter( List<BladeTypes> aProductList ) {
      hideProgress();

      if( aProductList != null && aProductList.size() > 0 ) {
         mIncludedLayout.setVisibility( View.GONE );
         mViewPager.setVisibility( View.VISIBLE );
         TIProductViewPagerAdapter aPager = new TIProductViewPagerAdapter( aProductList, myContext, myProduct );
         mViewPager.setAdapter( aPager );
         mViewPager.setPageMargin( 40 );
         mViewPager.setPageTransformer( true, new RotateDownPageTransformer() );
      } else {
         mIncludedLayout.setVisibility( View.VISIBLE );
         mViewPager.setVisibility( View.GONE );
      }
   }

}
