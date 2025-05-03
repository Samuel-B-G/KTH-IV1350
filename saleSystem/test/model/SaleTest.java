package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import integration.InventorySystem;
import integration.ItemInfoDTO;

public class SaleTest {
	Sale sale;
	InventorySystem inventorySystem;
	
	@BeforeEach
	public void setUp() {
		this.sale = new Sale();
		this.inventorySystem = new InventorySystem();
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier","name","description",0.1,10);
		inventorySystem.addItem(itemInfoDTO, 1);
	}
	
	@AfterEach
	public void tearDown() {
		this.sale = null;
		this.inventorySystem = null;
	}
	
	@Test
	public void testAddItemValidIdentifier() {
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier",1);
		
		ItemAndRunningTotalDTO item = sale.addItem(inventorySystem, requestedItemDTO);
		String result = item.getItemIdentifier();
		assertEquals("identifier", result, "Incorrect item was found");
	}
	
	@Test
	public void testAddItemInvalidIdentifier() {
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("doesNotExist",1);
		
		ItemAndRunningTotalDTO result = sale.addItem(inventorySystem, requestedItemDTO);
		assertEquals(null, result, "Item was found despite invalid identifier");
	}
	
	@Test
	public void testPayCorrectAmount() {
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier",1);
		
		sale.addItem(inventorySystem, requestedItemDTO);
		
		double result = sale.pay(11);
		assertEquals(0, result, "Change is incorrect");
	}
	
	@Test
	public void testPayTooMuch() {
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier",1);
		
		sale.addItem(inventorySystem, requestedItemDTO);
		
		double result = sale.pay(12);
		assertEquals(1, result, "Change is incorrect");
	}
	
	@Test
	public void testPayTooLittle() {
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier",1);
		
		sale.addItem(inventorySystem, requestedItemDTO);
		
		double result = sale.pay(10);
		assertEquals(-1, result, "Change is incorrect");
	}
	
	@Test
	public void testUpdateInventory() {
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier",1);
		sale.addItem(inventorySystem, requestedItemDTO);
		sale.updateInventory(inventorySystem);
		int result = inventorySystem.getQuantity("identifier");
		assertEquals(0, result, "Updated item quantity is incorrect");
	}
	
	/*
	 * Skipped for now as alternative flow 9a is not included in Seminar 3
	@Test
	public void checkDiscount() {
		
	}
	*/
}
