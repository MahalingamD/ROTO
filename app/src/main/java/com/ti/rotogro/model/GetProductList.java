package com.ti.rotogro.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ti.rotogro.data.db.entity.AddressMaster;
import com.ti.rotogro.data.db.entity.BladeDescription;
import com.ti.rotogro.data.db.entity.BladeTypes;
import com.ti.rotogro.data.db.entity.CityMaster;
import com.ti.rotogro.data.db.entity.LanguageMaster;
import com.ti.rotogro.data.db.entity.Product;
import com.ti.rotogro.data.db.entity.StateMaster;

import java.io.Serializable;
import java.util.List;


public class GetProductList implements Serializable {

   @Expose
   private List<Product> Product;

   @Expose
   private List<BladeTypes> BladeTypes;

   @Expose
   private List<BladeDescription> BladeDescription;

   @SerializedName("LanguageMaster")
   private List<LanguageMaster> mLanguageMaster;

   @Expose
   private List<StateMaster> StateMaster;

   @Expose
   private List<CityMaster> CityMaster;

   @Expose
   private List<AddressMaster> AddressMaster;


   public GetProductList() {
   }

   public List<Product> getProductDetails() {
      return Product;
   }

   public List<BladeDescription> getBladeDescription() {
      return BladeDescription;
   }

   public List<BladeTypes> getBladeTypes() {
      return BladeTypes;
   }

   public List<LanguageMaster> getLanguageMaster() {
      return mLanguageMaster;
   }

   public List<StateMaster> getStateMaster() {
      return StateMaster;
   }

   public List<CityMaster> getCityMaster() {
      return CityMaster;
   }

   public List<AddressMaster> getAddressMaster() {
      return AddressMaster;
   }

}
