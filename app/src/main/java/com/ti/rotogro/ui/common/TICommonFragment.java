package com.ti.rotogro.ui.common;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ti.rotogro.R;
import com.ti.rotogro.data.db.entity.Product;
import com.ti.rotogro.ui.contact.TIContactFragment;

/**
 * This class represents RetrofitInstance.
 *
 * @author Mahalingam
 * @date 30/01/19.
 */

public class TICommonFragment extends Fragment {


   public static String TAG = TICommonFragment.class.getSimpleName();
   FragmentActivity myContext;
   Product myProduct;
   ImageView myImageView;

   public TICommonFragment() {
      // Required empty public constructor
   }


   @Override
   public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
      // Inflate the layout for this fragment
      View aView = inflater.inflate( R.layout.fragment_common, container, false );
      Log.e(TAG,"onCreateView");

      myImageView = aView.findViewById( R.id.common_viewpage );

      myContext = getActivity();
      getBundle();

      return aView;
   }


   private void getBundle() {
      try {
         Bundle aBundle = getArguments();
         if( aBundle != null ) {
            Bundle args = getArguments();
            myProduct = ( Product ) args.getSerializable( "Product" );
            if( myProduct != null ) {
               loadGlideImage( myProduct.getProdImageUrl(), myImageView );
            }
         }
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   private void loadGlideImage( String Url, ImageView aImageView ) {
      Glide.with( myContext ).load( Url ).apply( new RequestOptions()
              .placeholder( R.drawable.logo ).fitCenter() ).into( aImageView );
   }

   @Override
   public void onAttach( Context context ) {

      Log.e(TAG,"onAttach");
      super.onAttach( context );
   }

   @Override
   public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState ) {
      Log.e(TAG,"onViewCreated");
      super.onViewCreated( view, savedInstanceState );
   }

   @Override
   public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
      Log.e(TAG,"onActivityCreated");
      super.onActivityCreated( savedInstanceState );
   }

   @Override
   public void onStart() {
      Log.e(TAG,"onStart");
      super.onStart();
   }

   @Override
   public void onPause() {
      Log.e(TAG,"onPause");
      super.onPause();
   }

   @Override
   public void onStop() {
      Log.e(TAG,"onStop");
      super.onStop();
   }

   @Override
   public void onDestroyView() {

      Log.e(TAG,"onDestroyView");
      super.onDestroyView();
   }

   @Override
   public void onDestroy() {
      Log.e(TAG,"onDestroy");
      super.onDestroy();
   }

   @Override
   public void onDetach() {
      Log.e(TAG,"onDetach");
      super.onDetach();
   }
}
