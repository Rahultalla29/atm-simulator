package Assignment1;
import java.util.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 


public class Transaction {

    public double transactionAmount;

    public String transactionType;
    public int transactionNumber;
    private Card card;
    private ATM atm;

    public Transaction(Card card, ATM atm, String transactionType, int transactionNumber) {
        this.transactionType = transactionType;
        this.transactionNumber = transactionNumber;
        this.card = card;
        this.atm = atm;
        this.transactionAmount = 0;
    }

    public boolean cardHasSufficientFunds(double required) {
        return required < card.getFunds(); 
    }

    public boolean atmHasSufficientFunds(double required) {
        return required < atm.getFunds(); 
    }

    public double checkBalance() {
        return card.getFunds();
    }


    public String withdraw(double amount) {
        double amount2 = amount;
        if (!cardHasSufficientFunds(amount)) {
            return "card insufficient";
        } else if (!atmHasSufficientFunds(amount)) {
            return "atm insufficient";
        } else if (amount < 0) {
            return "negative withdrawal";
        } else {
            double[] denominations = new double[] { 100, 50, 20, 10, 5, 2, 1, 0.5, 0.2, 0.1, 0.05 };
            int[] moneyCounter = new int[11];

            for (int i = 0; i < 11; i++) {
                if (amount >= denominations[i]) {
                    int numDenoms = (int) (amount / denominations[i]);
                    if ((atm.denominationsMap.get(denominations[i]) - numDenoms) < 0) {
                        moneyCounter[i] = atm.denominationsMap.get(denominations[i]);
                        amount = amount - moneyCounter[i] * denominations[i]; 
                        // atm.denominationsMap.put(denominations[i], 0);
                        continue;
                    }
                    moneyCounter[i] = numDenoms;
                    amount = amount - moneyCounter[i] * denominations[i]; 
                }


            }
            HashMap<Double, Integer> money = new HashMap<Double, Integer>();
            for (int i = 0; i < 11; i++) {
                if (moneyCounter[i] != 0) {
                    money.put(denominations[i], moneyCounter[i]);
                    atm.denominationsMap.put(denominations[i], atm.denominationsMap.get(denominations[i]) - moneyCounter[i]);
                }
            }

        }
        if (amount2 * 100 % 5 != 0) {
            amount2 -= (amount2 * 100 % 5) / 100;
        }

        this.card.funds -= amount2;
        this.transactionAmount = amount2;
        return "\nSuccessfully Withdrew!\n" + this.printRecipt();
    }

    public String deposit(double[] amount) {
        double[] validDenominations = new double[] { 100, 50, 20, 10, 5 };
        ArrayList<Double> valids = new ArrayList<Double>();
        valids.add(100.00);
        valids.add(50.00);
        valids.add(20.00);
        valids.add(10.00);
        valids.add(5.00);

        double sum = 0;
        for (double value : amount) {
            if (valids.contains(value)) {
                sum += value;
            }
        }
        for (double value : amount) {
            for (int i = 0; i < validDenominations.length; i++) {
                if (value == validDenominations[i]) {
                    atm.denominationsMap.put(validDenominations[i], atm.denominationsMap.get(validDenominations[i]) + 1);
                }
            }
        }
         this.card.funds += sum;
        this.transactionAmount = sum;
        return this.printRecipt();
    }

    public String printRecipt() {
        //going to return the relevant information to be used in main to print to screen
        DateTimeFormatter dtObj = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
        LocalDateTime dtnow = LocalDateTime.now();  

        String receipt = "\n##################################################\n" +
                         "################## NAAAR ATM \u00a9 ###################\n" +
                         "##################################################\n\n"+
                         "Date: " + dtObj.format(dtnow) + "\n" + 
                         "Name: " +  card.getName() + "\n" +
                         "Card Number: " +  "XX"+  card.getCardNumber().substring(card.getCardNumber().length() - 3) + "\n" + // % 100000
                        "Transaction Number: " + String.valueOf(transactionNumber) + "\n" +
                        "Transaction Type: " + String.valueOf(transactionType) + "\n\n" +
                        "----------------------------------------------------\n" +
                        "Account Balance:                " + String.valueOf(card.getFunds() + "\n" +
                        "Transaction Amount:                " + String.valueOf(transactionAmount) + "\n" +
                        "\n##################################################\n" );

        return receipt;
    }

}

