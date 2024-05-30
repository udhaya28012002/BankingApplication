package org.application.BusinessFunctions;

import com.sun.org.apache.xml.internal.security.Init;
import org.application.InitializingCustomers.InitializingCustomerBase;
import org.application.TransactionDetail.TransactionDetails;
import org.application.customerPrivilages.CustomerInterface;

import java.util.*;

import org.application.AllBankingServices.BankingServices;

public class Functions {
    public String generateCusID(){
        boolean val = false;
        String cusId = new java.util.Random().nextInt(90)+10+"";
        for(CustomerInterface customerId : InitializingCustomerBase.customers){
            if(customerId.getCustomerId()==cusId){
                val = true;
                break;
            }
        }
        if(val){
            new Functions().generateCusID();
        }
        return cusId;
    }

    public String generateAccNo(){
        boolean val = false;
        String accNo = new java.util.Random().nextInt(90000)+10000+"";
        for(CustomerInterface accNum : InitializingCustomerBase.customers){
            if(Objects.equals(accNum.getAccountNumber(), accNo)){
                val = true;
                break;
            }
        }
        if(val){
            new Functions().generateAccNo();
        }
        return accNo;
    }
    public String encryptinPass(String pass){
        String encryptedText = "";

        for(int i=0; i<pass.length(); i++){
            char value = '\0';
            int va=0;
            if(Character.isDigit(pass.charAt(i))){
                va = Character.getNumericValue(pass.charAt(i));
            }
            else{
                value = pass.charAt(i);
            }
            if(value >= 65 && value < 90){
                encryptedText = encryptedText + (char)(value+1);
            }
            else if(value >= 97 && value < 122){
                encryptedText = encryptedText + (char)(value+1);
            } else if (va >=0 && va < 9) {
                encryptedText = encryptedText + (va+1);
            }
            if(value == 90 || value==122 || va== 9){
                switch (value){
                    case 90: encryptedText = encryptedText+(char)65;
                        break;
                    case 122: encryptedText = encryptedText+(char)97;
                        break;
                    case 9: encryptedText = encryptedText+0;
                }
            }
        }
        return  encryptedText;
    }

    public String decryptinPass(String pass){
        String decryptedText = "";

        for(int i=0; i<pass.length(); i++){
            char value = '\0';
            int va=0;
            if(Character.isDigit(pass.charAt(i))){
                va = Character.getNumericValue(pass.charAt(i));
            }
            else{
                value = pass.charAt(i);
            }
            if(value > 65 && value <= 90){
                decryptedText = decryptedText + (char)(value-1);
            }
            else if(value > 97 && value <= 122){
                decryptedText = decryptedText + (char)(value-1);
            } else if (va >0 && va <= 9) {
                decryptedText = decryptedText + (va-1);
            }
            if(value == 65 || value==97 || va== 0){
                switch (value){
                    case 65: decryptedText = decryptedText+(char)90;
                        break;
                    case 97: decryptedText = decryptedText+(char)122;
                        break;
                    case 0: decryptedText = decryptedText+9;
                }
            }
        }
        return  decryptedText;
    }

    //This function is written based on the name and the password and this is not used.
    public boolean validateUser(java.util.List<CustomerInterface> customerList, String name, String pass){
        boolean val = false;
        for(CustomerInterface cus : customerList){
            if(cus.getName().equalsIgnoreCase(name) && cus.getEncryptedPwd().equals(pass)){
                val = true;
                break;
            }
        }
        return  val;
    }


