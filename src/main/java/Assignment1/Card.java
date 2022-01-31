package Assignment1;
import java.util.Date;

public class Card{


  private String cardNumber;
  private String name;
  private Date startDate;
  private Date expiryDate;
  private int pin;
  public boolean status;
  public double funds;
  public String valid;

  public Card(String name, String cardNumber,Date startDate,Date expiryDate,int pin, double funds, String valid){
    this.name = name;
    this.cardNumber = cardNumber;
    this.startDate  = startDate;
    this.expiryDate = expiryDate;
    this.pin = pin;
    this.funds = funds;
    this.status = true;
    this.valid = valid;
  }

  public String getName(){
    return name;
  }

  public String getCardNumber(){
    return cardNumber;
  }

  public Date getstartDate(){
    return startDate;
  }

  public Date getexpiryDate(){
    return expiryDate;
  }

  public int getPin(){
    return pin;
  }

  public boolean status(){
    return this.status;
  }

  public void changeStatus(){
    this.status = !status;
  }

  public double getFunds(){
    return this.funds;
  }
  public String getValid(){
    return this.valid;
  }
  public void setValid(String newValid){
    this.valid = newValid;
  }

  






}
