
package Assignment1;

import Assignment1.Card;
import java.text.SimpleDateFormat;  
import java.util.Date; 
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
  
  @Test
  public void testConstructor(){
    String name = "John";
    String cardNumber = "12345";
    try{
      Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
      Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
      int pin = 1234;
      double funds = 1000.50;
      Card c = new Card(name,cardNumber,startDate,expiryDate,pin,funds, null);
      assertNotNull(c);
    }catch(Exception e){
      System.out.println("error");
    }

    
  }

  @Test
  public void testGetName(){
    String name = "John";
    String cardNumber = "12345";
    try{
      Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
      Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
      int pin = 1234;
      double funds = 1000.50;
      Card c = new Card(name,cardNumber,startDate,expiryDate,pin,funds, null);
      assertEquals("John", c.getName());
    }catch(Exception e){
      System.out.println("error");
    }

  }

  @Test
  public void testGetCardNumber(){
    String name = "John";
    String cardNumber = "12345";;
    try{
      Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
      Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
      int pin = 1234;
      double funds = 1000.50;
      Card c = new Card(name,cardNumber,startDate,expiryDate,pin,funds, null);
      assertEquals("12345", c.getCardNumber());
    }catch(Exception e){
      System.out.println("error");
    }

  }

  @Test
  public void testGetStartDate(){
    String name = "John";
    String cardNumber = "12345";;
    try{
      Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
      Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
      int pin = 1234;
      double funds = 1000.50;
      Card c = new Card(name,cardNumber,startDate,expiryDate,pin,funds, null);
      String expected = "Fri Jan 10 00:00:00 AEDT 2020";
      String actual = c.getstartDate().toString();
      assertEquals(expected,actual);
    }catch(Exception e){
      System.out.println("error");
    }

  }

  @Test
  public void testGetExpiryDate(){
    String name = "John";
    String cardNumber = "12345";;
    try{
      Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
      Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
      int pin = 1234;
      double funds = 1000.50;
      Card c = new Card(name,cardNumber,startDate,expiryDate,pin,funds,null);
      String expected = "Wed Jan 10 00:00:00 AEDT 2024";
      String actual = c.getexpiryDate().toString();
      assertEquals(expected,actual);
    }catch(Exception e){
      System.out.println("error");
    }

  }

  @Test
  public void testGetPin(){
    String name = "John";
    String cardNumber = "12345";;
    try{
      Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
      Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
      int pin = 1234;
      double funds = 1000.50;
      Card c = new Card(name,cardNumber,startDate,expiryDate,pin,funds,null);
      assertEquals(1234, c.getPin());
    }catch(Exception e){
      System.out.println("error");
    }

  }

  @Test
  public void testStatus(){
    String name = "John";
    String cardNumber = "12345";;
    try{
      Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
      Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
      int pin = 1234;
      double funds = 1000.50;
      Card c = new Card(name,cardNumber,startDate,expiryDate,pin,funds,null);
      assertTrue(c.status());
    }catch(Exception e){
      System.out.println("error");
    }

    
  }

  @Test
  public void testChangeStatus(){
    String name = "John";
    String cardNumber = "12345";;
    try{
      Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
      Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
      int pin = 1234;
      double funds = 1000.50;
      Card c = new Card(name,cardNumber,startDate,expiryDate,pin,funds,null);
      assertTrue(c.status());
      c.changeStatus();
      assertFalse(c.status());
    }catch(Exception e){
      System.out.println("error");
    }

  }

  @Test
  public void testGetFunds(){
    String name = "John";
    String cardNumber = "12345";;
    try{
      Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
      Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
      int pin = 1234;
      double funds = 1000.50;
      Card c = new Card(name,cardNumber,startDate,expiryDate,pin,funds,null);
      assertEquals(1000.50, c.getFunds(),0.0001);
    }catch(Exception e){
      System.out.println("error");
    }

  }
  @Test
  public void testValid(){
    String name = "John";
    String cardNumber = "12345";;
    try{
      Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2020");
      Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2024");
      int pin = 1234;
      double funds = 1000.50;
      Card c = new Card(name,cardNumber,startDate,expiryDate,pin,funds,null);
      assertEquals(null, c.getValid());
      String value = "stolen";
      Card b = new Card(name,cardNumber,startDate,expiryDate,pin,funds,value);
      assertEquals("stolen", b.getValid());
      Card a = new Card(name,cardNumber,startDate,expiryDate,pin,funds,"lost");
      assertEquals("lost", a.getValid());
      a.setValid("stolen");
      assertEquals("stolen", a.getValid());
    }catch(Exception e){
      System.out.println("error");
    }

  }

}
