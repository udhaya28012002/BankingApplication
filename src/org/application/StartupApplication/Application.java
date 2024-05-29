package org.application.StartupApplication;

import org.application.AdminPrivilages.AdminRights;
import org.application.customerPrivilages.CustomerRights;

import java.util.Scanner;

public class Application {

    public void runApplication() throws Exception {
        Scanner sc = new Scanner(System.in);
        AdminRights ad = new AdminRights();
        while (true) {

            CustomerRights cusRig = new CustomerRights();
            System.out.println("Admin Login     : Press 1");
            System.out.println("User Login      : Press 2");
            System.out.println("Exit            : Press 0");

            int val = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            if (val == 1) {
                System.out.println("Enter UserName : ");
                String userName = sc.next();
                System.out.println("Enter Password : ");
                String pass = sc.next();
                if (ad.adminLogin(userName, pass)) {
                    AdminRights.adminAccess();
                } else {
                    System.out.println("The username and pass are incorrect. Please enter the details correctly");
                }
            } else if (val == 2) {
                System.out.println("Enter UserName : ");
                String userName = sc.nextLine();
                System.out.println("Enter Password : ");
                String pass = sc.nextLine();
                if (cusRig.cusLogin(userName, pass)) {
                    cusRig.customerAccess(userName, pass);
                } else {
                    System.out.println("The username and pass are incorrect. Please enter the details correctly");
                }
            } else if (val == 0) {
                break;
            } else {
                System.out.println("Please enter the correct option number to proceed further....");
            }
        }
        sc.close();
    }
}

