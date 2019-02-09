package com.ti.rotogro.ui.productdetail;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ti.rotogro.R;
import com.ti.rotogro.base.APPFragmentManager;
import com.ti.rotogro.data.db.AppDatabase;
import com.ti.rotogro.data.db.entity.BladeDescription;
import com.ti.rotogro.data.db.entity.BladeTypes;
import com.ti.rotogro.helper.TIHelper;

import java.io.File;
import java.util.List;


public class TIProductDetailAdapter extends PagerAdapter {

   private FragmentActivity myContext;
   private APPFragmentManager myFragmentManager;

   List<BladeTypes> myBladeTypesList;
   AppDatabase myAppDatabase;

   private RecyclerView myRecyclerview;
   private TIProductRecyclerAdapter myRecyclerAdapter;
   private RecyclerView.LayoutManager mLayoutManager;


   public TIProductDetailAdapter( FragmentActivity aContext, List<BladeDescription> aDetailList, List<BladeTypes> aBladeTypesList ) {
      myContext = aContext;
      myAppDatabase = AppDatabase.getDatabase( myContext );
      myBladeTypesList = aBladeTypesList;
      myFragmentManager = new APPFragmentManager( myContext );

   }

   @NonNull
   @Override
   public Object instantiateItem( @NonNull ViewGroup container, int position ) {
      View inflate = LayoutInflater.from( container.getContext() ).inflate( R.layout.productdetailviewpager_item, container, false );

      BladeTypes aBladeTypes = myBladeTypesList.get( position );

      ImageView aMainImageView = inflate.findViewById( R.id.inflate_product_image );
      TextView aProductName = inflate.findViewById( R.id.inflate_product_name_TXT );

      LinearLayout aFirstRoottLayout = inflate.findViewById( R.id.first_product_root_layout );
      LinearLayout aSecondRoottLayout = inflate.findViewById( R.id.second_product_root_layout );
      LinearLayout aThirdRoottLayout = inflate.findViewById( R.id.third_product_root_layout );

      RelativeLayout aFirst_product_medium_Layout = inflate.findViewById( R.id.first_product_medium_layout );
      RelativeLayout aSecond_product_medium_Layout = inflate.findViewById( R.id.second_product_medium_layout );
      RelativeLayout aThird_product_medium_Layout = inflate.findViewById( R.id.third_product_medium_layout );

      RelativeLayout aFirst_product_small_Layout = inflate.findViewById( R.id.first_product_small_layout );
      RelativeLayout aSecond_product_small_Layout = inflate.findViewById( R.id.second_product_small_layout );
      RelativeLayout aThird_product_small_Layout = inflate.findViewById( R.id.third_product_small_layout );

      ImageView aFirst_product_small_Image = inflate.findViewById( R.id.first_product_small_image );
      ImageView aSecond_product_small_Image = inflate.findViewById( R.id.second_product_small_image );
      ImageView aThird_product_small_Image = inflate.findViewById( R.id.third_product_small_image );

      ImageView aFirst_product_medium_Image = inflate.findViewById( R.id.first_product_medium_image );
      ImageView aSeond_product_medium_Image = inflate.findViewById( R.id.second_product_medium_image );
      ImageView aThird_product_medium_Image = inflate.findViewById( R.id.third_product_medium_image );
      myRecyclerview = inflate.findViewById( R.id.myProductDetail_RecyclerView );

      mLayoutManager = new LinearLayoutManager( myContext, LinearLayoutManager.VERTICAL, false );
      myRecyclerview.setLayoutManager( mLayoutManager );

      List<BladeDescription> aDetailList = myAppDatabase.bladeDescriptionDao().getAllBladeDescriptions( aBladeTypes.getBladeTypeId(), "1" );
      myRecyclerAdapter = new TIProductRecyclerAdapter( myContext, aDetailList );
      myRecyclerview.setAdapter( myRecyclerAdapter );

      aProductName.setText( aBladeTypes.getBladeTypeName() );

      loadGlideImage( aBladeTypes.getBladeImage1(), aMainImageView );

      loadGlideImage( aBladeTypes.getBladeImage1(), aFirst_product_small_Image );
      loadGlideImage( aBladeTypes.getBladeImage2(), aSecond_product_small_Image );
      loadGlideImage( aBladeTypes.getBladeImage3(), aThird_product_small_Image );

      loadGlideImage( aBladeTypes.getBladeImage1(), aFirst_product_medium_Image );
      loadGlideImage( aBladeTypes.getBladeImage2(), aSeond_product_medium_Image );
      loadGlideImage( aBladeTypes.getBladeImage3(), aThird_product_medium_Image );


      aFirstRoottLayout.setOnClickListener( view -> {

         aFirst_product_medium_Layout.setVisibility( View.VISIBLE );
         aSecond_product_medium_Layout.setVisibility( View.GONE );
         aThird_product_medium_Layout.setVisibility( View.GONE );

         aFirst_product_small_Layout.setVisibility( View.GONE );
         aSecond_product_small_Layout.setVisibility( View.VISIBLE );
         aThird_product_small_Layout.setVisibility( View.VISIBLE );

         loadGlideImage( aBladeTypes.getBladeImage1(), aMainImageView );
      } );

      aSecondRoottLayout.setOnClickListener( view -> {

         aFirst_product_medium_Layout.setVisibility( View.GONE );
         aSecond_product_medium_Layout.setVisibility( View.VISIBLE );
         aThird_product_medium_Layout.setVisibility( View.GONE );

         aFirst_product_small_Layout.setVisibility( View.VISIBLE );
         aSecond_product_small_Layout.setVisibility( View.GONE );
         aThird_product_small_Layout.setVisibility( View.VISIBLE );

         loadGlideImage( aBladeTypes.getBladeImage2(), aMainImageView );

      } );

      aThirdRoottLayout.setOnClickListener( view -> {

         aFirst_product_medium_Layout.setVisibility( View.GONE );
         aSecond_product_medium_Layout.setVisibility( View.GONE );
         aThird_product_medium_Layout.setVisibility( View.VISIBLE );

         aFirst_product_small_Layout.setVisibility( View.VISIBLE );
         aSecond_product_small_Layout.setVisibility( View.VISIBLE );
         aThird_product_small_Layout.setVisibility( View.GONE );

         loadGlideImage( aBladeTypes.getBladeImage3(), aMainImageView );
      } );


      container.addView( inflate );
      return inflate;
   }

   private void loadGlideImage( String Url, ImageView aImageView ) {
      Glide.with( myContext ).load( Url ).apply( new RequestOptions()
              .placeholder( R.drawable.logo ).fitCenter() ).into( aImageView );
   }


   @Override
   public int getCount() {
      return myBladeTypesList.size();
   }

   @Override
   public boolean isViewFromObject( @NonNull View view, @NonNull Object object ) {
      return view == object;
   }

   @Override
   public void destroyItem( @NonNull ViewGroup container, int position, @NonNull Object object ) {
      container.removeView( ( ( View ) object ) );
   }
}
