package org.application.customerPrivilages;

import java.util.LinkedList;

public interface CustomerInterface {
    String getCustomerId();
    String getAccountNumber();
    String getName();
    double getBalance();
    String getEncryptedPwd();
    int getTransactionCount();
    LinkedList<String> getPreviousPassList();

    void setTransactionCount(int transactionCount);
    void setEncryptedPwd(String encryptedPwd);
    void setBalance(double balance);
    void setPreviousPassList(String pass);
}

