package com.example.demoroomdb.model.RoomDatabase;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.demoroomdb.model.DAO.EmployeeDao;
import com.example.demoroomdb.model.Entity.Employee;

@Database(entities = {Employee.class}, version = 2,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    static Migration migration = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE employeeTable ADD COLUMN datetime TEXT DEFAULT ''");
        }
    };

    private static AppDatabase INSTANCE;
    public abstract EmployeeDao employeeDao();
    public static synchronized AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"appDB.db").addMigrations(migration).build();
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

    private static class PopulateAsyncTask extends AsyncTask<Employee, Void, Void> {
        private EmployeeDao dao;
        private PopulateAsyncTask(AppDatabase appDatabase) {
            dao = appDatabase.employeeDao();
        }
//        @Override
//        protected Void doInBackground(Void... voids) {
////            dao.delete(new Employee("huy.ht","Tran Thanh Huy", "0916631422","",12,"Male"));
//            return null;
//

        @Override
        protected Void doInBackground(Employee... employees) {
            Log.d("App DB", "Delete data : " + employees[employees.length-1]);
//            dao.delete(employees[employees.length-1]);
            return null;
        }
    }
}
