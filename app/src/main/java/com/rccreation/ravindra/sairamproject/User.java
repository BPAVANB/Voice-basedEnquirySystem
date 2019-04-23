package com.rccreation.ravindra.sairamproject;

import java.util.Date;

public class User {
    String Mobile,email,userid,Role,sname;


    Date sessionExpiryDate;

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        this.Role = role;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setMobile(String mobile) {
        this.Mobile = mobile;
    }

    public void setSessionExpiryDate(Date sessionExpiryDate) {
        this.sessionExpiryDate = sessionExpiryDate;
    }

    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }


    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }



}