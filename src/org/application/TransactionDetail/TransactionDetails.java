package org.application.TransactionDetail;

import org.application.InitializingCustomers.InitializingCustomerBase;
import org.application.customerPrivilages.CustomerInterface;

public class TransactionDetails {
    private String name;
    private String accNo;
    private String cusId;
    private String transactionId;
    private String transType;
    private Double amount;
    private Double balance;

    public  TransactionDetails(String name, String accNo, String cusId, String transactionId, String transType, Double amount, Double balance){
        this.name = name;
        this.accNo = accNo;
        this.cusId = cusId;
        this.transactionId = transactionId;
        this.transType = transType;
        this.amount = amount;
        this.balance = balance;
        for(CustomerInterface cus : InitializingCustomerBase.customers){
            if(cus.getCustomerId().equals(cusId)){
                cus.setTransactionCount(cus.getTransactionCount() + 1);
                break;
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getAccNo() {
        return accNo;
    }

    public String getCusId() {
        return cusId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getTransType() {
        return transType;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getBalance() {
        return balance;
    }

}
