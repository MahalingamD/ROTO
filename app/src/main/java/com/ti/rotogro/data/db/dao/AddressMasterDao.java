package com.ti.rotogro.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ti.rotogro.data.db.entity.AddressMaster;
import com.ti.rotogro.data.db.entity.CityMaster;
import com.ti.rotogro.data.db.entity.LanguageMaster;

import java.util.List;

@Dao
public interface AddressMasterDao {

   @Insert
   void insert( AddressMaster... user );

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   void insertAllAddress( List<AddressMaster> order );

   @Update
   void update( AddressMaster... user );

   @Delete
   void delete( AddressMaster... user );

   @Query("DELETE FROM address")
   void deleteAll();

   @Query("SELECT * FROM address where StateId = :aStateId and CityId = :aCityId and IsActive = :active ORDER BY AddressId")
   List<AddressMaster> getAllAddress( String aStateId, String aCityId, String active );

   @Query("SELECT * FROM address where StateId = :aStateId and CityId = :aCityId and " +
           "LanguageId=:aLanguageId and IsActive = :active ORDER BY AddressId")
   List<AddressMaster> getAllSelectedAddress( String aStateId, String aCityId, String aLanguageId, String active );
}
