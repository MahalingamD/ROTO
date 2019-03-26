
package com.ti.rotogro.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class VersionDetails implements Serializable {

   @SerializedName("Message")
   private String mMessage;
   @SerializedName("Response")
   private com.ti.rotogro.model.Response mResponse;
   @SerializedName("Status")
   private String mStatus;
   @SerializedName("Update_type")
   private String mUpdateType;
   @SerializedName("url")
   private String mUrl;
   @SerializedName("VersionDetails")
   private VersionDetails mVersionDetails;
   @SerializedName("Version_name")
   private String mVersionName;

   public String getMessage() {
      return mMessage;
   }

   public void setMessage( String Message ) {
      mMessage = Message;
   }

   public com.ti.rotogro.model.Response getResponse() {
      return mResponse;
   }

   public void setResponse( com.ti.rotogro.model.Response Response ) {
      mResponse = Response;
   }

   public String getStatus() {
      return mStatus;
   }

   public void setStatus( String Status ) {
      mStatus = Status;
   }

   public String getUpdateType() {
      return mUpdateType;
   }

   public void setUpdateType( String UpdateType ) {
      mUpdateType = UpdateType;
   }

   public String getUrl() {
      return mUrl;
   }

   public void setUrl( String url ) {
      mUrl = url;
   }

   public VersionDetails getVersionDetails() {
      return mVersionDetails;
   }

   public void setVersionDetails( VersionDetails VersionDetails ) {
      mVersionDetails = VersionDetails;
   }

   public String getVersionName() {
      return mVersionName;
   }

   public void setVersionName( String VersionName ) {
      mVersionName = VersionName;
   }

}
