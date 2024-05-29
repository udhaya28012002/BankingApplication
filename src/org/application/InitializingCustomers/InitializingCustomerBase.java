package org.application.InitializingCustomers;

import org.application.AllBankingServices.BankingServices;
import org.application.BusinessFunctions.Functions;
import org.application.TransactionDetail.TransactionDetails;
import org.application.customerPrivilages.CustomerFactory;
import org.application.customerPrivilages.CustomerInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InitializingCustomerBase {

    public static List<CustomerInterface> customers = new ArrayList<>();

    public static void readingCustomersName() {
        for(CustomerInterface cus : customers){
            System.out.println(cus.getCustomerId());
            System.out.println(cus.getEncryptedPwd());
        }
    }

    public static void initializingNewCustomers(){
        String nameOfTheCustomer = "", passwordNeedToSet="", retypeThePass="";
        System.out.println("Please enter the below details to process further....");
        System.out.println("Please enter your full name : ");
        nameOfTheCustomer = new Scanner(System.in).next();
        Functions fun = new Functions();
        if(!fun.passwordComplexity().contains("false")) {
            InitializingCustomerBase.initializingNewCustomers();
        }
        else {
            String accNo = fun.generateAccNo();
            String cusId = fun.generateCusID();
            CustomerInterface cus = CustomerFactory.createCustomer(cusId,accNo,nameOfTheCustomer,10000.00,fun.encryptinPass(passwordNeedToSet));
            customers.add(cus);
            TransactionDetails td = new TransactionDetails(nameOfTheCustomer,accNo, cusId,(int)(Math.random() * (999990 - 100000 + 1)+ 100000)+"","Opening",10000.00,10000.00);
            BankingServices.transactionDetails.add(td);
        }
    }
    public static void readCustomersFromFile() throws Exception {
        String fileName = "E:\\TestProjectJava\\src\\org\\application\\bank_db.txt";
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\s+");
            if (parts.length != 5) {
                throw new Exception("Invalid line format in file: " + line);
            }

            String customerId = parts[0];
            String accountNumber = parts[1];
            String name = parts[2];
            double balance = Double.parseDouble(parts[3]);
            String encryptedPwd = parts[4];

            CustomerInterface customer = CustomerFactory.createCustomer(customerId, accountNumber, name, balance, encryptedPwd);
            TransactionDetails td = new TransactionDetails(name,accountNumber,customerId,(int)(Math.random() * (999990 - 100000 + 1)+ 100000)+"","Opening",balance,balance);
            BankingServices.transactionDetails.add(td);
            customers.add(customer);
        }
        reader.close();
    }


}

