/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Assignment1;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.text.SimpleDateFormat;  
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 

//comment

class TransactionTest {
    /////////////////////// POSITIVE CASES ///////////////////////

    //Checking that the checkBalance function returns the card balance correctly
    @Test 
    public void checkBalanceTest() {
        try {
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
            Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
            Card c = new Card("jimbo jimmy", "12345", startDate, expiryDate, 1234, 5000.00, null);
            ATM atm = new ATM("somefile.csv");
            Transaction t = new Transaction(c, atm, "Deposit", atm.transactionNumber);
            assertEquals(t.checkBalance(), 5000);
        } catch(Exception e){
            System.out.println("error");
        }
    }

    //A trivial withdraw case to check that withdrawal deducts the correct funds and notes from the card account and the atm, and correctly sets the transaction amount
    @Test
    public void trivialWithdrawTest() {
        try {
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
            Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
            Card c = new Card("jimbo jimmy", "12345", startDate, expiryDate, 1234, 5000.00, null);
            ATM atm = new ATM("somefile.csv");
            Transaction t = new Transaction(c, atm, "Withdraw", atm.transactionNumber);
            t.withdraw(250.00);
            assertEquals(c.getFunds(), 4750.00);  //Checking the card funds have been reduced
            assertEquals(atm.getFunds(), 94175.00);  //Checking the atm funds have been reduced
            double[] denominations = new double[] { 100, 50, 20, 10, 5, 2, 1, 0.5, 0.2, 0.1, 0.05 };
            HashMap<Double, Integer> denomMap = new HashMap<Double, Integer>();
            for (double denom : denominations) {
                denomMap.put(denom, 500);
            }
            denomMap.put(100.00, 498);
            denomMap.put(50.00, 499);
            assertEquals(atm.denominationsMap, denomMap); //Checking the denominations in the atm are correct
            assertEquals(t.transactionAmount, 250); //Checking the transaction amount is correct
        } catch(Exception e){
            System.out.println("error");
        } 
    }


    //Checking that depositting functions correctly by adding the correct denominations to atm and increases card funds appropriately
    @Test
    public void depositTest() {
        try {
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
            Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
            Card c = new Card("jimbo jimmy", "12345", startDate, expiryDate, 1234, 5000.00, null);
            ATM atm = new ATM("somefile.csv");
            double[] amount = {100.00, 50.00, 50.00, 10.00, 20.00, 5.00};
            Transaction t = new Transaction(c, atm, "Deposit", atm.transactionNumber);
            double[] denominations = new double[] { 100, 50, 20, 10, 5, 2, 1, 0.5, 0.2, 0.1, 0.05 };
            HashMap<Double, Integer> denomMap = new HashMap<Double, Integer>();
            for (double denom : denominations) {
                denomMap.put(denom, 500);
            }
            assertEquals(atm.denominationsMap, denomMap);
            t.deposit(amount);
            denomMap.put(100.00, 501);
            denomMap.put(50.00, 502);
            denomMap.put(10.00, 501);
            denomMap.put(20.00, 501);
            denomMap.put(5.00, 501);
            assertEquals(atm.denominationsMap, denomMap);
            assertEquals(atm.getFunds(), 94660.0);
            assertEquals(c.getFunds(), 5235.0); 
            assertEquals(t.transactionAmount, 235.0);
        } catch(Exception e){
            System.out.println("error");
        }
    }

    //Checking that the receipt prints the correct information before and after depositing
    @Test 
    public void depositReceiptTest() {
        try {
            DateTimeFormatter dtObj = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
            LocalDateTime dtnow = LocalDateTime.now();  
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
            Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
            Card c = new Card("jimbo jimmy", "12345", startDate, expiryDate, 1234, 5000.00, null);
            ATM atm = new ATM("somefile.csv");
            Transaction t = new Transaction(c, atm, "Deposit", atm.transactionNumber);
            ArrayList<String> arr = new ArrayList<String>(Arrays.asList(t.printRecipt(),dtObj.format(dtnow)));
            double[] amount = {100.00, 50.00, 50.00};
            String initial = "\n##################################################\n" +
                                "################## NAAAR ATM \u00a9 ###################\n" +
                                "##################################################\n\n"+
                                "Date: " + arr.get(1) + "\n" + 
                                "Name: " +  "jimbo jimmy" + "\n" +
                                "Card Number: " +  "XX"+  "345" + "\n" + 
                                "Transaction Number: " + String.valueOf(t.transactionNumber) + "\n" +
                                "Transaction Type: " + "Deposit" + "\n\n" +
                                "----------------------------------------------------\n" +
                                "Account Balance:                " + "5000.0" + "\n" +
                                "Transaction Amount:                " + "0.0" + "\n" +
                                "\n##################################################\n";
            assertEquals(arr.get(0), initial);
            t.deposit(amount);
            ArrayList<String> arr2 = new ArrayList<String>(Arrays.asList(t.printRecipt(),dtObj.format(dtnow)));
            String expected = "\n##################################################\n" +
                                "################## NAAAR ATM \u00a9 ###################\n" +
                                "##################################################\n\n"+
                                "Date: " + arr2.get(1) + "\n" + 
                                "Name: " +  "jimbo jimmy" + "\n" +
                                "Card Number: " +  "XX"+  c.getCardNumber().substring(c.getCardNumber().length() - 3) + "\n" + 
                                "Transaction Number: " + String.valueOf(t.transactionNumber) + "\n" +
                                "Transaction Type: " + "Deposit" + "\n\n" +
                                "----------------------------------------------------\n" +
                                "Account Balance:                " + "5200.0" + "\n" +
                                "Transaction Amount:                " + "200.0" + "\n" +
                                "\n##################################################\n";
            assertEquals(arr2.get(0), expected);
        } catch(Exception e){
            System.out.println("error");
        }

    }

