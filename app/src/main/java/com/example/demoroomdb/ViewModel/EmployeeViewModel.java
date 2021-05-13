package com.example.demoroomdb.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.demoroomdb.EmployeeRepository;
import com.example.demoroomdb.Entity.Employee;

import java.util.List;

public class EmployeeViewModel extends AndroidViewModel {
    private EmployeeRepository repository;
    private LiveData<List<Employee>> allEmployee;

    public EmployeeViewModel(@NonNull Application application) {
        super(application);
        repository = new EmployeeRepository(application);
        allEmployee = repository.getAllEmployees();
    }

    public void insert(Employee employee){
        repository.insert(employee);
    }
    public void update(Employee employee) {
        repository.update(employee);
    }
    public void delete(Employee employee) {
        repository.delete(employee);
    }
    public void deleteAll() {
        repository.deleteAll();
    }

    public LiveData<List<Employee>> getAllEmployee() { return allEmployee; }
}
