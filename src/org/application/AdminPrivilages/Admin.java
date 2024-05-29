package org.application.AdminPrivilages;

import java.util.ArrayList;

public class Admin {
    private String userName;
    private String pass;


    Admin(String userName, String pass) {
        this.userName = userName;
        this.pass = pass;
    }

    public String getUserName() {
        return userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
