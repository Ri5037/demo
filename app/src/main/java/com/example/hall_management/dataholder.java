package com.example.hall_management;

public class dataholder {

    /**this class contains neccessary details about student users(getter,setter,constructor methods)**/
    String name;
    String dept;
    String pimage;
    String contact;
    String email;
    String pass;
    String reg;


    public dataholder(String pimage) {
        this.pimage = pimage;

    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
