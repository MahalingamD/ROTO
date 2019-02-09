
package com.ti.rotogro.model;

import com.google.gson.annotations.SerializedName;
import com.ti.rotogro.data.db.entity.LanguageMaster;

import java.util.List;

@SuppressWarnings("unused")
public class GetMasterList {


   @SerializedName("LanguageMaster")
   private List<LanguageMaster> mLanguageMaster;

   public List<LanguageMaster> getLanguageMaster() {
      return mLanguageMaster;
   }

   public void setLanguageMaster( List<LanguageMaster> LanguageMaster ) {
      mLanguageMaster = LanguageMaster;
   }

}
