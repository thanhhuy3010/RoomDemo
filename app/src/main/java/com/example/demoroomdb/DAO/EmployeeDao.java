package com.example.demoroomdb.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.demoroomdb.Entity.Employee;

import java.util.List;
@Dao
public interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Employee employee);

    @Update
    void update(Employee employee);

    @Query("SELECT * from employeeTable ")
    LiveData<List<Employee>> getAllEmployee();

    @Delete
    void delete(Employee employee);

    @Query("DELETE FROM employeeTable")
    void deleteAll();
}