    //Checking that the receipt prints the correct information before and after withdrawing
    @Test
    public void withdrawReceiptTest() {
        try {
            DateTimeFormatter dtObj = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
            LocalDateTime dtnow = LocalDateTime.now();  
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
            Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
            Card c = new Card("jimbo jimmy", "12345", startDate, expiryDate, 1234, 5000.00, null);
            ATM atm = new ATM("somefile.csv");
            Transaction t = new Transaction(c, atm, "Withdraw", atm.transactionNumber);
            ArrayList<String> arr = new ArrayList<String>(Arrays.asList(t.printRecipt(),dtObj.format(dtnow)));
            String initial = "\n##################################################\n" +
                                "################## NAAAR ATM \u00a9 ###################\n" +
                                "##################################################\n\n"+
                                "Date: " + arr.get(1) + "\n" + 
                                "Name: " +  "jimbo jimmy" + "\n" +
                                "Card Number: " +  "XX"+  "345" + "\n" + 
                                "Transaction Number: " + String.valueOf(t.transactionNumber) + "\n" +
                                "Transaction Type: " + "Withdraw" + "\n\n" +
                                "----------------------------------------------------\n" +
                                "Account Balance:                " + "5000.0" + "\n" +
                                "Transaction Amount:                " + "0.0" + "\n" +
                                "\n##################################################\n";
            assertEquals(arr.get(0), initial);
            t.withdraw(234.65);
            ArrayList<String> arr2 = new ArrayList<String>(Arrays.asList(t.printRecipt(),dtObj.format(dtnow)));
            String expected = "\n##################################################\n" +
                                "################## NAAAR ATM \u00a9 ###################\n" +
                                "##################################################\n\n"+
                                "Date: " + arr2.get(1) + "\n" + 
                                "Name: " +  "jimbo jimmy" + "\n" +
                                "Card Number: " +  "XX"+  c.getCardNumber().substring(c.getCardNumber().length() - 3) + "\n" + 
                                "Transaction Number: " + String.valueOf(t.transactionNumber) + "\n" +
                                "Transaction Type: " + "Withdraw" + "\n\n" +
                                "----------------------------------------------------\n" +
                                "Account Balance:                " + "4765.35" + "\n" +
                                "Transaction Amount:                " + "234.65" + "\n" +
                                "\n##################################################\n";
            assertEquals(arr2.get(0), expected);

        } catch(Exception e){
            System.out.println("error");
        }
    }

    /////////////////////// NEGATIVE CASES ///////////////////////

    //Checking that the withdrawal function deducts smaller denominations when atm doesn't have enough of larger denominations, and decreases balances appropriately
    @Test
    public void withdrawTest() {
        try {
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
            Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
            Card c = new Card("jimbo jimmy", "12345", startDate, expiryDate, 1234, 5000.00, null);
            ATM atm = new ATM("somefile.csv");
            atm.denominationsMap.put(100.00, 1);
            Transaction t = new Transaction(c, atm, "Withdraw", atm.transactionNumber);
            t.withdraw(300.00);
            assertEquals(c.getFunds(), 4700.00);  //Checking the card funds have been reduced
            assertEquals(atm.getFunds(), 44225.00);  //Checking the atm funds have been reduced
            double[] denominations = new double[] { 100, 50, 20, 10, 5, 2, 1, 0.5, 0.2, 0.1, 0.05 };
            HashMap<Double, Integer> denomMap = new HashMap<Double, Integer>();
            for (double denom : denominations) {
                denomMap.put(denom, 500);
            }
            denomMap.put(100.00, 0);
            denomMap.put(50.00, 496);
            assertEquals(atm.denominationsMap, denomMap); //Checking the denominations in the atm are correct
            assertEquals(t.transactionAmount, 300); //Checking the transaction amount is correct
        } catch(Exception e){
            System.out.println("error");
        }
    }

