package com.example.demoroomdb.model.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.demoroomdb.model.Entity.Tablet;

import java.util.List;

@Dao
public interface TabletDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Tablet tablet);

    @Query("SELECT * from tablet_entity ")
    LiveData<List<Tablet>> getAllTablet();
}
