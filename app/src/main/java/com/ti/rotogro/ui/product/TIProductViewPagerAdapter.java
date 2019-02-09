package com.ti.rotogro.ui.product;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ti.rotogro.R;
import com.ti.rotogro.base.APPFragmentManager;
import com.ti.rotogro.data.db.entity.BladeTypes;
import com.ti.rotogro.data.db.entity.Product;
import com.ti.rotogro.helper.TIHelper;
import com.ti.rotogro.ui.productdetail.TIProductDetailFragment;

import java.io.File;
import java.util.List;


public class TIProductViewPagerAdapter extends PagerAdapter {

   private List<BladeTypes> myBladeTypes;
   private FragmentActivity myContext;
   private APPFragmentManager myFragmentManager;
   private Product myProduct;


   public TIProductViewPagerAdapter( List<BladeTypes> aBladeTypes, FragmentActivity aContext, Product aProduct ) {
      myBladeTypes = aBladeTypes;
      myContext = aContext;
      myProduct = aProduct;
      myFragmentManager = new APPFragmentManager( myContext );
   }

   @NonNull
   @Override
   public Object instantiateItem( @NonNull ViewGroup container, int position ) {
      View inflate = LayoutInflater.from( container.getContext() ).inflate( R.layout.cardviewpager_item, container, false );
      ImageView mImageView = inflate.findViewById( R.id.inflate_product_image );
      TextView aCurrentTextView = inflate.findViewById( R.id.inflate_product_name_TXT );
      TextView aCurrentPosition = inflate.findViewById( R.id.inflate_item_current_position_TXT );
      TextView aProductRate = inflate.findViewById( R.id.inflate_product_rate_TXT );
      TextView aTotalTextView = inflate.findViewById( R.id.inflate_item_total_position_TXT );
      LinearLayout aProductLayout = inflate.findViewById( R.id.inflate_product_detail_Layout );

      BladeTypes aBladeTypes = myBladeTypes.get( position );

    /*  String aString = aBladeTypes.getLocalBladeImage1();
      Uri uri = Uri.fromFile( new File( aString ) );

      loadImageView( myContext, mImageView, uri.toString() );*/

      loadGlideImage( aBladeTypes.getBladeImage1(), mImageView );


      aCurrentTextView.setText( String.format( "%s", myBladeTypes.get( position ).getBladeTypeName() ) );

      aCurrentPosition.setText( String.format( "%d", position + 1 ) );

      aTotalTextView.setText( String.format( "/%d", myBladeTypes.size() ) );

      aProductRate.setText( "₹ " + aBladeTypes.getRate() );


      aProductLayout.setOnClickListener( new View.OnClickListener() {
         @Override
         public void onClick( View view ) {
            Bundle aBundle = new Bundle();
            aBundle.putInt( "position", position );
            aBundle.putSerializable( "bladetype", aBladeTypes );
            aBundle.putSerializable( "product", myProduct );
            myFragmentManager.updateContent( new TIProductDetailFragment(), TIProductDetailFragment.TAG, aBundle );
         }
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
      return myBladeTypes.size();
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
