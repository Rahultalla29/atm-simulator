// Assignment1/app/src/main/java/Assignment1

package Assignment1;
import Assignment1.Card;
import Assignment1.Transaction;
import java.util.*;

import java.io.FileWriter;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.text.SimpleDateFormat;  
import java.util.Date;  

  
import java.text.DateFormat; 
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Instant;
import java.time.ZonedDateTime;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class ATM {
    
    public double funds;
    private ArrayList<Card> validCards;
    private String cardFileName;
    public int transactionNumber;
    public boolean admin;
    public HashMap<Double, Integer> denominationsMap;
    
 
    public ATM(String cardFileName) {
        this.funds = 0;    //add into atm class?
        this.transactionNumber = 0;
        this.validCards = new ArrayList<Card>();
        this.admin = false;
        // check if valid file in order to make a instance 
        // if more cards have to be added then updated csv and run again?
        this.cardFileName = cardFileName;

        //RUNNING CSV PARSER AT INSTANTIATE
        this.setValidCards();


        double[] denominations = new double[] { 100, 50, 20, 10, 5, 2, 1, 0.5, 0.2, 0.1, 0.05 };
        HashMap<Double, Integer> denominationsMap = new HashMap<Double, Integer>();
        for (double denom : denominations) {
            denominationsMap.put(denom, 500);
        }
        this.denominationsMap = denominationsMap;
    }
    // what do i parse in? card no and pin?
    public Object[] cardValidation(String cardNo, int pin){

        //Pair<boolean, String> value = Pair.with(false,"Invalid Card Number/pin"); 
        Object[] value = {false, "Invalid Card Number/pin"};

        // involves the password checkig emthods
        Card correctCard = this.getCard(cardNo, pin);
        //int start = correctCard.getstartDate().compareTo(java.time.LocalDate.now());
        if (correctCard != null) {
            Date todayDate = new Date();
            // check if correct time and status
            if (!todayDate.after(correctCard.getstartDate())){
                
                value[1] = "Your card cannot be used before start date.";
            }
            else if (!todayDate.before(correctCard.getexpiryDate())){
                
                value[1] = "Your card has been expired.";
                
            }
            else if (correctCard.status() == false){
                value[1] = "Card Activity has been deactivated.";
                
            }
            else if (correctCard.getValid() != null & correctCard.getValid().equals("stolen")){
                value[1] = "The card being used has been stolen. It will now be confiscated. Our Apologies for any inconvenience.";
                // change status now potentially
            }
            else if (correctCard.getValid() != null & correctCard.getValid().equals("lost")){
                value[1] = "The card being used has been reported lost. It will now be confiscated. Our Apologies for any inconvenience.";
                // change status now potentially
            }
            else {
                value[0] = true;
                value[1] = "Confirmed details.";
                
            }
        }

        return value;


    }







    public Card getCard( String cardNo, int pin){

        for (int i =0; i <this.validCards.size(); i++){
            if (this.validCards.get(i).getCardNumber().equals(cardNo) & this.validCards.get(i).getPin() == pin){
                
                return validCards.get(i);
            }
        }
        return null;
    }


    // if smae card nubmer and different pin
    public Card getInvalidCard( String cardNo){

        for (int i =0; i <this.validCards.size(); i++){
            if (this.validCards.get(i).getCardNumber().equals(cardNo)){
                
                return validCards.get(i);
            }
        }
        return null;
    }


    public String cardTransactionMethod(Card card, String transactionType){


        this.setTransactionNumber(this.getTransactionNumber() + 1);
        Transaction transaction = new Transaction(card, this, transactionType,this.getTransactionNumber());
        if (transactionType.equals("BALANCE CHECK")){
            String balance = String.valueOf(transaction.checkBalance());
            return balance;
        }
        else if (transactionType.equals("BALANCE RECEIPT")){
            String balance = transaction.printRecipt();
            return balance;
        }
        return "wrong transaction type";
       

    }

    public String cardTransactionMethod(Card card, String transactionType, double amount){
        // only for withdrawal



        this.setTransactionNumber(this.getTransactionNumber() + 1);
        Transaction transaction = new Transaction(card, this, transactionType,this.getTransactionNumber());
        // reutrn true if successful for transaction class as well
        if (transactionType == "WITHDRAWAL"){
            return transaction.withdraw(amount);
        }
        return "wrong transaction type";

    }

    public String cardTransactionMethod(Card card, String transactionType, double[] amount){
        // only for deposit

        this.setTransactionNumber(this.getTransactionNumber() + 1);
        Transaction transaction = new Transaction(card, this, transactionType,this.getTransactionNumber());
        // reutrn true if successful for transaction class as well

        if ((transactionType == "DEPOSIT")){
            String receipt = transaction.deposit(amount);
            return "\nSuccessfully Deposited!\n" + receipt;
        }
       return "wrong transaction type";

    }




    // getter and setter methods

    public double getFunds(){
        // calcualte from denomination map
        
        // HashMap<Double, Integer> denominationsMap = new HashMap<Double, Integer>();
        double value = 0.00;
        for (Map.Entry mapElement : denominationsMap.entrySet()) {
            
            value = value + (double) mapElement.getKey() * (int) mapElement.getValue();
        }
        this.funds = value;
        return this.funds;

    }

    public boolean setFunds(HashMap<Double, Integer> amount){
        // only thing left 
        if (this.admin){
            for (Map.Entry mapElement : amount.entrySet()) {
                int initialNumNotes = denominationsMap.get(mapElement.getKey());
                int addedNumNotes = (int) mapElement.getValue();
                denominationsMap.put((double) mapElement.getKey(), initialNumNotes + addedNumNotes);
            }
            return true;
        }
        return false;
    }


    public ArrayList<Card> getValidCards(){
        return this.validCards;
    }

    
    // assuming the file follows comma structure
    public void setValidCards(){
        ArrayList<String> cards = new ArrayList<String>();
        try{

        
            File file = new File(cardFileName);
            Scanner scanner = new Scanner(file);
            //ArrayList<String> cards = new ArrayList<String>();
            
            // convert to list of strings 
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                cards.add(line);
            }
            scanner.close();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }

        //split each string into an array
        for(int i =0; i <cards.size(); i++){
            String card = cards.get(i);
            String[] splitCard = card.split(",");
            
            // [String name, int cardNumber,int startDate,int expiryDate,int pin, double funds]
            String name = splitCard[0];
            //int CardNumber = Integer.parseInt(splitCard[1]);
            String CardNumber = splitCard[1];
            // convert to date object? might be better
            try{
                Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(splitCard[2]);
                Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse(splitCard[3]);
                int pin = Integer.parseInt(splitCard[4]);

                double funds = Double.parseDouble(splitCard[5]);
                String valid = splitCard[6];
            
                // create card object
                Card newCard = new Card(name, CardNumber, startDate, expiryDate, pin, funds,valid);

                this.validCards.add(newCard);
            }
            catch (Exception e){
                // nothing
            }

            //int startDate = splitCard.get(2);
            //int expiryDate = splitCard.get(3);

            
            
        }


    }
    public void updateCardCsv(){
        //import java.io.FileWriter;  // Import the File class
        //import java.io.IOException;  // Import the IOException class to handle errors
        try {
            FileWriter myWriter = new FileWriter(this.cardFileName);
            for(int i = 0; i < this.getValidCards().size(); i++){
                Card card = this.getValidCards().get(i);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
                String start = dateFormat.format(this.getValidCards().get(i).getstartDate());
                String expiry = dateFormat.format(this.getValidCards().get(i).getexpiryDate());
                String line = card.getName()+","+card.getCardNumber()+","+start+","+expiry+","+Integer.toString(card.getPin())+","+Double.toString(card.getFunds())+","+card.getValid()+"\n";
                myWriter.write(line);          
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public int getTransactionNumber(){
        return this.transactionNumber;
    }
    
    public void setTransactionNumber(int newNumber){
        this.transactionNumber = newNumber;
    }

    public boolean isAdmin(){
        return this.admin;
    }
    // do we need this method?
    public void setAdmin(boolean value){
        this.admin = value;
    }


}