package com.ti.rotogro.ui.productdetail;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ti.rotogro.R;
import com.ti.rotogro.base.APPFragmentManager;
import com.ti.rotogro.base.BaseFragment;
import com.ti.rotogro.data.db.entity.BladeDescription;
import com.ti.rotogro.data.db.entity.BladeTypes;
import com.ti.rotogro.data.db.entity.Product;
import com.ti.rotogro.ui.contact.TIContactFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple TIProductDetailFragment class.
 * author Mahalingam.D 23-01-2019
 */
public class TIProductDetailFragment extends BaseFragment implements TIProductDetailContract.View, View.OnClickListener {


   public static String TAG = TIProductDetailFragment.class.getSimpleName();
   private FragmentActivity myContext;

   private TIProductDetailPresenter myTiProductDetailPresenter;
   public ViewPager myViewPager;
   List<Product> myProductList;
   APPFragmentManager myFragmentManager;
   TIProductDetailAdapter myAdapter;

   ImageView myPageLeftImage, myPageRightImage;
   BladeTypes myBladeTypes, mCurrentBladeType;
   Product myProduct;

   private TextView myPageCallTXT;

   private int myCurrentPosition = 0;
   int passPosition = 0;
   List<BladeDescription> myDetailList;
   List<BladeTypes> myBladeTypesList;

   @Override
   public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

      View aView = inflater.inflate( R.layout.fragment_product_detail, container, false );

      myContext = getActivity();
      getBundle();
      myTiProductDetailPresenter = new TIProductDetailPresenter( this, myContext );
      myTiProductDetailPresenter.attachView( this );
      myTiProductDetailPresenter.initPresenter();

      return aView;
   }

   private void getBundle() {
      Bundle aBundle = getArguments();
      if( aBundle != null ) {
         passPosition = aBundle.getInt( "position" );
         myBladeTypes = ( BladeTypes ) aBundle.getSerializable( "bladetype" );
         myProduct = ( Product ) aBundle.getSerializable( "product" );
         myCurrentPosition = passPosition;
         mCurrentBladeType = myBladeTypes;
      }
   }

   @Override
   protected void setUp( View aView ) {

      myFragmentManager = new APPFragmentManager( myContext );
      myProductList = new ArrayList<>();

      myViewPager = aView.findViewById( R.id.product_detail_viewpager );
      myPageLeftImage = aView.findViewById( R.id.fragment_page_left_image );
      myPageRightImage = aView.findViewById( R.id.fragment_page_right_image );
      myPageCallTXT = aView.findViewById( R.id.fragment_page_call_Text );


      myPageLeftImage.setOnClickListener( this );
      myPageRightImage.setOnClickListener( this );
      myPageCallTXT.setOnClickListener( this );

      myViewPager.setOffscreenPageLimit( 1 );
      myViewPager.setPageMargin( 40 );

      myTiProductDetailPresenter.getBladeTypes( myProduct.getProductId() );
      myTiProductDetailPresenter.setProductDetail( myBladeTypes.getBladeTypeId() );
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
   public void setViewPagerAdapter( List<BladeDescription> aProductDetailList ) {
      hideProgress();

      myDetailList = aProductDetailList;
      myAdapter = new TIProductDetailAdapter( myContext, myDetailList, myBladeTypesList );
      myViewPager.setAdapter( myAdapter );
      myViewPager.setCurrentItem( myCurrentPosition );

      setNavigation( myCurrentPosition );


      myViewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
         @Override
         public void onPageScrolled( int i, float v, int i1 ) {
         }

         @Override
         public void onPageSelected( int i ) {
            myCurrentPosition = i;
            mCurrentBladeType = myBladeTypesList.get( myCurrentPosition );
            setNavigation( myCurrentPosition );
         }

         @Override
         public void onPageScrollStateChanged( int i ) {
         }
      } );

   }

   @Override
   public void setBladeTypeDetails( List<BladeTypes> aBladeTypes ) {
      myBladeTypesList = aBladeTypes;
   }

   private void setNavigation( int myCurrentPosition ) {

      if( myCurrentPosition == 0 ) {
         DrawableCompat.setTint( myPageLeftImage.getDrawable(), ContextCompat.getColor( myContext, R.color.light_gray ) );
         myPageLeftImage.setEnabled( false );
      } else {
         DrawableCompat.setTint( myPageLeftImage.getDrawable(), ContextCompat.getColor( myContext, R.color.black ) );
         myPageLeftImage.setEnabled( true );
      }

      if( myCurrentPosition == myBladeTypesList.size() - 1 ) {
         DrawableCompat.setTint( myPageRightImage.getDrawable(), ContextCompat.getColor( myContext, R.color.light_gray ) );
         myPageRightImage.setEnabled( false );
      } else {
         DrawableCompat.setTint( myPageRightImage.getDrawable(), ContextCompat.getColor( myContext, R.color.black ) );
         myPageRightImage.setEnabled( true );
      }

   }

   @Override
   public void onClick( View view ) {
      switch( view.getId() ) {

         case R.id.fragment_page_left_image: {
            myViewPager.setCurrentItem( myCurrentPosition - 1 );
            break;
         }
         case R.id.fragment_page_right_image: {
            myViewPager.setCurrentItem( myCurrentPosition + 1 );
            break;
         }
         case R.id.fragment_page_call_Text: {
            if( mCurrentBladeType != null ) {
               Bundle aBundle = new Bundle();
               aBundle.putSerializable( "PassType", mCurrentBladeType );
               myFragmentManager.updateContent( new TIContactFragment(), TIContactFragment.TAG, aBundle );
            }
            break;
         }
         default:
            break;

      }
   }
}
