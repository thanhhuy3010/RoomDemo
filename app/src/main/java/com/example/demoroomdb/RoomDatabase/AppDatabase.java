package com.example.demoroomdb.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.demoroomdb.DAO.EmployeeDao;
import com.example.demoroomdb.Entity.Employee;

@Database(entities = {Employee.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract EmployeeDao employeeDao();
    public static synchronized AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"appDB.db")
                    .fallbackToDestructiveMigration()
                        .build();
        }
        return INSTANCE;
    }
}
