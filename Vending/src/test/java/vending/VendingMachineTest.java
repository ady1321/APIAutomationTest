package vending;
import org.junit.Ignore;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runners.MethodSorters;

import junit.framework.Assert;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VendingMachineTest {
    private static VendingMachine vm;
   
    @BeforeClass
    public static void setUp(){
        vm = VendingMachineFactory.createVendingMachine();
    }
   
    @AfterClass
    public static void tearDown(){
        vm = null;
    }
   
    @Test
    @Order(1)
    public void AtestBuyItemWithExactPrice() {
    	 System.out.println("Test for item with exact price");
        //select item, price in cents
        long price = vm.selectItemAndGetPrice(Item.COKE); 
        //price should be Coke's price      
        assertEquals(Item.COKE.getPrice(), price);
        //25 cents paid              
        vm.insertCoin(Coin.QUARTER);                                   
      
        
        Bucket<Item, List<Coin>> bucket = vm.collectItemAndChange();
        Item item = bucket.getFirst();
        List<Coin> change = bucket.getSecond();
       
        //should be Coke
        assertEquals(Item.COKE, item);
        //there should not be any change   
        assertTrue(change.isEmpty());        
       System.out.println("Test for item with exact price passed\n");
        
    }
   
    @Test
    @Order(2)
    public void BtestBuyItemWithMorePrice(){
    	 System.out.println("Test for item with more price");
        long price = vm.selectItemAndGetPrice(Item.SODA);
        assertEquals(Item.SODA.getPrice(), price);
       
        vm.insertCoin(Coin.QUARTER);       
        vm.insertCoin(Coin.QUARTER);      
       
        Bucket<Item, List<Coin>> bucket = vm.collectItemAndChange();
        Item item = bucket.getFirst();
        List<Coin> change = bucket.getSecond();
        //should be Coke
        assertEquals(Item.SODA, item);
        //there should not be any change     
        assertTrue(!change.isEmpty());        
        //comparing change                             
        assertEquals(50 - Item.SODA.getPrice(), getTotal(change));  
        System.out.println("Test for item with more price passed\n");
    }  
  
   
    @Test
    @Order(3)
    public void CtestRefund(){
    	System.out.println("Test for refund");
        long price = vm.selectItemAndGetPrice(Item.PEPSI);
        assertEquals(Item.PEPSI.getPrice(), price);       
        vm.insertCoin(Coin.DIME);
        vm.insertCoin(Coin.NICKLE);
        vm.insertCoin(Coin.PENNY);
        vm.insertCoin(Coin.QUARTER);
        List<Coin> refund = vm.refund();
        long refundamount = getTotal(refund);
        assertEquals(41, refundamount);    
        System.out.println("Test for refund passed with refund amount " + refundamount + "\n");
    }
   
    @Test(expected=SoldOutException.class)
    @Order(4)
    public void DtestSoldOut(){
    	System.out.println("Test for soldout");
        for (int i = 0; i < 5; i++) {
            vm.selectItemAndGetPrice(Item.COKE);
            vm.insertCoin(Coin.QUARTER);
            vm.collectItemAndChange();
        }
      System.out.println("Test for sold out exception passed\n");
    }
   
    @Test(expected=NotSufficientChangeException.class)
    @Order(5)
    public void EtestNotSufficientChangeException(){
    	System.out.println("Test for not sufficient change");
        for (int i = 0; i < 5; i++) {
            vm.selectItemAndGetPrice(Item.SODA);
            vm.insertCoin(Coin.QUARTER);
            vm.insertCoin(Coin.QUARTER);
            vm.collectItemAndChange();
           
            vm.selectItemAndGetPrice(Item.PEPSI);
            vm.insertCoin(Coin.QUARTER);
            vm.insertCoin(Coin.QUARTER);
            vm.collectItemAndChange();
        }
      System.out.println("Test for not sufficient change exception passed\n");
    }
   
   
    @Test
    @Order(6)
    public void FtestReset(){
    	System.out.println("Test for reset");
    	vm.reset();
        vm = VendingMachineFactory.createVendingMachine();
        vm.selectItemAndGetPrice(Item.COKE);
        System.out.println("Test for reset passed\n");
    }
   
    @Ignore
    public void testVendingMachineImpl(){
        VendingMachineImpl vm = new VendingMachineImpl();
    }
   
    private long getTotal(List<Coin> change){
        long total = 0;
        for(Coin c : change){
            total = total + c.getDenomination();
        }
        return total;
    }
}