package com.ti.rotogro.data.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "address")
public class AddressMaster implements Serializable {

   @PrimaryKey()
   @NonNull
   @ColumnInfo(name = "AddressId")
   @SerializedName("AddressId")
   public String AddressId;

   @ColumnInfo(name = "AddressName")
   @SerializedName("AddressName")
   public String AddressName;

   @ColumnInfo(name = "ConductName")
   @SerializedName("ConductName")
   public String ConductName;

   @ColumnInfo(name = "MobileNumber")
   @SerializedName("MobileNumber")
   public String MobileNumber;

   @ColumnInfo(name = "IsActive")
   @SerializedName("IsActive")
   public String IsActive;

   @ColumnInfo(name = "LanguageId")
   @SerializedName("LanguageId")
   public String LanguageId;

   @ColumnInfo(name = "CityId")
   @SerializedName("CityId")
   public String CityId;

   @ColumnInfo(name = "StateId")
   @SerializedName("StateId")
   public String StateId;

   @NonNull
   public String getAddressId() {
      return AddressId;
   }

   public void setAddressId( @NonNull String addressId ) {
      AddressId = addressId;
   }

   public String getAddressName() {
      return AddressName;
   }

   public void setAddressName( String addressName ) {
      AddressName = addressName;
   }

   public String getConductName() {
      return ConductName;
   }

   public void setConductName( String conductName ) {
      ConductName = conductName;
   }

   public String getMobileNumber() {
      return MobileNumber;
   }

   public void setMobileNumber( String mobileNumber ) {
      MobileNumber = mobileNumber;
   }

   public String getIsActive() {
      return IsActive;
   }

   public void setIsActive( String isActive ) {
      IsActive = isActive;
   }

   public String getLanguageId() {
      return LanguageId;
   }

   public void setLanguageId( String languageId ) {
      LanguageId = languageId;
   }

   public String getCityId() {
      return CityId;
   }

   public void setCityId( String cityId ) {
      CityId = cityId;
   }

   public String getStateId() {
      return StateId;
   }

   public void setStateId( String stateId ) {
      StateId = stateId;
   }
}
