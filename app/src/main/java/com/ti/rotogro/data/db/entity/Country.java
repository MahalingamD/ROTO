package com.ti.rotogro.data.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * This class represents the country view interface.
 *
 * @author Jean Carlos (Github: @jeancsanchez)
 * @date 09/03/18.
 * Jesus loves you.
 */
@Entity
public class Country implements Serializable {

   @PrimaryKey(autoGenerate = true)
   long id;

   public String name;
   @SerializedName("alpha2_code")
   @ColumnInfo
   public String alphaCode_one;

   @SerializedName("alpha3_code")
   @ColumnInfo
   public String alphaCode_two;



   public String getName() {
      return name;
   }

   public String getAlphaCode_one() {
      return alphaCode_one;
   }

   public String getAlphaCode_two() {
      return alphaCode_two;
   }

   public long getId() {
      return id;
   }

   public void setId( long id ) {
      this.id = id;
   }

   public void setName( String name ) {
      this.name = name;
   }

   public void setAlphaCode_one( String alphaCode_one ) {
      this.alphaCode_one = alphaCode_one;
   }

   public void setAlphaCode_two( String alphaCode_two ) {
      this.alphaCode_two = alphaCode_two;
   }


   public Country() {
      name = "";
      alphaCode_one = "";
      alphaCode_two = "";
   }


   public Country( String aName, String aAlpha2, String aAlpha3 ) {
      name = aName;
      alphaCode_one = aAlpha2;
      alphaCode_two = aAlpha3;
   }

}
