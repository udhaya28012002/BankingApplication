package org.application.customerPrivilages;

public class CustomerFactory {
    public static CustomerInterface createCustomer(String customerId, String accountNumber, String name, double balance, String encryptedPwd) {
        return new Customer(customerId, accountNumber, name, balance, encryptedPwd);
    }
}
