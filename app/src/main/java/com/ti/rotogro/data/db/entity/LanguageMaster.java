package com.ti.rotogro.data.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "language")
public class LanguageMaster implements Serializable {

   @PrimaryKey()
   @NonNull
   @ColumnInfo(name = "LanguageId")
   @SerializedName("Language_id")
   public String Lan_id;

   @ColumnInfo(name = "LanguageName")
   @SerializedName("Language_name")
   public String Lan_Name;

   @ColumnInfo(name = "IsActive")
   @SerializedName("IsActive")
   public int active = 0;

   public boolean IsSelect = false;

   public int getActive() {
      return active;
   }

   public boolean isSelect() {
      return IsSelect;
   }

   public void setSelect( boolean select ) {
      IsSelect = select;
   }

   public int isActive() {
      return active;
   }

   public void setActive( int active ) {
      this.active = active;
   }

   public String getLan_id() {
      return Lan_id;
   }

   public void setLan_id( String lan_id ) {
      Lan_id = lan_id;
   }

   public String getLan_Name() {
      return Lan_Name;
   }

   public void setLan_Name( String lan_Name ) {
      Lan_Name = lan_Name;
   }


   public LanguageMaster() {
      Lan_id = "";
      Lan_Name = "";
      active = 0;
   }

   public LanguageMaster( String aLan_id, String aLanName, int aBool ) {
      Lan_id = aLan_id;
      Lan_Name = aLanName;
      active = aBool;
   }

}
