package com.example.demoroomdb.RoomDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
                        .addCallback(roomCallback)
                        .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateAsyncTask extends AsyncTask<Void, Void, Void> {
        private EmployeeDao dao;
        private PopulateAsyncTask(AppDatabase appDatabase) {
            dao = appDatabase.employeeDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.insert(new Employee("huy.ht","Tran Thanh Huy", "0916631422","",12,"Male"));
            return null;
        }
    }
}
