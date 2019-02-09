package com.ti.rotogro.ui.productdetail;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ti.rotogro.R;
import com.ti.rotogro.data.db.entity.BladeDescription;

import java.util.ArrayList;
import java.util.List;

public class TIProductRecyclerAdapter extends RecyclerView.Adapter<TIProductRecyclerAdapter.ViewHolder> {

   private List<BladeDescription> myLanguageList;
   FragmentActivity myContext;

   public TIProductRecyclerAdapter( FragmentActivity aContext, List<BladeDescription> aLanguageList ) {
      this.myLanguageList = aLanguageList;
      myContext = aContext;
   }


   @NonNull
   @Override
   public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
      LayoutInflater layoutInflater = LayoutInflater.from( parent.getContext() );
      View view = layoutInflater.inflate( R.layout.product_item_view_item, parent, false );
      return new ViewHolder( view );
   }

   @Override
   public void onBindViewHolder( @NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position ) {

      BladeDescription aLanguage = myLanguageList.get( position );

      holder.myProductTypeTXT.setText( aLanguage.getBladeDesc() );

      holder.myProductNameTXT.setText( aLanguage.getRotogroValues() );

   }

   @Override
   public int getItemCount() {
      return myLanguageList.size();
   }

   class ViewHolder extends RecyclerView.ViewHolder {

      TextView myProductTypeTXT;
      TextView myProductNameTXT;

      ViewHolder( View itemView ) {
         super( itemView );
         myProductTypeTXT = itemView.findViewById( R.id.inflate_product_type );
         myProductNameTXT = itemView.findViewById( R.id.inflate_product_name );
      }
   }
}