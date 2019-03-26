package com.ti.rotogro.ui.splash;

import com.ti.rotogro.base.BaseView;
import com.ti.rotogro.model.VersionDetails;

public class TISplashContract {

   public interface Presenter {
      void initPresenter();

      void checkAppUpdate( String aVersionName );

   }

   public interface View extends BaseView {

      void viewSplashCount();

      void initActivity();

      void appUpdate( VersionDetails versionDetails );

      void termAlert();
   }
}
