package com.ti.rotogro.ui.login.adapter;

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
import com.ti.rotogro.ui.login.LanguageRecyclerItemClickListener;

import java.util.List;

public class LanguageRecyclerAdapter extends RecyclerView.Adapter<LanguageRecyclerAdapter.ViewHolder> {

   private List<LanguageMaster> myLanguageMasterList;
   private LanguageRecyclerItemClickListener languageRecyclerItemClickListener;
   private AppCompatActivity myContext;

   public LanguageRecyclerAdapter( AppCompatActivity aContext, List<LanguageMaster> aLanguageMasterList, LanguageRecyclerItemClickListener languageRecyclerItemClickListener ) {
      this.myLanguageMasterList = aLanguageMasterList;
      this.languageRecyclerItemClickListener = languageRecyclerItemClickListener;
      myContext = aContext;
   }


   @NonNull
   @Override
   public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
      LayoutInflater layoutInflater = LayoutInflater.from( parent.getContext() );
      View view = layoutInflater.inflate( R.layout.language_view_item, parent, false );
      return new ViewHolder( view );
   }

   @Override
   public void onBindViewHolder( @NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position ) {

      LanguageMaster aLanguageMaster = myLanguageMasterList.get( position );
      holder.myLanguageTXT.setText( aLanguageMaster.getLan_Name() );


      holder.itemView.setOnClickListener( v -> {
         languageRecyclerItemClickListener.onItemClick( position, aLanguageMaster );

         for( int i = 0; i < myLanguageMasterList.size(); i++ ) {
            LanguageMaster aLanguageMaster1 = myLanguageMasterList.get( i );
            if( i == position ) {
               aLanguageMaster1.setSelect( true );
            } else {
               aLanguageMaster1.setSelect( false );
            }
         }

         notifyDataSetChanged();
      } );


      if( aLanguageMaster.isSelect() ) {
         holder.myLanguageLayout.setBackground( ContextCompat.getDrawable( myContext, R.drawable.circle_green_design ) );
      } else {
         holder.myLanguageLayout.setBackground( ContextCompat.getDrawable( myContext, R.drawable.circle_white_design ) );
      }


   }

   @Override
   public int getItemCount() {
      return myLanguageMasterList.size();
   }

   public void updateAdapter( List<LanguageMaster> aLanguageMasterList ) {
      myLanguageMasterList = aLanguageMasterList;
      notifyDataSetChanged();
   }

   class ViewHolder extends RecyclerView.ViewHolder {

      TextView myLanguageTXT;
      LinearLayout myLanguageLayout;

      ViewHolder( View itemView ) {
         super( itemView );
         myLanguageTXT = itemView.findViewById( R.id.lanuguage_name );
         myLanguageLayout = itemView.findViewById( R.id.language_layout );
      }
   }
}