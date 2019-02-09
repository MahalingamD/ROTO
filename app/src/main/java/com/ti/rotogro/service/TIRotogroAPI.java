package com.ti.rotogro.service;

import com.ti.rotogro.BuildConfig;
import com.ti.rotogro.model.Data;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * This class represents the Countries API, all endpoints can stay here.
 *
 * @author Mahalingam (Github: @jeancsanchez)
 * @date 30/01/19.
 */
public interface TIRotogroAPI {


   @GET(BuildConfig.Main_Base_url + "GetAppUpdate")
   Call<Data> getAppUpdate( @Query("version_name") String version_name );

   @GET(BuildConfig.Main_Base_url + "GetlanguageList")
   Call<Data> getAllLanguage();

   @GET(BuildConfig.Main_Base_url + "ValidateUser")
   Call<Data> getOTP( @Query("Mob_Number") String aMobile_number );

   @GET(BuildConfig.Main_Base_url + "ValidateOtp")
   Call<Data> validateOTP( @QueryMap Map<String, String> params );

   @GET(BuildConfig.Main_Base_url + "GetUpdateProduct")
   Call<Data> updateProduct( @Query("LastUpdate") String aLastUpdateTime);


}
