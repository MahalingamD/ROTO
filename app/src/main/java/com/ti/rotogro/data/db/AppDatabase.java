package com.ti.rotogro.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ti.rotogro.data.db.dao.AddressMasterDao;
import com.ti.rotogro.data.db.dao.BladeDescriptionDao;
import com.ti.rotogro.data.db.dao.BladeTypesDao;
import com.ti.rotogro.data.db.dao.CityMasterDao;
import com.ti.rotogro.data.db.dao.LanguageDao;
import com.ti.rotogro.data.db.dao.ProductDao;
import com.ti.rotogro.data.db.dao.StateMasterDao;
import com.ti.rotogro.data.db.entity.AddressMaster;
import com.ti.rotogro.data.db.entity.BladeDescription;
import com.ti.rotogro.data.db.entity.BladeTypes;
import com.ti.rotogro.data.db.entity.CityMaster;
import com.ti.rotogro.data.db.entity.Country;
import com.ti.rotogro.data.db.entity.LanguageMaster;
import com.ti.rotogro.data.db.entity.Product;
import com.ti.rotogro.data.db.entity.StateMaster;

@Database(entities = { LanguageMaster.class, Country.class, Product.class, BladeTypes.class,
        BladeDescription.class, StateMaster.class, CityMaster.class, AddressMaster.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {

   private static AppDatabase INSTANCE;

   public abstract LanguageDao languageModel();

   public abstract ProductDao ProductModel();

   public abstract BladeTypesDao bladeTypesDao();

   public abstract BladeDescriptionDao bladeDescriptionDao();

   public abstract StateMasterDao stateMasterDao();

   public abstract CityMasterDao cityMasterDao();

   public abstract AddressMasterDao addressMasterDao();


   public static AppDatabase getDatabase( Context context ) {
      if( INSTANCE == null ) {
         INSTANCE = Room.databaseBuilder( context.getApplicationContext(), AppDatabase.class, "rotogro_db" )
                 .allowMainThreadQueries()
                 .build();
      }
      return INSTANCE;
   }

   public static AppDatabase getMemoryDatabase( Context context ) {
      if( INSTANCE == null ) {
         INSTANCE = Room.inMemoryDatabaseBuilder( context.getApplicationContext(), AppDatabase.class )
                 .allowMainThreadQueries()
                 .build();
      }
      return INSTANCE;
   }

   public static void destroyInstance() {
      INSTANCE = null;
   }

}
