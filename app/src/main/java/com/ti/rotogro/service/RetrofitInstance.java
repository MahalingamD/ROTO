package com.ti.rotogro.service;

import com.ti.rotogro.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

   private Retrofit retrofit = null;


   /**
    * This method creates a new instance of the API interface.
    *
    * @return The API interface
    */
   public TIRotogroAPI getAPI() {

      if( retrofit == null ) {
         retrofit = new Retrofit
                 .Builder()
                 .baseUrl( BuildConfig.Main_Base_url )
                // .baseUrl( BuildConfig.Base_url )
                 .addConverterFactory( GsonConverterFactory.create() )
                 .build();
      }

      return retrofit.create( TIRotogroAPI.class );
   }
}