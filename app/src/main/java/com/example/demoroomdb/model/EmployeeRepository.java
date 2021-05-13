package com.example.demoroomdb.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.demoroomdb.model.DAO.EmployeeDao;
import com.example.demoroomdb.model.Entity.Employee;
import com.example.demoroomdb.model.RoomDatabase.AppDatabase;

import java.util.List;

public class EmployeeRepository {
    private EmployeeDao employeeDao;
    private LiveData<List<Employee>> allEmployees;

    public EmployeeRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        employeeDao = appDatabase.employeeDao();
        allEmployees = employeeDao.getAllEmployee();
    }

    public void insert(Employee employee) {
        new InsertEmployee(employeeDao).execute(employee);
    }
    public void update(Employee employee) {
        new UpdateEmployee(employeeDao).execute(employee);
    }
    public void delete(Employee employee) {
        new DeleteEmployee(employeeDao).execute(employee);
    }
    public void deleteAll() {
        new DeleteAllEmployee(employeeDao).execute();
    }
    public LiveData<List<Employee>> getAllEmployees() {
        return allEmployees;
    }

    private static class InsertEmployee extends AsyncTask<Employee, Void, Void> {
        private EmployeeDao employeeDao;
        private InsertEmployee ( EmployeeDao mEmployeeDao) {
            this.employeeDao = mEmployeeDao;
        }
        @Override
        protected Void doInBackground(Employee... employees) {
            employeeDao.insert(employees[0]);
            return null;
        }
    }

    private static class UpdateEmployee extends AsyncTask<Employee, Void, Void> {
        private EmployeeDao employeeDao;
        private UpdateEmployee ( EmployeeDao mEmployeeDao) {
            this.employeeDao = mEmployeeDao;
        }
        @Override
        protected Void doInBackground(Employee... employees) {
            employeeDao.update(employees[0]);
            return null;
        }
    }

    private static class DeleteEmployee extends AsyncTask<Employee, Void, Void> {
        private EmployeeDao employeeDao;
        private DeleteEmployee ( EmployeeDao mEmployeeDao) {
            this.employeeDao = mEmployeeDao;
        }
        @Override
        protected Void doInBackground(Employee... employees) {
            employeeDao.delete(employees[0]);
            return null;
        }
    }

    private static class DeleteAllEmployee extends AsyncTask<Void, Void, Void> {
        private EmployeeDao employeeDao;
        private DeleteAllEmployee ( EmployeeDao mEmployeeDao) {
            this.employeeDao = mEmployeeDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            employeeDao.deleteAll();
            return null;
        }
    }

}
