// /*
//  * This Java source file was generated by the Gradle 'init' task.
//  */

// 1. NEED TO CHECK WITHDRAWAL,DEPOSIT etc are correct and csv is being changed correctly
// Modify CSV for Deposit and Withdraw  and ATM testing- Akasha
// App Testing - Rahul testttt
// Interface and format and App logic - Negeen 
// 2. All cases are covered (testing)
// 3. Testing for App.java

// gradle run --console=plain -q

// gradle --stop

// S
package Assignment1;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.*; 

public class App {
 
    private final String cardFile = "src/resources/cards.csv"; /*  needs to be made final */
    private final int admin = 1234;
    private boolean status;
    private ATM atm;
    private Card currentCustomer;

    public App() {
        this.atm = new ATM(this.cardFile);
        this.status = true;
    }

    public static boolean isNumeric(String str) { 
        try {  
          Double.parseDouble(str);  
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
      }

    public boolean takeCustomerDetails(Scanner input) {
        boolean status = true;
        int maxAttempt = 1;
        String cardNoSame = null;
        int cardNoSameCount = 0;
        while(status){


            boolean cardNoBool = true;
            String cardNo = null;
            while (cardNoBool) {
                System.out.print("\n Please Enter Card Number OR EXIT to exit: ");  
                cardNo = input.next();
                if (cardNo.toUpperCase().equals("EXIT")) {
                    status = false;
                    this.status = false;
                    input.close();
                    return false;
                } else if (cardNo.length() != 5 || !isNumeric(cardNo)) {
                    System.out.println(" Invalid Card Number.");
                    continue;
                } else if (this.atm.getInvalidCard(cardNo) == null) {
                    System.out.println(" Sorry This Card Does not Exist!");
                    continue;
                }
                else { cardNoBool = false; }
            }

            /// THEY GIVE CARDnO we check if it is a valid card No
            // iN LOOP FOR 3 TIMES UNITL CORRECT UNTIL
            Object value = false;
            int pin = 0;
            if (this.atm.getInvalidCard(cardNo) != null){

                String strPin = null;
                int i = 0;
                while (i < 3) {
                    System.out.print("\n Please Enter PIN OR EXIT to exit: ");  
                    strPin = input.next();
                    if (strPin.toUpperCase().equals("EXIT")) {
                        status = false;
                        this.status = false;
                        input.close();
                        return false;
                    } else if (strPin.length() != 4 || !isNumeric(strPin)) {
                        System.out.println(" Invalid Pin.");
                        i+=1;
                        continue;
                    }
                    else{        
                        pin = Integer.parseInt(strPin);

                        this.currentCustomer = this.atm.getCard(cardNo, pin);
                        value = this.atm.cardValidation(cardNo,pin)[0]; 
                        i += 1;
                        if (((Boolean) value).booleanValue()){
                            break;
                        }        
                    }
                        
                }

 

            }




            Object description = this.atm.cardValidation(cardNo,pin)[1];
            
            boolean di = ((Boolean) value).booleanValue();
            String response = String.valueOf(description);

            if (this.currentCustomer != null) {
                this.currentCustomer.status = di;
            }
            
            //boolean di = false; // FOR TESTING PURPOSES
            if (di){
                //this.currentCustomer = this.atm.getCard(cardNo, pin);
                System.out.println("\nYour Login was sucessful! Welcome "+this.currentCustomer.getName()); 
                status = false;
                //input.close();
                return true;
                
            }
            else{

                System.out.println("\n We are sorry!\nYour card has been deactived due to too many invalid attempts.\n");
                status = false;
                this.status = false;
                // add teh stolen/lost/inactive cases  
            }      
            

            if (response != null) {
                if (this.currentCustomer != null && !this.currentCustomer.status()) {

                    System.out.println("\n We are sorry!\n Your Login was unsuccessful! Your card has been deactivated. " + response); 
                    status = false;
                    this.status = false;

                }
            }else {
                this.status = false;
                status = false;}
               
                
        } 
        return false;
                    
    }

    public boolean takeAdminDetails(Scanner input) {
        boolean status = true;
        while(status){            
            System.out.print(" Please Enter Admin Access Code OR 1 to EXIT: ");  
            int adminCode = input.nextInt();

            if (adminCode == 1 ) {
                this.status = false;
                input.close();
                status = false;
            }

            else if (adminCode == this.admin ) {
                System.out.println("Your Admin Access was sucessful! " );
                this.atm.setAdmin(true); 
                //input.close();
                status = false;
                return true;}

            else {  System.out.println("Your Admin Access was unsuccessful. Try Again" ); } 
        }
        return false;
    }


    public void adminDeposit(Scanner input){
        boolean status = true;
        
        while(status){
            System.out.println("\nPlease select from the following options: ");
            System.out.println("1. DEPOSIT");
            System.out.println("2. EXIT");
            System.out.print("Please enter your required action: ");
        
            String action = input.next();

            if(action.toUpperCase().equals("DEPOSIT") || action.toUpperCase().equals("EXIT")) {
                switch (action.toUpperCase()) {
                    case "DEPOSIT":
                    ArrayList<Double> validEntries = new ArrayList<Double>(Arrays.asList(100.00, 50.00, 20.00, 10.00, 5.00, 2.00, 1.00, 0.50, 0.20, 0.10, 0.05));
                    HashMap<Double, Integer> notes = new HashMap<Double, Integer>();
                    boolean status2 = true;
                    System.out.println("\nEnter 0 at anytime to finalise deposit OR enter 1 at anytime to EXIT\n" +
                    "\nCurrent Bank Balance: $" + Double.toString(this.atm.getFunds()) + "\n");
                    while (status2) {
                        System.out.println("Enter note value and number of notes (space delimited):");
                        double note = (double) input.nextInt();
                        if (note == 1.00) { 
                            this.status = false;
                            input.close();
                            status = false;
                            status2 = false;   
                        } 
                        //Finalise deposit
                        else if (note == 0.00) {
                            this.atm.setFunds(notes);
                            System.out.println("Finalised deposit successfully!\n Logging out of ADMIN Mode.\n" +
                            "New Bank Balance: $" + Double.toString(this.atm.getFunds()) + "\n");
                            status2 = false;
                            status = false;
                        } else {
                            String strNumNotes = input.next();
                            if (isNumeric(strNumNotes)) {
                                if (validEntries.contains(note) && Integer.parseInt(strNumNotes) <= 500 && Integer.parseInt(strNumNotes) >= 0) {
                                    int numNotes = Integer.parseInt(strNumNotes);
                                    notes.put(note, numNotes);
                                } else { System.out.println("Invalid entry."); }
                            } else { System.out.println("Invalid entry."); }
                        }
                    }
                    break;
                    case "EXIT":
                    this.status = false;
                    input.close();
                    status = false;
                    
                }
            } else {
                System.out.println("Invalid input. Try again.");
            }
        } 
    }

    public void chooseATMOption(Scanner input) {
        boolean status = true;

        while(status){
            System.out.println("\nPlease select from the following options:");
            System.out.println("1. WITHDRAW");
            System.out.println("2. BALANCE-CHECK");    
            System.out.println("3. BALANCE-RECEIPT");
            System.out.println("4. DEPOSIT");
            System.out.println("5. EXIT"); 
            System.out.print("Please enter your required action: ");

            String action = null;
            if(input.hasNext()){
                action = input.next();
            }
            
            if (action.toUpperCase().equals("EXIT")) { 
                status = false;
                this.status = false;}
            
            else if (action.equals("")) {
                continue;
            }
                
            else if(action.toUpperCase().equals("BALANCE-CHECK") || action.toUpperCase().equals("BALANCE-RECEIPT")) {
                switch (action.toUpperCase()) {
                    case "BALANCE-CHECK": 
                    System.out.print("\nTotal Balance: $");
                    System.out.println(this.atm.cardTransactionMethod(this.currentCustomer, "BALANCE CHECK") + "\n");
                    boolean exit = this.backOrExit(input);
                    if (!exit) {status = false;}
                    break;
                    case "BALANCE-RECEIPT":
                    System.out.println(this.atm.cardTransactionMethod(this.currentCustomer, "BALANCE RECEIPT"));  
                    exit = this.backOrExit(input);
                    if (!exit) {status = false;}
                    break;                
                }
            }  

            else if(action.toUpperCase().equals("WITHDRAW") || action.toUpperCase().equals("DEPOSIT") ) {
                switch (action.toUpperCase()) {
                    case "WITHDRAW":
                    boolean status3 =  true;
                    while (status3) {
                        System.out.print("\nPlease enter amount to withdraw or EXIT to exit: ");
                        String strAmount = input.next();
                        if (strAmount.toUpperCase().equals("EXIT")) {
                            status3 = false;
                            status = false;
                            this.status = false;
                            input.close();
                        } else {
                            double amount = Double.parseDouble(strAmount);
                            String result = this.atm.cardTransactionMethod(this.currentCustomer, "WITHDRAWAL", amount);
                            if (result.equals("card insufficient")) {
                                System.out.println("\nSorry, your account has insufficient funds.");
                                System.out.println("Account Balance: $" + this.currentCustomer.getFunds());
                                boolean status2 = true;
                                while (status2) {
                                    System.out.println("Enter BACK to return to home page or WITHDRAW to retry.");
                                    System.out.print("Please enter your required action: ");
                                    String strAction = input.next().toUpperCase();
                                    if (strAction.equals("BACK")) { 
                                        status3 = false;
                                        break; 
                                    } else if (strAction.equals("WITHDRAW")) { 
                                        status2 = false;
                                    } else {
                                        System.out.println("\nInvalid input. Please try again.");
                                    }
                                }      

                            } else if (result.equals("atm insufficient")) {
                                System.out.println("Withdrawal is not available at this ATM. We are sorry for any inconvenience.");
                                this.status = false;
                                input.close();
                                status = false;
                                status3 = false;
                                break;
                            } else {
                                System.out.println(result);
                                this.atm.updateCardCsv(); 
                                boolean exit = this.backOrExit(input);
                                if (!exit) {
                                    status = false;
                                    status3 = false;
                                }
                                break;   
                            }
                        }
                    }
                    break;

                    case "DEPOSIT":
                    ArrayList<Integer> validEntries = new ArrayList<Integer>(Arrays.asList(100, 50, 20, 10, 5));
                    ArrayList<Double> notes = new ArrayList<Double>();
                    boolean status2 = true;
                    System.out.println("\nEnter DONE at anytime to finalise deposit OR enter EXIT at anytime to exit");
                    while (status2) {
                        System.out.println("Please enter a note to deposit:");
                        String strNote = input.next();

                        if (strNote.toUpperCase().equals("DONE")) {
                            double[] doubleNotes = new double[notes.size()];
                            for (int i = 0; i < notes.size(); i++) {
                                doubleNotes[i] = notes.get(i);
                            }
                            System.out.println("\nFinalised deposit successfully!");
                            System.out.println(this.atm.cardTransactionMethod(currentCustomer, "DEPOSIT", doubleNotes));
                            this.atm.updateCardCsv();
                            status2 = false;
                            boolean exit = this.backOrExit(input);
                            if (!exit) {status = false;}
                            break;
                        }

                        else if (strNote.toUpperCase().equals("EXIT")) { 
                            this.status = false;
                            input.close();
                            status = false;
                            status2 = false;
                        }
                        else if (validEntries.contains(Integer.parseInt(strNote))) {
                            notes.add((double) Integer.parseInt(strNote));
                        }
                        else {
                            System.out.println("Invalid denomination.");
                        }
                    }
                }
            } 

            else { 
                System.out.println("\nInvalid input. Please try again!\n");
            }
        }
    }

    public boolean backOrExit(Scanner input) {
        boolean stat = true;
        System.out.println("Enter BACK to go back to Home page or EXIT to exit.");
        System.out.print("Please enter your required action: ");
        while (stat) {
            String strAction = input.next();
            if (strAction.toUpperCase().equals("BACK")) { 
                break;
            } else if (strAction.toUpperCase().equals("EXIT")) { 
                this.status = false;
                input.close();
                stat = false;
                return false;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
        return true; 
    }

    // ########### MAIN FUNCTION FOR APP ####################//
    
    public void ATMActivate(Scanner input) {
        
        // choosing login 
        while(this.status){

            System.out.println("Please select from the following options:");
            System.out.println("1. LOGIN");
            System.out.println("2. ADMIN");    
            System.out.println("3. EXIT");  
            System.out.print("Please enter your required action: "); 

            

                String action = input.next();

                if (action.toUpperCase().equals("EXIT")) { this.status = false;}
                
                else if(action.toUpperCase().equals("LOGIN")) {
                    if (this.takeCustomerDetails(input)) { //Correct Customer Details
                        this.chooseATMOption(input);
                        input.close();
                        break;
                    }
                }  

                else if(action.toUpperCase().equals("ADMIN")) {
                    if (this.takeAdminDetails(input)) { //Correct Admin Details
                        this.adminDeposit(input);
                    }
                } 

                else { 
                    System.out.println("Invalid input Try again!");
                }
        }     
        
        System.out.println("\n#############################################################");
        System.out.println("## THANK YOU FOR USING NAAAR ATM FOR YOUR FINANCIAL NEEDS ###");
        System.out.println("#############################################################\n");
        
    }

    
    public String getGreeting() {

        return "Hello World!";
        
    }
    
    public static void main(String[] args) {

        String welcome = "##################################################\n" +
                        "############# WELCOME TO NAAAR ATM \u00a9 #############\n" +
                        "##################################################\n";

        System.out.println(welcome);
        Scanner input = new Scanner(System.in);
        App atmApp = new App();
        atmApp.ATMActivate(input);



    }
}