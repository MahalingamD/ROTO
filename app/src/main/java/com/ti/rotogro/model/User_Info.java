package com.ti.rotogro.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User_Info implements Serializable {
   @SerializedName("user_id")
   private String userId;
   @SerializedName("user_name")
   private String userName;
   @SerializedName("IsActive")
   private String isActive;

   @SerializedName("So_id")
   private String soId;
   @SerializedName("mobile_number")
   private String mobileNumber;
   @SerializedName("User_Type")
   private String userType;
   @SerializedName("SoPowerAppsId")
   private String soPowerAppsId;
   @SerializedName("UserPowerAppsid")
   private String userPowerAppsid;


   public void setUserId( String userId ) {
      this.userId = userId;
   }

   public String getUserId() {
      return userId;
   }

   public void setUserName( String userName ) {
      this.userName = userName;
   }

   public String getUserName() {
      return userName;
   }

   public void setIsActive( String isActive ) {
      this.isActive = isActive;
   }

   public String getIsActive() {
      return isActive;
   }

   public void setUserPowerAppsid( String userPowerAppsid ) {
      this.userPowerAppsid = userPowerAppsid;
   }

   public String getUserPowerAppsid() {
      return userPowerAppsid;
   }

   public void setSoId( String soId ) {
      this.soId = soId;
   }

   public String getSoId() {
      return soId;
   }

   public void setMobileNumber( String mobileNumber ) {
      this.mobileNumber = mobileNumber;
   }

   public String getMobileNumber() {
      return mobileNumber;
   }

   public void setUserType( String userType ) {
      this.userType = userType;
   }

   public String getUserType() {
      return userType;
   }

   public void setSoPowerAppsId( String soPowerAppsId ) {
      this.soPowerAppsId = soPowerAppsId;
   }

   public String getSoPowerAppsId() {
      return soPowerAppsId;
   }


}
