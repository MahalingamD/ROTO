package com.ti.rotogro.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.ti.rotogro.data.db.entity.BladeTypes;

import java.util.List;

@Dao
public interface BladeTypesDao {

   @Insert
   void insert( BladeTypes... aProduct );

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   void insertAllProduct( List<BladeTypes> aProduct );

   @Update
   void update( BladeTypes... aProduct );

   @Delete
   void delete( BladeTypes... aProduct );

   @Query("DELETE FROM bladetypes")
   void deleteAll();


   @Query("SELECT * FROM bladetypes where ProductId= :aProducdId and IsActive = :aActive ORDER BY BladeTypeId")
   List<BladeTypes> getAllProducts( String aProducdId, String aActive );


   @Query("SELECT * FROM bladetypes where IsActive = :aActive")
   List<BladeTypes> getAllActiveProducts( String aActive );
}
