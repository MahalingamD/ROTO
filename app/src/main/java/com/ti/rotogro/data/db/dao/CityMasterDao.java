package com.ti.rotogro.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ti.rotogro.data.db.entity.CityMaster;
import com.ti.rotogro.data.db.entity.LanguageMaster;
import com.ti.rotogro.data.db.entity.StateMaster;

import java.util.List;

@Dao
public interface CityMasterDao {

   @Insert
   void insert( CityMaster... user );

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   void insertAllCity( List<CityMaster> order );

   @Update
   void update( CityMaster... user );

   @Delete
   void delete( CityMaster... user );

   @Query("DELETE FROM city")
   void deleteAll();

   @Query("SELECT * FROM city where StateId= :aStateId and  IsActive = :active ORDER BY CityId")
   List<CityMaster> getAllCity( String aStateId, String active );


   @Query("SELECT * FROM city where StateId= :aStateId and LanguageId = :aLanguageId and IsActive = :active ORDER BY CityId")
   List<CityMaster> getAllSelectedCity( String aStateId, String aLanguageId, String active );
}
