package com.aariyan.all_about_rxJava.Model;

public class StudentModel {
    private String name;
    private int roleNumber;

    public StudentModel(){}

    public StudentModel(String name, int roleNumber) {
        this.name = name;
        this.roleNumber = roleNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoleNumber() {
        return roleNumber;
    }

    public void setRoleNumber(int roleNumber) {
        this.roleNumber = roleNumber;
    }
}