    /*
    This method is used for performing the password check and this is used for changing/updating the password.
    Below are done at this level
    1) Re-typing password check.
    2) Length check.
    3) UpperCase, LowerCase, Digit check.
    4) Previous pass check.
    We are continuously performing this operation un-till the user provides the required pattern.
    This function will end only when its return true.
     */
    public static String passwordComplexity(CustomerInterface cusObj){
        String val = "";
        Functions fun = new Functions();
        if(Functions.OTPVerification()){
            System.out.println("Please enter your pass");
            String passwordNeedToSet = new Scanner(System.in).next();
            System.out.println("Please re-type your pass");
            String retypeThePass = new Scanner(System.in).next();

            //Check the password entered and the re-typed password are correct.
            if((passwordNeedToSet.equals(retypeThePass))) {

                //We need to clear the previous pass list when it exceeds the size of 3.
                if(cusObj.getPreviousPassList().size() > 3){
                    cusObj.getPreviousPassList().remove(0);
                    cusObj.getPreviousPassList().remove(1);
                    cusObj.getPreviousPassList().remove(2);
                }


                //check if the current pass is matches with the prev 3 pass.
                if(!cusObj.getPreviousPassList().contains(fun.encryptinPass(passwordNeedToSet))){
                    //checking the length validation.
                    if(passwordNeedToSet.length() >= 6){
                        int upperCaseCount = (int) passwordNeedToSet.chars().filter(Character::isUpperCase).count();
                        int lowerCaseCount = (int) passwordNeedToSet.chars().filter(Character::isLowerCase).count();
                        int digitCount = (int) passwordNeedToSet.chars().filter(Character::isDigit).count();
                        //check if the cases constraints are reached.
                        if(upperCaseCount >= 2 && lowerCaseCount >=2 && digitCount >= 2){
                            val = "true"+passwordNeedToSet;
                        }
                        else{
                            System.out.println("The password need to contain least 2 lowercase, 2 uppercase and 2 numbers.");
                            Functions.passwordComplexity(cusObj);
                        }
                    }
                    else{
                        System.out.println("The entered password doesn't match the length");
                        Functions.passwordComplexity(cusObj);
                    }
                }
                else {
                    System.out.println("Password enter is of one of your previous password...\n please enter the new password!");
                    Functions.passwordComplexity(cusObj);
                }
            }
            else{
                System.out.println("The password entered is not matched....Please enter the pass again!");
                Functions.passwordComplexity(cusObj);
            }
        }
        return val;
    }

    /*
    This function is for verifying the pass constraints for the new users.
     */
    public static String passwordComplexity(){
        String val = "";
        System.out.println("Please enter your pass");
        String passwordNeedToSet = new Scanner(System.in).next();
        System.out.println("Please re-type your pass");
        String retypeThePass = new Scanner(System.in).next();
        //Check the password entered and the re-typed password are correct.
        if((passwordNeedToSet.equals(retypeThePass))) {
            //checking the length validation.
            if(passwordNeedToSet.length() >= 6){
                int upperCaseCount = (int) passwordNeedToSet.chars().filter(Character::isUpperCase).count();
                int lowerCaseCount = (int) passwordNeedToSet.chars().filter(Character::isLowerCase).count();
                int digitCount = (int) passwordNeedToSet.chars().filter(Character::isDigit).count();
                //check if the cases constraints are reached.
                if(upperCaseCount >= 2 && lowerCaseCount >=2 && digitCount >= 2){
                    val = "true"+passwordNeedToSet;
                }
                else{
                    System.out.println("The password need to contain least 2 lowercase, 2 uppercase and 2 numbers.");
                    Functions.passwordComplexity();
                }
            }
            else{
                System.out.println("The entered password doesn't match the length");
                Functions.passwordComplexity();
            }
        }
        else{
            System.out.println("The password entered is not matched....Please enter the pass again!");
            Functions.passwordComplexity();
        }
        return val;
    }

