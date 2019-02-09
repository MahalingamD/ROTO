package com.ti.rotogro.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author mahalingam
 * @date 04-01-2019.
 */
public class Data implements Serializable {

   @SerializedName("Response")
   private Response Response;

   @SerializedName("VersionDetails")
   private VersionDetails VersionDetails;

   @SerializedName("User_Info")
   private User_Info myUserInfo;

   @SerializedName("GetMasterList")
   private GetMasterList mGetMasterList;

   private GetProductList GetProductList;


   public Response getResponse() {
      return Response;
   }

   public VersionDetails getVersionDetails() {
      return VersionDetails;
   }

   public User_Info getUserDetails() {
      return myUserInfo;
   }

   public GetMasterList getGetMasterList() {
      return mGetMasterList;
   }

   public GetProductList getGetProductList() {
      return GetProductList;
   }


}
