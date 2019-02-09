package com.ti.rotogro.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.ti.rotogro.R;


public class APPFragmentManager {

   /**
    * Last fragment tag
    */
   private static String myLastTag = "";
   private FragmentActivity myContext;

   /**
    * Constructor to Initiate fragment manager
    *
    * @param aContext FragmentActivity
    */
   public APPFragmentManager( FragmentActivity aContext ) {
      myContext = aContext;
   }

   /**
    * Update the Current Fragment by passing the below parameters
    *
    * @param aFragment Fragment
    * @param tag       String
    * @param aBundle   Bundle
    */
   public void updateContent( Fragment aFragment, String tag, Bundle aBundle ) {
      try {

         Log.e( "TAG Screen name", tag );

         // Initialise Fragment Manager
         final FragmentManager aFragmentManager = myContext.getSupportFragmentManager();

         // Initialise Fragment Transaction
         final FragmentTransaction aTransaction = aFragmentManager.beginTransaction();


         if( aBundle != null ) {
            aFragment.setArguments( aBundle );
         }

         // Add the selected fragment
         aTransaction.add( R.id.main_container, aFragment, tag );

         // add the tag to the backstack
         aTransaction.addToBackStack( tag );

         // Commit the Fragment transaction
         // aTransaction.commit ();

         aTransaction.commitAllowingStateLoss();

         myLastTag = tag;

         Log.i( "LastTag", myLastTag );

      } catch( StackOverflowError | Exception e ) {
         e.printStackTrace();
      }
   }


   /**
    * Clear All Fragments
    */
   public void clearAllFragments() {

      try {
         FragmentManager aFragmentManager = myContext.getSupportFragmentManager();

         for( int i = 0; i < aFragmentManager.getBackStackEntryCount(); ++i ) {
            aFragmentManager.popBackStack();
         }
      } catch( StackOverflowError | Exception e ) {
         e.printStackTrace();
      }

   }

   public void oneStepBack() {
      FragmentTransaction fts = myContext.getSupportFragmentManager().beginTransaction();
      FragmentManager fragmentManager = myContext.getSupportFragmentManager();
      if( fragmentManager.getBackStackEntryCount() >= 2 ) {
         fragmentManager.popBackStackImmediate();
         fts.commit();
      }
   }


   public int getBackstackCount() {

      FragmentManager aFragmentManager = myContext.getSupportFragmentManager();

      return aFragmentManager.getBackStackEntryCount();
   }


   public void onBackPress() {

      try {

         FragmentManager aFragmentManager = myContext.getSupportFragmentManager();
         if( aFragmentManager.getBackStackEntryCount() > 1 ) {
            aFragmentManager.popBackStack();
            aFragmentManager.executePendingTransactions();

            Log.d( "TAG", "CURRENT FRAGMENT BACK STACK CLASS " + aFragmentManager.getBackStackEntryAt( aFragmentManager.getBackStackEntryCount() - 1 ).getName() );


            //TODO Stop video
            String aFragmentTag = aFragmentManager.getBackStackEntryAt( aFragmentManager.getBackStackEntryCount() - 1 ).getName();
            Fragment aFragment = myContext.getSupportFragmentManager().findFragmentByTag( aFragmentTag );
            aFragment.onResume();

            if( aFragment instanceof BaseFragment ) {
               ( ( BaseFragment ) aFragment ).onResumeFragment();
            }


            String aFragmentName = aFragmentManager.getBackStackEntryAt( aFragmentManager.getBackStackEntryCount() - 1 ).getName();
         }
      } catch( StackOverflowError | Exception e ) {
         e.printStackTrace();
      }
   }


   public void onBackPress1() {

      try {
         FragmentManager aFragmentManager = myContext.getSupportFragmentManager();
         if( aFragmentManager.getBackStackEntryCount() > 0 ) {
            aFragmentManager.popBackStack();
            aFragmentManager.executePendingTransactions();

            Log.d( "TAG", "CURRENT FRAGMENT BACK STACK CLASS " + aFragmentManager.getBackStackEntryAt( aFragmentManager.getBackStackEntryCount() - 1 ).getName() );


            //TODO Stop video
            String aFragmentTag = aFragmentManager.getBackStackEntryAt( aFragmentManager.getBackStackEntryCount() - 1 ).getName();
            Fragment aFragment = myContext.getSupportFragmentManager().findFragmentByTag( aFragmentTag );
            aFragment.onResume();

            if( aFragment instanceof BaseFragment ) {
               ( ( BaseFragment ) aFragment ).onResumeFragment();

            }

            String aFragmentName = aFragmentManager.getBackStackEntryAt( aFragmentManager.getBackStackEntryCount() - 1 ).getName();
         }
      } catch( StackOverflowError | Exception e ) {
         e.printStackTrace();
      }
   }


}
