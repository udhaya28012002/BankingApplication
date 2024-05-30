package org.application.AllBankingServices;

import org.application.BusinessFunctions.Functions;
import org.application.InitializingCustomers.InitializingCustomerBase;
import org.application.TransactionDetail.TransactionDetails;
import org.application.customerPrivilages.CustomerInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankingServices {

    public static List<TransactionDetails> transactionDetails = new ArrayList<>();
    public void cashDeposit(String cusId, String pass){
        Scanner sc = new Scanner(System.in);
        Functions fun = new Functions();
        for(CustomerInterface cus : InitializingCustomerBase.customers){
            if(cus.getCustomerId().equals(cusId) && pass.equals(fun.decryptinPass(cus.getEncryptedPwd()))){
                System.out.println("Please enter the amount to be deposited : ");
                double amt = sc.nextDouble();
                cus.setBalance(cus.getBalance()+amt);
                TransactionDetails td = new TransactionDetails(cus.getName(),cus.getAccountNumber(), cus.getCustomerId(),(int)(Math.random() * (999990 - 100000 + 1)+ 100000)+"","CashDeposit",amt,cus.getBalance());
                transactionDetails.add(td);
                if(!fun.topNCustomers(cus)){
                    fun.maintenanceFee(cus);
                }
                System.out.println("Current Balance amount is : "+cus.getBalance());
            }
        }
    }

    public void atmWithdrawl(String cusId, String pass){
        Scanner sc = new Scanner(System.in);
        Functions fun = new Functions();
        for(CustomerInterface cus : InitializingCustomerBase.customers){
            if(cus.getCustomerId().equals(cusId) && pass.equals(fun.decryptinPass(cus.getEncryptedPwd()))){
                System.out.println("Please enter the amount to be withdraw : ");
                double amt = sc.nextDouble();
                if(cus.getBalance() >= amt){
                    cus.setBalance(cus.getBalance()-amt);
                    TransactionDetails td = new TransactionDetails(cus.getName(),cus.getAccountNumber(), cus.getCustomerId(),(int)(Math.random() * (999990 - 100000 + 1)+ 100000)+"","ATMWithdrawl",amt,cus.getBalance());
                    transactionDetails.add(td);
                    if(!fun.topNCustomers(cus)){
                        fun.maintenanceFee(cus);
                    }
                    System.out.println("Current Balance amount is : "+cus.getBalance());
                }
            }
        }
    }

    public void accountTransfer(String cusId, String pass){
        Scanner sc = new Scanner(System.in);
        Functions fun = new Functions();
        for(CustomerInterface cus : InitializingCustomerBase.customers){
            if(cus.getCustomerId().equals(cusId) && pass.equals(fun.decryptinPass(cus.getEncryptedPwd()))){
                System.out.println("Please enter the Beneficiary account number : ");
                String receiverAccNo = sc.next();
                System.out.println("Please enter the amount to be send          : ");
                Double amtToBeSent = sc.nextDouble();
                for(CustomerInterface benCus : InitializingCustomerBase.customers){
                    if(benCus.getAccountNumber().equals(receiverAccNo)){
                        if(cus.getBalance() >= amtToBeSent){
                            benCus.setBalance(benCus.getBalance()+amtToBeSent);
                            cus.setBalance(cus.getBalance()-amtToBeSent);
                            TransactionDetails tdTo = new TransactionDetails(cus.getName(),cus.getAccountNumber(), cus.getCustomerId(),(int)(Math.random() * (999990 - 100000 + 1)+ 100000)+"","TransferTo "+benCus.getAccountNumber(),amtToBeSent,cus.getBalance());
                            transactionDetails.add(tdTo);
                            TransactionDetails tdFrom = new TransactionDetails(benCus.getName(),benCus.getAccountNumber(), benCus.getCustomerId(),(int)(Math.random() * (999990 - 100000 + 1)+ 100000)+"","TransferFrom "+cus.getAccountNumber(),amtToBeSent,benCus.getBalance());
                            transactionDetails.add(tdFrom);
                            fun.operationalFee(cus,amtToBeSent);
                            System.out.println("Amount sent successfully");
                            if(!fun.topNCustomers(cus)){
                                fun.maintenanceFee(cus);
                            }
                            System.out.println("Current Balance amount is : "+cus.getBalance());
                            break;
                        }
                        else{
                            System.out.println("Insufficient Balance!");
                            break;
                        }
                    }

                }
            }
        }
    }

    public void statement(String cusId, String pass){
        Scanner sc = new Scanner(System.in);
        Functions fun = new Functions();
        for(CustomerInterface cus : InitializingCustomerBase.customers){
            if(cus.getCustomerId().equals(cusId) && pass.equals(fun.decryptinPass(cus.getEncryptedPwd()))){
                System.out.println("TRANSACTION DETAILS");
                System.out.println("Name        : "+cus.getName());
                System.out.println("Account No  : "+cus.getAccountNumber());
                System.out.println("Customer Id : "+cus.getCustomerId());
                System.out.println();
                System.out.println(" TransId    |     TransType    |   Amount     |     Balance");
                for(TransactionDetails td : transactionDetails){
                    if(td.getCusId().equals(cusId)){
                        System.out.println(td.getTransactionId() +"     |    "+td.getTransType()+"    |   "+td.getAmount()+"    |   "+td.getBalance());
                    }
                }
            }
        }
    }
}
