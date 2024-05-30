package org.application.AdminPrivilages;

import org.application.BusinessFunctions.Functions;
import org.application.InitializingCustomers.InitializingCustomerBase;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminRights {
    private static java.util.List<Admin> adminList;

    public AdminRights() throws Exception {
        adminList = new ArrayList<Admin>();
        Admin ad = new Admin("admin", "benjo");
        adminList.add(ad);
        InitializingCustomerBase.readCustomersFromFile();
    }
    public boolean adminLogin(String userName, String pass){
        Functions fun = new Functions();
        boolean val = userName.equals(adminList.get(0).getUserName()) && pass.equals(fun.decryptinPass(adminList.get(0).getPass()));
        return val;
    }

    public static boolean changePass(){
        boolean val = false;
        Admin ad = adminList.get(0);
        Functions fun = new Functions();
        String newPass = Functions.passwordComplexity();
        if(newPass.contains("true")){
            ad.setPass(fun.encryptinPass(newPass));
            System.out.println("Password has been changed successfully...");
            val = true;
        }

        return val;
    }

    public static void adminAccess(){
        try{
            Scanner sc = new Scanner(System.in);

            while(true){
                System.out.println("For Initializing new Customers          : Press 1");
                System.out.println("For viewing all the customers names     : Press 2");
                System.out.println("For Log Out                             : Press 0");
                System.out.println("For Changing Pass                       : Press 4");
                int option = sc.nextInt();
                if(option == 1){
                    InitializingCustomerBase.initializingNewCustomers();
                    System.out.println("New Customers are registered.....");
                }
                if(option == 2){
                    InitializingCustomerBase.readingCustomersName();
                }
                if(option == 4){
                    AdminRights.changePass();
                }
                if(option == 0){
                    break;
                }
            }

        }
        catch (Exception ex){
            System.out.println("The input should only contain digits not apart from digits");
            AdminRights.adminAccess();
        }
    }
}
