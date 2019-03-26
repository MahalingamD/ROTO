package com.ti.rotogro.ui.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ti.rotogro.R;
import com.ti.rotogro.data.db.entity.Product;
import com.ti.rotogro.helper.TIHelper;
import com.ti.rotogro.ui.home.HomeRecyclerItemClickListener;

import java.io.File;
import java.util.List;

public class TIProductRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   private List<Product> myProductList;
   private HomeRecyclerItemClickListener recyclerItemClickListener;
   AppCompatActivity myContext;

   int aDeviceWidth;
   private int aAspectRatioWidth = 0;
   private int aAspectRatioHeight = 0;
   int viewHeight = 0;

   private static final int TYPE_SINGLE = 1;
   private static final int TYPE_GROUP = 2;

   public TIProductRecyclerAdapter( AppCompatActivity aContext, List<Product> aProductList, int height, HomeRecyclerItemClickListener recyclerItemClickListener ) {
      this.myProductList = aProductList;
      this.recyclerItemClickListener = recyclerItemClickListener;
      myContext = aContext;
      viewHeight = height;
   }


   @NonNull
   @Override
   public RecyclerView.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
      LayoutInflater layoutInflater = LayoutInflater.from( parent.getContext() );
      // View view = layoutInflater.inflate( R.layout.layout_inflate_product_adapter, parent, false );

      View view;
      Display aDisplay = myContext.getWindowManager().getDefaultDisplay();

      aDeviceWidth = ( aDisplay.getWidth() );
      aAspectRatioHeight = ( aDisplay.getHeight() );

      aAspectRatioWidth = ( ( aDeviceWidth / 2 ) * 9 ) / 16; // in this case aspect ratio 16:9
      aAspectRatioHeight = ( aAspectRatioHeight / 2 ) - ( viewHeight + 40 );

      if( viewType == TYPE_SINGLE ) {
         view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.layout_inflate_product_adapter, parent, false );
         return new SingleViewHolder( view );
      } else if( viewType == TYPE_GROUP ) {
         view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.layout_inflate_multiple_product_adapter, parent, false );
         return new MultipleViewHolder( view );
      }

      return null;
   }

   @Override
   public void onBindViewHolder( @NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position ) {
      Product aProduct = myProductList.get( position );


      switch( holder.getItemViewType() ) {
         case TYPE_SINGLE:
            ShowSingleView( ( SingleViewHolder ) holder, position, aProduct );
            break;
         case TYPE_GROUP:
            multipleShow( ( MultipleViewHolder ) holder, position, aProduct );
            break;
      }

      holder.itemView.setOnClickListener( new View.OnClickListener() {
         @Override
         public void onClick( View v ) {
            recyclerItemClickListener.onItemClick( position, aProduct );
         }
      } );


   }

   private void multipleShow( MultipleViewHolder holder, int position, Product aProduct ) {


      if( aProduct.getProdImageUrl() == null || aProduct.getProdImageUrl().equals( "" ) ) {
         loadDrawableGlideImage( ContextCompat.getDrawable( myContext, R.drawable.logo ), holder.myProductListImg );
      } else {
         loadGlideImage( aProduct.getProdImageUrl(), holder.myProductListImg );
      }

      holder.myProductName.setText( aProduct.getProductName() );

      if( aProduct.getProductCount().equals( "0" ) ) {
         holder.myProductSize.setText( R.string.label_coming_soon );
      } else {
         holder.myProductSize.setText( "" );
      }

   }

   private void loadDrawableGlideImage( Drawable drawable, ImageView myProductListImg ) {
      Glide.with( myContext ).load( drawable ).apply( new RequestOptions()
              .placeholder( R.drawable.logo ).fitCenter() ).into( myProductListImg );
   }


   private void ShowSingleView( SingleViewHolder holder, int position, Product aProduct ) {

      LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, aAspectRatioHeight );
      params1.gravity = Gravity.CENTER | Gravity.CENTER_VERTICAL;
      holder.myRootView.setLayoutParams( params1 );

      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT );
      params.gravity = Gravity.CENTER | Gravity.CENTER_VERTICAL;
      holder.myRootView1.setLayoutParams( params );


      if( position == 0 ) {
         holder.myProductName.setText( aProduct.getProductName() );
         holder.myProductSize.setText( "" );
         holder.myRootView.setBackgroundColor( ContextCompat.getColor( myContext, R.color.yellow_green ) );
      } else {
         holder.myProductName.setText( aProduct.getProductName() );
         holder.myProductName.setTextColor( ContextCompat.getColor( myContext, R.color.low_grey ) );

         if( aProduct.getProductCount().equals( "0" ) ) {
            holder.myProductSize.setText( R.string.label_coming_soon );
         } else {
            holder.myProductSize.setText( "" );
         }
         holder.myRootView.setBackgroundColor( ContextCompat.getColor( myContext, R.color.white ) );
      }


      loadGlideImage( aProduct.getProdImageUrl(), holder.myProductListImg );

      LinearLayout.LayoutParams imgparams = ( LinearLayout.LayoutParams ) holder.myProductListImg.getLayoutParams();
      imgparams.gravity = Gravity.CENTER | Gravity.CENTER_VERTICAL;
      holder.myProductListImg.setLayoutParams( imgparams );

      holder.myProductName.setText( aProduct.getProductName() );
   }

   private void loadImageView( Context aContext, ImageView aImageView, String aImageUrl ) {
      TIHelper.loadImage( aContext, aImageView, aImageUrl, R.drawable.placeholder_plain, R.drawable.placeholder_plain );
   }


   @Override
   public int getItemViewType( int position ) {
      if( myProductList.size() == 1 || myProductList.size() == 2 )
         return TYPE_SINGLE;
      else
         return TYPE_GROUP;
   }


   private void loadGlideImage( String Url, ImageView aImageView ) {
      Glide.with( myContext ).load( Url ).apply( new RequestOptions()
              .placeholder( R.drawable.logo ).fitCenter() ).into( aImageView );
   }

   @Override
   public int getItemCount() {

      return myProductList != null ? myProductList.size() : 0;
   }

   public void updateAdapter( List<Product> aProductList, int aViewHeight ) {
      myProductList = aProductList;
      viewHeight = aViewHeight;
      notifyDataSetChanged();
   }


   public static class SingleViewHolder extends RecyclerView.ViewHolder {

      ImageView myProductListImg;
      LinearLayout myRootView, myRootView1;
      TextView myProductName;
      TextView myProductSize;

      SingleViewHolder( View itemView ) {
         super( itemView );
         myRootView = itemView.findViewById( R.id.inflate_layout_root_lay );
         myRootView1 = itemView.findViewById( R.id.inflate_layout_root_lay1 );
         myProductListImg = itemView.findViewById( R.id.inflate_layout_season_list_img );
         myProductName = itemView.findViewById( R.id.inflate_layout_Product_name_TXT );
         myProductSize = itemView.findViewById( R.id.inflate_layout_Product_Size_TXT );
      }
   }


   public static class MultipleViewHolder extends RecyclerView.ViewHolder {

      ImageView myProductListImg;
      LinearLayout myRootView1;
      CardView myRootView;
      TextView myProductName;
      TextView myProductSize;

      MultipleViewHolder( View itemView ) {
         super( itemView );
         myRootView = itemView.findViewById( R.id.inflate_layout_root_lay );
         myRootView1 = itemView.findViewById( R.id.inflate_layout_root_lay1 );
         myProductListImg = itemView.findViewById( R.id.inflate_layout_season_list_img );
         myProductName = itemView.findViewById( R.id.inflate_layout_Product_name_TXT );
         myProductSize = itemView.findViewById( R.id.inflate_layout_Product_Size_TXT );
      }
   }


}