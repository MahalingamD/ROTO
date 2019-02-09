package com.ti.rotogro.ui.dialog.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ti.rotogro.R;
import com.ti.rotogro.data.db.entity.LanguageMaster;
import com.ti.rotogro.ui.dialog.RecyclerItemClickListener;


import java.util.List;

public class TILanguageRecyclerAdapter extends RecyclerView.Adapter<TILanguageRecyclerAdapter.ViewHolder> {

   AppCompatActivity myContext;
   private List<LanguageMaster> myLanguageMasterList;
   private RecyclerItemClickListener recyclerItemClickListener;
   int selectedPosition = -1;

   public TILanguageRecyclerAdapter( AppCompatActivity aContext, List<LanguageMaster> aLanguageMasterList, RecyclerItemClickListener recyclerItemClickListener ) {
      this.myLanguageMasterList = aLanguageMasterList;
      this.recyclerItemClickListener = recyclerItemClickListener;
      myContext = aContext;
   }


   @NonNull
   @Override
   public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
      LayoutInflater layoutInflater = LayoutInflater.from( parent.getContext() );
      View view = layoutInflater.inflate( R.layout.dialog_language_view_item, parent, false );
      return new ViewHolder( view );
   }

   @Override
   public void onBindViewHolder( @NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position ) {

      LanguageMaster aContactDetail = myLanguageMasterList.get( position );
      holder.myLanguageName.setText( aContactDetail.getLan_Name() );

      if( selectedPosition == position )
         holder.myLanguageLayout.setBackground( ContextCompat.getDrawable( myContext, R.drawable.circle_green_design ) );
      else
         holder.myLanguageLayout.setBackgroundColor( ContextCompat.getColor( myContext, R.color.white ) );


      holder.myLanguageLayout.setOnClickListener( new View.OnClickListener() {
         @Override
         public void onClick( View view ) {
            recyclerItemClickListener.onItemClick( aContactDetail );
            selectedPosition = position;
            notifyDataSetChanged();
         }
      } );


   }

   @Override
   public int getItemCount() {
      return myLanguageMasterList.size();
   }

   public void updateAdapter( List<LanguageMaster> aContactList ) {
      myLanguageMasterList = aContactList;
      notifyDataSetChanged();
   }

   class ViewHolder extends RecyclerView.ViewHolder {

      TextView myLanguageName;
      LinearLayout myLanguageLayout;

      ViewHolder( View itemView ) {
         super( itemView );
         myLanguageName = itemView.findViewById( R.id.lanuguage_name );
         myLanguageLayout = itemView.findViewById( R.id.language_layout );

      }
   }
}