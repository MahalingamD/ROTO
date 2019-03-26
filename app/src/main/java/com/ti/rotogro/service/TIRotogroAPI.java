package com.ti.rotogro.service;

import com.ti.rotogro.model.Data;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * This class represents the Countries API, all endpoints can stay here.
 *
 * @author Mahalingam
 * @date 30/01/19.
 */
public interface TIRotogroAPI {

   //Single parameter
   @GET("{path}")
   Call<Data> getVersionResult( @Path("path") String aPath, @Query("version_name") String version_name );

   //No parameter
   @GET("{path}")
   Call<Data> getAllLanguage( @Path("path") String aPath );

   @GET("{path}")
   Call<Data> getOTP( @Path("path") String aPath, @Query("Mob_Number") String aMobile_number );

   //Map parameter
   @GET("{path}")
   Call<Data> getResult( @Path("path") String aPath, @QueryMap Map<String, String> params );

   @GET("{path}")
   Call<Data> updateProduct( @Path("path") String aPath, @Query("LastUpdate") String aLastUpdateTime );

}
