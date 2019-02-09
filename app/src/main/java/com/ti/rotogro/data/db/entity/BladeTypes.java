package com.ti.rotogro.data.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "bladetypes")
public class BladeTypes implements Serializable {


   @ColumnInfo(name = "ProductId")
   private String ProductId;

   @PrimaryKey()
   @NonNull
   @ColumnInfo(name = "BladeTypeId")
   private String BladeTypeId;

   @ColumnInfo(name = "BladeTypeName")
   private String BladeTypeName;

   @ColumnInfo(name = "IsActive")
   private String IsActive;

   @ColumnInfo(name = "BladeImage1")
   private String BladeImage1;

   @ColumnInfo(name = "BladeImage2")
   private String BladeImage2;

   @ColumnInfo(name = "BladeImage3")
   private String BladeImage3;

   @ColumnInfo(name = "LocalBladeImage1")
   private String LocalBladeImage1;

   @ColumnInfo(name = "LocalBladeImage2")
   private String LocalBladeImage2;

   @ColumnInfo(name = "LocalBladeImage3")
   private String LocalBladeImage3;

   @ColumnInfo(name = "Rate")
   private String Rate;

   public String getRate() {
      return Rate;
   }

   public void setRate( String rate ) {
      Rate = rate;
   }

   public String getLocalBladeImage1() {
      return LocalBladeImage1;
   }

   public void setLocalBladeImage1( String localBladeImage1 ) {
      LocalBladeImage1 = localBladeImage1;
   }

   public String getLocalBladeImage2() {
      return LocalBladeImage2;
   }

   public void setLocalBladeImage2( String localBladeImage2 ) {
      LocalBladeImage2 = localBladeImage2;
   }

   public String getLocalBladeImage3() {
      return LocalBladeImage3;
   }

   public void setLocalBladeImage3( String localBladeImage3 ) {
      LocalBladeImage3 = localBladeImage3;
   }

   public String getProductId() {
      return ProductId;
   }

   public void setProductId( String productId ) {
      ProductId = productId;
   }

   public String getBladeTypeId() {
      return BladeTypeId;
   }

   public void setBladeTypeId( String bladeTypeId ) {
      BladeTypeId = bladeTypeId;
   }

   public String getBladeTypeName() {
      return BladeTypeName;
   }

   public void setBladeTypeName( String bladeTypeName ) {
      BladeTypeName = bladeTypeName;
   }

   public String getIsActive() {
      return IsActive;
   }

   public void setIsActive( String isActive ) {
      IsActive = isActive;
   }

   public String getBladeImage1() {
      return BladeImage1;
   }

   public void setBladeImage1( String bladeImage1 ) {
      BladeImage1 = bladeImage1;
   }

   public String getBladeImage2() {
      return BladeImage2;
   }

   public void setBladeImage2( String bladeImage2 ) {
      BladeImage2 = bladeImage2;
   }

   public String getBladeImage3() {
      return BladeImage3;
   }

   public void setBladeImage3( String bladeImage3 ) {
      BladeImage3 = bladeImage3;
   }

}
