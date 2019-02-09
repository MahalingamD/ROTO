package com.ti.rotogro.ui.login;

import com.ti.rotogro.base.BaseView;
import com.ti.rotogro.data.db.entity.LanguageMaster;
import com.ti.rotogro.model.Response;


import java.util.List;
import java.util.Map;

public class TILoginContract {

   public interface Presenter {

      void initPresenter();

      void getLanguage();

      void sendOTP( String s );

      void verifyOTP( Map<String, String> params );



   }

   public interface View extends BaseView {

      void setRecyclerViewAdapter( List<LanguageMaster> result );

      void initActivity();

      void callNextActivity();

      void onOTPResult(Response aResult);

      void getDeviceDetail();


   }
}
