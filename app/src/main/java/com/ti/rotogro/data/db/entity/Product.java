package com.ti.rotogro.data.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "product")
public class Product implements Serializable {

   @PrimaryKey()
   @NonNull
   @ColumnInfo(name = "ProductId")
   private String ProductId;

   @ColumnInfo(name = "ProductName")
   private String ProductName;

   @ColumnInfo(name = "ProdImageUrl")
   private String ProdImageUrl;

   @ColumnInfo(name = "LanguageId")
   private String LanguageId;

   @ColumnInfo(name = "IsActive")
   private String IsActive;

   @ColumnInfo(name = "LastUpdate")
   private String LastUpdate;

   @ColumnInfo(name = "ProductCount")
   private String ProductCount;

   @ColumnInfo(name = "ProductLocalPath")
   private String ProductLocalPath;

   public String getProductLocalPath() {
      return ProductLocalPath;
   }

   public void setProductLocalPath( String productLocalPath ) {
      ProductLocalPath = productLocalPath;
   }

   public boolean IsSelect = false;

   public boolean isSelect() {
      return IsSelect;
   }

   public void setSelect( boolean select ) {
      IsSelect = select;
   }

   @NonNull
   public String getProductId() {
      return ProductId;
   }

   public void setProductId( @NonNull String productId ) {
      ProductId = productId;
   }

   public String getProductName() {
      return ProductName;
   }

   public void setProductName( String productName ) {
      ProductName = productName;
   }

   public String getProdImageUrl() {
      return ProdImageUrl;
   }

   public void setProdImageUrl( String prodImageUrl ) {
      ProdImageUrl = prodImageUrl;
   }

   public String getLanguageId() {
      return LanguageId;
   }

   public void setLanguageId( String languageId ) {
      LanguageId = languageId;
   }

   public String getIsActive() {
      return IsActive;
   }

   public void setIsActive( String isActive ) {
      IsActive = isActive;
   }

   public String getLastUpdate() {
      return LastUpdate;
   }

   public void setLastUpdate( String lastUpdate ) {
      LastUpdate = lastUpdate;
   }

   public String getProductCount() {
      return ProductCount;
   }

   public void setProductCount( String productCount ) {
      ProductCount = productCount;
   }


}
