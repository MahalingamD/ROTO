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
public interface StateMasterDao {

   @Insert
   void insert( StateMaster... user );

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   void insertAllState( List<StateMaster> order );


   @Update
   void update( StateMaster... user );

   @Delete
   void delete( StateMaster... user );

   @Query("DELETE FROM state")
   void deleteAll();


   @Query("SELECT * FROM state where IsActive = :active")
   List<StateMaster> getAllState( String active );
}