    //Checking that the appropriate message is returned when a card has insufficient balance to withdraw
    @Test
    public void insufficientCardFundsTest() {
        try {
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
            Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
            Card c = new Card("jimbo jimmy", "12345", startDate, expiryDate, 1234, 5000.00, null);
            ATM atm = new ATM("somefile.csv");
            atm.denominationsMap.put(100.00, 1);
            Transaction t = new Transaction(c, atm, "Withdraw", atm.transactionNumber);
            assertEquals(t.withdraw(5500.00), "card insufficient");
            assertEquals(c.getFunds(), 5000.00);
        } catch(Exception e){
            System.out.println("error");
        }
    }

    //Checking that the appropriate message is returned when a atm has insufficient balance to withdraw
    @Test
    public void insufficientATMFundsTest() {
        try {
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
            Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
            Card c = new Card("jimbo jimmy", "12345", startDate, expiryDate, 1234, 5000.00, null);
            ATM atm = new ATM("somefile.csv");
            for (Map.Entry denomination : atm.denominationsMap.entrySet()) {
                atm.denominationsMap.put((double) denomination.getKey(), 0);
            }
            atm.denominationsMap.put(100.00, 1);
            Transaction t = new Transaction(c, atm, "Withdraw", atm.transactionNumber);
            assertEquals(t.withdraw(150.00), "atm insufficient");
            assertEquals(c.getFunds(), 5000.00);
            assertEquals(atm.getFunds(), 100.00);
        } catch(Exception e){
            System.out.println("error");
        }
    }

    //Checking that coins are invalid and do not increate the funds of the card account or the atm denominations
    @Test
    public void coinDepositTest() {
        try {
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
            Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
            Card c = new Card("jimbo jimmy", "12345", startDate, expiryDate, 1234, 5000.00, null);
            ATM atm = new ATM("somefile.csv");
            double[] amount = {100.00, 50.00, 2, 1, 0.5, 0.2, 0.1, 0.05 };
            Transaction t = new Transaction(c, atm, "Deposit", atm.transactionNumber);
            double[] denominations = new double[] { 100, 50, 20, 10, 5, 2, 1, 0.5, 0.2, 0.1, 0.05 };
            HashMap<Double, Integer> denomMap = new HashMap<Double, Integer>();
            for (double denom : denominations) {
                denomMap.put(denom, 500);
            }
            assertEquals(atm.denominationsMap, denomMap);
            t.deposit(amount);
            denomMap.put(100.00, 501);
            denomMap.put(50.00, 501);
            assertEquals(atm.denominationsMap, denomMap);
            assertEquals(atm.getFunds(), 94575.0);
            assertEquals(c.getFunds(), 5150.0); 
            assertEquals(t.transactionAmount, 150.0);
        } catch(Exception e){
            System.out.println("error");
        }
    }

    /////////////////////// EDGE CASES ///////////////////////

    //Checking that correct error message is returned when negative amount is attempted to with withdrawn
    @Test
    public void negativeWithdrawal() {
        try {
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
            Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
            Card c = new Card("jimbo jimmy", "12345", startDate, expiryDate, 1234, 5000.00, null);
            ATM atm = new ATM("somefile.csv");
            Transaction t = new Transaction(c, atm, "Withdraw", atm.transactionNumber);
            assertEquals(t.withdraw(-150.00), "negative withdrawal");
            assertEquals(c.getFunds(), 5000.00);
            assertEquals(atm.getFunds(), 94425.0);
        } catch(Exception e){
            System.out.println("error");
        }
    }

    //Checking that inappropriate withdrawal e.g. $34.57 is rounded down to the nearest cent.
    @Test
    public void inappropriateWithdrawal () {
        try {
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
            Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
            Card c = new Card("jimbo jimmy", "12345", startDate, expiryDate, 1234, 5000.00,null);
            ATM atm = new ATM("somefile.csv");
            Transaction t = new Transaction(c, atm, "Withdraw", atm.transactionNumber);
            t.withdraw(100.53);
            assertEquals(c.getFunds(), 4899.5);
            assertEquals(atm.getFunds(), 94324.5);
            assertEquals(t.transactionAmount, 100.5);
        } catch(Exception e){
            System.out.println("error");
        }
    }
}