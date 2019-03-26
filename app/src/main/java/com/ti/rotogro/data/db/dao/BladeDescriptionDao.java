package com.ti.rotogro.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ti.rotogro.data.db.entity.BladeDescription;

import java.util.List;

@Dao
public interface BladeDescriptionDao {

   @Insert
   void insert( BladeDescription... aProduct );

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   void insertAllProduct( List<BladeDescription> aProduct );

   @Update
   void update( BladeDescription... aProduct );

   @Delete
   void delete( BladeDescription... aProduct );

   @Query("DELETE FROM bladedescription")
   void deleteAll();


   @Query("SELECT * FROM bladedescription where  IsActive = :aActive")
   List<BladeDescription> getAllActiveProducts( String aActive );


   @Query("SELECT * FROM bladedescription where BladeTypeId = :aBladeType and  IsActive = :aActive ORDER BY BladeDescId")
   List<BladeDescription> getAllBladeDescriptions( String aBladeType, String aActive );
}
