package com.ti.rotogro.data.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "state")
public class StateMaster implements Serializable {

   @PrimaryKey()
   @NonNull
   @ColumnInfo(name = "StateId")
   @SerializedName("StateId")
   public String StateId;

   @ColumnInfo(name = "StateName")
   @SerializedName("StateName")
   public String StateName;

   @ColumnInfo(name = "IsActive")
   @SerializedName("IsActive")
   public String active;

   @ColumnInfo(name = "LanguageId")
   @SerializedName("LanguageId")
   public String LanguageId;

   @NonNull
   public String getStateId() {
      return StateId;
   }

   public void setStateId( @NonNull String stateId ) {
      StateId = stateId;
   }

   public String getStateName() {
      return StateName;
   }

   public void setStateName( String stateName ) {
      StateName = stateName;
   }

   public String getActive() {
      return active;
   }

   public void setActive( String active ) {
      this.active = active;
   }

   public String getLanguageId() {
      return LanguageId;
   }

   public void setLanguageId( String languageId ) {
      LanguageId = languageId;
   }
}
