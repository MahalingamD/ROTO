package com.ti.rotogro.data.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "city")
public class CityMaster implements Serializable {

   @PrimaryKey()
   @NonNull
   @ColumnInfo(name = "CityId")
   @SerializedName("CityId")
   public String CityId;

   @ColumnInfo(name = "CityName")
   @SerializedName("CityName")
   public String CityName;

   @ColumnInfo(name = "IsActive")
   @SerializedName("IsActive")
   public String IsActive;

   @ColumnInfo(name = "LanguageId")
   @SerializedName("LanguageId")
   public String LanguageId;

   @ColumnInfo(name = "StateId")
   @SerializedName("StateId")
   public String StateId;

   @NonNull
   public String getCityId() {
      return CityId;
   }

   public void setCityId( @NonNull String cityId ) {
      CityId = cityId;
   }

   public String getCityName() {
      return CityName;
   }

   public void setCityName( String cityName ) {
      CityName = cityName;
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

   public String getStateId() {
      return StateId;
   }

   public void setStateId( String stateId ) {
      StateId = stateId;
   }
}
