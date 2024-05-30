package org.application.customerPrivilages;

import java.util.LinkedList;

class Customer implements CustomerInterface{
        private final String customerId;
        private final String accountNumber;
        private final String name;
        private double balance;
        private String encryptedPwd;

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    private int transactionCount;



    private final java.util.LinkedList<String> previousPassList;

        /*
        This is the constructor which will holds all the details of single individual customer.
        We are creating each instances of arrayList when the new customer is added.
         */
        public Customer(String customerId, String accountNumber, String name, double balance, String encryptedPwd) {
            this.customerId = customerId;
            this.accountNumber = accountNumber;
            this.name = name;
            this.balance = balance;
            this.encryptedPwd = encryptedPwd;
            previousPassList = new LinkedList<>();
            this.transactionCount = 0;
            previousPassList.add(encryptedPwd);
        }

        public String getName() {
            return name;
        }
        public String getCustomerId(){
            return customerId;
        }

        public double getBalance() {
            return balance;
        }

        public String getAccountNumber() {
                return accountNumber;
            }

            public String getEncryptedPwd() {
                return encryptedPwd;
            }

        public void setEncryptedPwd(String encryptedPwd) {
            this.encryptedPwd = encryptedPwd;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public LinkedList<String> getPreviousPassList() {
            return previousPassList;
        }
    public void setPreviousPassList(String pass) {
        previousPassList.add(pass);
    }
}
