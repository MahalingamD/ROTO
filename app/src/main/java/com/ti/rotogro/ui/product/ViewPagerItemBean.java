package com.ti.rotogro.ui.product;


public class ViewPagerItemBean {
   String img_url;
   String tilte_text;

   public ViewPagerItemBean( String img_url, String tilte_text ) {
      this.img_url = img_url;
      this.tilte_text = tilte_text;
   }

   public ViewPagerItemBean() {

   }

   public String getImg_url() {
      return img_url;
   }

   public void setImg_url( String img_url ) {
      this.img_url = img_url;
   }

   public String getTilte_text() {
      return tilte_text;
   }

   public void setTilte_text( String tilte_text ) {
      this.tilte_text = tilte_text;
   }
}
