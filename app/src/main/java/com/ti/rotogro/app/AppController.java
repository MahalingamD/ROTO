package com.ti.rotogro.app;


import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.ti.rotogro.data.db.AppDatabase;
import com.ti.rotogro.preference.TIPrefs;
import com.ti.rotogro.utils.LocaleHelper;

import java.util.Locale;


public class AppController extends Application {

   public static final String TAG = AppController.class.getSimpleName();
   private static AppController myInstance;
   private AppDatabase db;


   public static synchronized AppController getInstance() {
      return myInstance;
   }


   @Override
   protected void attachBaseContext(Context base) {
      super.attachBaseContext(LocaleHelper.onAttach(base, "ta_IN"));
   }

   @Override
   public void onCreate() {
      super.onCreate();
      myInstance = this;
     /* db = Room.databaseBuilder( this, AppDatabase.class, "rotogro_db" )
              .addMigrations( FROM_1_TO_2 ).build();*/


      // Initialize the Shared Preferences class
      new TIPrefs.Builder().setContext( this )
              .setMode( ContextWrapper.MODE_PRIVATE )
              .setPrefsName( getPackageName() )
              .setUseDefaultSharedPreference( true ).build();

      Room.databaseBuilder( this, AppDatabase.class, "rotogro_db" )
              .fallbackToDestructiveMigration()
              .build();
   }




   static final Migration FROM_1_TO_2 = new Migration( 1, 2 ) {
      @Override
      public void migrate( final SupportSQLiteDatabase database ) {
         //database.execSQL( "ALTER TABLE Repo ADD COLUMN createdAt TEXT" );
      }
   };
}