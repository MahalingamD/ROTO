package com.ti.rotogro.ui.contact.adapter;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ti.rotogro.R;
import com.ti.rotogro.data.db.entity.CityMaster;

import java.util.List;

public class TICitySpinnerAdapter extends ArrayAdapter<CityMaster> {

   private FragmentActivity myContext;
   private List<CityMaster> mCityMasterList;
   private LayoutInflater inflater;


   public TICitySpinnerAdapter( FragmentActivity aContext, int spinner_rows, List<CityMaster> aStateList, Resources res ) {
      super( aContext, spinner_rows, aStateList );

      myContext = aContext;
      mCityMasterList = aStateList;

      inflater = ( LayoutInflater ) myContext.getSystemService( myContext.LAYOUT_INFLATER_SERVICE );
   }


   @Override
   public int getCount() {
      return mCityMasterList.size();
   }

   @Override
   public CityMaster getItem( int position ) {
      return mCityMasterList.get( position );
   }

   @Override
   public long getItemId( int position ) {
      return position;
   }


   @Override
   public View getDropDownView( int position, View convertView, @NonNull ViewGroup parent ) {
      return getCustomView( position, convertView, parent );
   }

   @NonNull
   @Override
   public View getView( int position, View convertView, @NonNull ViewGroup parent ) {
      return getCustomView( position, convertView, parent );
   }


   private View getCustomView( int position, View convertView, ViewGroup parent ) {
      View row = inflater.inflate( R.layout.spinner_rows, parent, false );
      TextView label = row.findViewById( R.id.inflate_object_name );
      label.setText( mCityMasterList.get( position ).getCityName() );
      return row;
   }
}
