package com.ti.rotogro.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ti.rotogro.data.db.entity.Product;

import java.util.List;

@Dao
public interface ProductDao {

   @Insert
   void insert( Product... aProduct );

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   void insertAllProduct( List<Product> aProduct );

   @Update
   void update( Product... aProduct );

   @Delete
   void delete( Product... aProduct );

   @Query("DELETE FROM product")
   void deleteAll();

   @Query("SELECT * FROM product where  LanguageId = :aLanguage and IsActive = :aActive")
   List<Product> getAllsProducts( String aLanguage, String aActive );


   @Query("SELECT * FROM product where  IsActive = :aActive")
   List<Product> getAllProducts(  String aActive );
}
