package com.example.demoroomdb.model.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "employeeTable")
public class Employee {
    @PrimaryKey(autoGenerate = true)
    private int idUser;

    @ColumnInfo(name = "username")
    private String userName;

    @ColumnInfo(name = "fullName")
    private String fullName;

    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;

    @ColumnInfo(name = "role")
    private String role;

    @ColumnInfo(name = "age")
    private int age;

    @ColumnInfo(name = "gender")
    private String gender;

    public Employee(String userName, String fullName, String phoneNumber, String role, int age, String gender) {
        this.userName = userName;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.age = age;
        this.gender = gender;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
