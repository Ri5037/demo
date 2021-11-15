package com.example.hall_management;

public class Users {

    /**getter,setter,constructor method**/
    public String fullname,email,password,contact,reg,dept;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Users(String fullname, String email, String password, String contact, String reg, String dept) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.contact = contact;
        this.reg = reg;
        this.dept = dept;
    }
}
