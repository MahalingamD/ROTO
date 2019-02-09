package com.ti.rotogro.ui.product;


import com.ti.rotogro.base.BaseView;
import com.ti.rotogro.data.db.entity.BladeTypes;
import com.ti.rotogro.data.db.entity.Product;

import java.util.List;

/**
 * Created by mahalingam on 27/01/17.
 */

public interface TIProductContract extends BaseView {


   public interface Presenter {

      void initPresenter();

      void setProductDetail(String aProductId);


   }


   public interface View extends BaseView {

      void initFragment();

      void setViewPagerAdapter( List<BladeTypes> aProductList );
   }
}
