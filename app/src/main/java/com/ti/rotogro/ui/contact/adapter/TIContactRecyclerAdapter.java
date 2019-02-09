package com.ti.rotogro.ui.contact.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ti.rotogro.R;
import com.ti.rotogro.data.db.entity.AddressMaster;
import com.ti.rotogro.model.ContactDetail;
import com.ti.rotogro.ui.contact.RecyclerItemClickListener;

import java.util.List;

public class TIContactRecyclerAdapter extends RecyclerView.Adapter<TIContactRecyclerAdapter.ViewHolder> {

   FragmentActivity myContext;
   private List<AddressMaster> mAddressMasterList;
   private RecyclerItemClickListener recyclerItemClickListener;


   public TIContactRecyclerAdapter( FragmentActivity aContext, List<AddressMaster> aAddressMasterList, RecyclerItemClickListener recyclerItemClickListener ) {
      mAddressMasterList = aAddressMasterList;
      this.recyclerItemClickListener = recyclerItemClickListener;
      myContext = aContext;
   }


   @NonNull
   @Override
   public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
      LayoutInflater layoutInflater = LayoutInflater.from( parent.getContext() );
      View view = layoutInflater.inflate( R.layout.contact_view_item, parent, false );
      return new ViewHolder( view );
   }

   @Override
   public void onBindViewHolder( @NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position ) {

      AddressMaster aContactDetail = mAddressMasterList.get( position );
      holder.myContactName.setText( aContactDetail.getConductName() );
      holder.myContactAddress.setText( aContactDetail.getAddressName() );
      holder.myContactMobile.setText( aContactDetail.getMobileNumber() );


      holder.myContactLayout.setOnClickListener( new View.OnClickListener() {
         @Override
         public void onClick( View view ) {
            recyclerItemClickListener.onItemClick( aContactDetail );


         }
      } );


   }

   @Override
   public int getItemCount() {
      return mAddressMasterList.size();
   }

   public void updateAdapter( List<AddressMaster> aContactList ) {
      mAddressMasterList = aContactList;
      notifyDataSetChanged();
   }

   class ViewHolder extends RecyclerView.ViewHolder {

      TextView myContactName, myContactAddress, myContactMobile;
      LinearLayout myContactLayout;

      ViewHolder( View itemView ) {
         super( itemView );
         myContactName = itemView.findViewById( R.id.contact_name );
         myContactAddress = itemView.findViewById( R.id.contact_address );
         myContactMobile = itemView.findViewById( R.id.contact_mobile );

         myContactLayout = itemView.findViewById( R.id.contact_layout );

      }
   }
}