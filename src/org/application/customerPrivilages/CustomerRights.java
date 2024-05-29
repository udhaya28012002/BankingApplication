package org.application.customerPrivilages;
import org.application.AllBankingServices.BankingServices;
import org.application.BusinessFunctions.Functions;
import org.application.InitializingCustomers.InitializingCustomerBase;

import java.util.Scanner;

public class CustomerRights {
    public boolean cusLogin(String userName, String pass){
        boolean val = false;
        Functions fun = new Functions();
        for(CustomerInterface cus : InitializingCustomerBase.customers){
            if(cus.getCustomerId().equals(userName) && pass.equals(fun.decryptinPass(cus.getEncryptedPwd()))){
                val = true;
                break;
            }
        }
        return val;
    }

    public static boolean changePass(String cusId){
        Functions fun = new Functions();
        fun.displayPasswordConstraintMessagesOnScreen();
        boolean val = false;
        for(CustomerInterface cus : InitializingCustomerBase.customers){
            if(cus.getCustomerId().equals(cusId)){
               String newPass = Functions.passwordComplexity(cus);
               if(newPass.contains("true")){
                   cus.setEncryptedPwd(fun.encryptinPass(newPass.substring(4)));
                   System.out.println("Password has been changed successfully...");
                   cus.setPreviousPassList(fun.encryptinPass(newPass.substring(4)));
                   val = true;
               }
               else{
                   CustomerRights.changePass(cusId);
               }
            }
        }
        return val;
    }

    public void customerAccess(String cusId, String pass) {
        BankingServices bs = new BankingServices();
        Functions fun = new Functions();
        Scanner sc = new Scanner(System.in);
        while (true) {
            String val = fun.ForcePassChange(cusId);
            if(val.contains("true")){
                pass = val.substring(4);
            }
            System.out.println("For ATM Withdraw            : Press 1");
            System.out.println("For Cash Deposit            : Press 2");
            System.out.println("For Account Transfer        : Press 3");
            System.out.println("For Statement               : Press 4");
            System.out.println("For Changing Password       : Press 5");
            System.out.println("For Exit                    : Press 0");
            int option = sc.nextInt();
            if (option == 1) {
                bs.atmWithdrawl(cusId, pass);
            }
            if (option == 2) {
                bs.cashDeposit(cusId, pass);
            }
            if (option == 3) {
                bs.accountTransfer(cusId, pass);
            }
            if (option == 4) {
                bs.statement(cusId, pass);
            }
            if (option == 5) {
                CustomerRights.changePass(cusId);
            }
            if (option == 0) {
                break;
            }
        }
    }
}
