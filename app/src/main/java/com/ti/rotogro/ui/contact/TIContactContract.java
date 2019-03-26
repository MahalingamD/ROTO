package com.ti.rotogro.ui.contact;

import com.ti.rotogro.base.BaseView;
import com.ti.rotogro.data.db.entity.AddressMaster;
import com.ti.rotogro.data.db.entity.CityMaster;
import com.ti.rotogro.data.db.entity.LanguageMaster;
import com.ti.rotogro.data.db.entity.StateMaster;

import java.util.List;

public class TIContactContract {

   public interface Presenter {

      void initPresenter();

      void getStateValues();

      void getCityValues( String aStateId, LanguageMaster aLanguageId );

      void getContactValues( String aStateId, String aCityId,LanguageMaster aLanguageId );

   }

   public interface View extends BaseView {

      void setStateSpinner( List<StateMaster> result );

      void setCitySpinner( List<CityMaster> aCityMasterList );

      void initActivity();

      void setRecyclerAdapter( List<AddressMaster> aAddressMasterList );

   }
}
