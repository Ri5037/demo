package com.example.hall_management;

public class Users {

    /**getter,setter,constructor method**/
    public String fullname,email,password,contact,reg,dept;

    public Users() {

    }

    public Users(String fullname, String email, String password, String contact, String reg,String dept) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.contact = contact;
        this.reg = reg;
        this.dept = dept;
    }
}
