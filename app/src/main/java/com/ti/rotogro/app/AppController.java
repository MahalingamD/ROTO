package com.ti.rotogro.app;


import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.ContextWrapper;

import com.ti.rotogro.adapter.Singleton;
import com.ti.rotogro.data.db.AppDatabase;
import com.ti.rotogro.preference.TIPrefs;


public class AppController extends Application {

   public static final String TAG = AppController.class.getSimpleName();
   private static AppController myInstance;

   public static synchronized AppController getInstance() {
      return myInstance;
   }


   @Override
   public void onCreate() {
      super.onCreate();
      myInstance = this;

      Singleton.getInstance();

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