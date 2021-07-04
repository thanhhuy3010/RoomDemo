package com.example.demoroomdb.model.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "employeeTable")
public class Employee {
    @NotNull
    @PrimaryKey(autoGenerate = true)
    private int idUser;

    @ColumnInfo(name = "username")
    private String username;

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

    @ColumnInfo(name = "email")
    private String email;

    private static Employee INSTANCE;

    public Employee(String userName, String fullName, String phoneNumber, String role, int age, String gender) {
        this.username = userName;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.age = age;
        this.gender = gender;
        this.email = "";
    }

    public Employee(){}

    public static synchronized Employee getInstance(String userName, String fullName, String phoneNumber, String role, int age, String gender) {
        if ( INSTANCE == null) {
            INSTANCE = new Employee(userName, fullName, phoneNumber,role, age, gender);
        }
        return INSTANCE;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
