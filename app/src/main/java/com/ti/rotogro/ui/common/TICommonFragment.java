package com.ti.rotogro.ui.common;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ti.rotogro.R;
import com.ti.rotogro.ui.contact.TIContactFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TICommonFragment extends Fragment {


   public static String TAG = TICommonFragment.class.getSimpleName();

   public TICommonFragment() {
      // Required empty public constructor
   }


   @Override
   public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
      // Inflate the layout for this fragment
      return inflater.inflate( R.layout.fragment_common, container, false );
   }

}
