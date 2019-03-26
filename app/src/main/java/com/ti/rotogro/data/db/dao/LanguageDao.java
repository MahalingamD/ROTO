package com.ti.rotogro.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ti.rotogro.data.db.entity.LanguageMaster;

import java.util.List;

@Dao
public interface LanguageDao {

   @Insert
   void insert( LanguageMaster... user );

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   void insertAllLanguage( List<LanguageMaster> order );


   @Update
   void update( LanguageMaster... user );

   @Delete
   void delete( LanguageMaster... user );

   @Query("DELETE FROM language")
   void deleteAll();

   // @Query("SELECT * FROM language")

   @Query("SELECT * FROM language where IsActive = :active ORDER BY LanguageId")
   List<LanguageMaster> getAllLanguages( int active );

   @Query("SELECT * FROM language where IsActive = :active ORDER BY LanguageId")
   LiveData<List<LanguageMaster>> getAllLiveLanguages( int active );
}
