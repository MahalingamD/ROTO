package com.ti.rotogro.data.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "bladedescription")
public class BladeDescription implements Serializable {


   @PrimaryKey()
   @NonNull
   @ColumnInfo(name = "BladeDescId")
   private String BladeDescId;



   @ColumnInfo(name = "BladeTypeId")
   private String BladeTypeId;

   @ColumnInfo(name = "IsActive")
   private String IsActive;

   @ColumnInfo(name = "BladeDesc")
   private String BladeDesc;

   @ColumnInfo(name = "RotogroValues")
   private String RotogroValues;


   @NonNull
   public String getBladeDescId() {
      return BladeDescId;
   }

   public void setBladeDescId( @NonNull String bladeDescId ) {
      BladeDescId = bladeDescId;
   }

   public String getBladeTypeId() {
      return BladeTypeId;
   }

   public void setBladeTypeId( String bladeTypeId ) {
      BladeTypeId = bladeTypeId;
   }

   public String getIsActive() {
      return IsActive;
   }

   public void setIsActive( String isActive ) {
      IsActive = isActive;
   }

   public String getBladeDesc() {
      return BladeDesc;
   }

   public void setBladeDesc( String bladeDesc ) {
      BladeDesc = bladeDesc;
   }

   public String getRotogroValues() {
      return RotogroValues;
   }

   public void setRotogroValues( String rotogroValues ) {
      RotogroValues = rotogroValues;
   }
}