    public static boolean OTPVerification(){
        boolean val = false;
        final int OTP_VALIDITY_DURATION = 300000;
        int otp = (int) (Math.random() * (99999 - 10000 + 1)) + 10000;
        System.out.println("Please enter the number displayed on your screen to update the new Password...");
        System.out.println(otp);
        long timestamp = new Date().getTime();
        System.out.println("Enter the above displayed number within 5mins : ");
        int otpByUser = new java.util.Scanner(System.in).nextInt();
        long currentTimeStamp = new Date().getTime();
        //checking that the OTP is entered withing appropriate time.
        if((currentTimeStamp - timestamp) <= OTP_VALIDITY_DURATION){
            if(otp==otpByUser){
                val = true;
            }
            else {
                System.out.println("The code entered was not matched...Please re-try....");
                Functions.OTPVerification();
            }
        }
        else {
            System.out.println("Please re-type the newly generated otp!");
            Functions.OTPVerification();
        }
        return val;
    }

    //Force the user to change the pass once the transaction count crosses 5.
    public String ForcePassChange(String cusId){
        String val = "";
        for(CustomerInterface cus : InitializingCustomerBase.customers){
            if(cus.getCustomerId().equals(cusId)){
                    int transCount = cus.getTransactionCount();
                    System.out.println("Transaction Count : "+ cus.getTransactionCount());
                    if(transCount >= 5){
                        System.out.println("=================================================================");
                        System.out.println("For some security reason, we admit you to change the password, \nKindly follow the below steps to update the password!");
                        System.out.println("=================================================================");

                        String newPass = Functions.passwordComplexity(cus);
                        System.out.println(newPass);
                        Functions Fun = new Functions();
                        if(newPass.contains("true")) {
                            cus.setEncryptedPwd(Fun.encryptinPass(newPass.substring(4)));
                            cus.setPreviousPassList(Fun.encryptinPass(newPass.substring(4)));
                            System.out.println("Password has been changed successfully...");
                            cus.setTransactionCount(0);
                            val = newPass;
                            break;
                        }
                    }
            }
        }
        return val;
    }

    public void operationalFee(CustomerInterface cus, Double transactionAmt){
        if(transactionAmt > 5000.00){
            cus.setBalance(cus.getBalance()-10);
            TransactionDetails tdFrom = new TransactionDetails(cus.getName(),cus.getAccountNumber(), cus.getCustomerId(),(int)(Math.random() * (999990 - 100000 + 1)+ 100000)+"","Operational Fee",10.00,cus.getBalance());
            BankingServices.transactionDetails.add(tdFrom);
        }
    }

    public void maintenanceFee(CustomerInterface cus){
        if(cus.getTransactionCount() > 10){
            cus.setBalance(cus.getBalance()-100);
            TransactionDetails tdTo = new TransactionDetails(cus.getName(),cus.getAccountNumber(), cus.getCustomerId(),(int)(Math.random() * (999990 - 100000 + 1)+ 100000)+"","Maintenance Fee",100.00,cus.getBalance());
            BankingServices.transactionDetails.add(tdTo);
        }
    }

    public boolean topNCustomers(CustomerInterface cust){
        boolean val = false;
        String[] topNCus = new String[3];
        ArrayList<String> tempList = new ArrayList<>();
        for(CustomerInterface cus : InitializingCustomerBase.customers){
            tempList.add(cus.getCustomerId() + "_" + cus.getBalance());
        }
        Collections.sort(tempList);
        String val1 = tempList.get(tempList.size()-1);
        topNCus[0] = val1.substring(val1.indexOf('_')+1);
        String val2 = tempList.get(tempList.size()-2);
        topNCus[1] = val1.substring(val2.indexOf('_')+1);
        String val3 = tempList.get(tempList.size()-3);
        topNCus[2] = val1.substring(val3.indexOf('_')+1);
        tempList.clear();
        System.out.println(Arrays.toString(topNCus));
        String custId = cust.getCustomerId();
        if(custId.equals(topNCus[0]) || custId.equals(topNCus[1]) || cust.equals(topNCus[2])){
            val = true;
        }
        return val;
    }
    public void displayPasswordConstraintMessagesOnScreen(){
        System.out.println();
        System.out.println("The password need to match the below constraints:");
        System.out.println("Minimum length of 6 character, least 2 UpperCase character, 2 LowerCaseCharacter and 2 Numbers....");
        System.out.println();
    }
}
