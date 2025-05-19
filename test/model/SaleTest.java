package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import integration.DiscountDatabase;
import integration.InventorySystem;
import integration.ItemDoesNotExistException;
import integration.ItemInfoDTO;
import integration.NoDatabaseConnectionException;

class SaleTest {
	Sale sale;
	InventorySystem inventorySystem;

	@BeforeEach
	public void setUp() {
		this.sale = new Sale();
		this.inventorySystem = InventorySystem.getInventorySystem();
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0.1, 10);
		inventorySystem.addItem(itemInfoDTO, 1);
	}

	@AfterEach
	public void tearDown() {
		this.sale = null;
		inventorySystem.emptyInventory();
		this.inventorySystem = null;
	}

	@Test
	public void testAddItemValidIdentifier() throws ItemDoesNotExistException {
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier", 1);

		ItemAndRunningTotalDTO item = sale.addItem(inventorySystem, requestedItemDTO);
		String result = item.getItemIdentifier();
		assertEquals("identifier", result, "Incorrect item was found");
	}

	@Test
	public void testAddItemInvalidIdentifierException() {
		String itemIdentifier = "doesNotExist";
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO(itemIdentifier, 1);

		try {
			sale.addItem(inventorySystem, requestedItemDTO);
			fail("Non-existing item was returned.");
		} catch (ItemDoesNotExistException e) {
			String expectedMsg = "No item with the item identifier '"+itemIdentifier+"' exists in the inventory system.";
			assertEquals(e.getMessage(), expectedMsg, "Wrong exception message.");
		}
	}

	@Test
	public void testPayCorrectAmount() throws ItemDoesNotExistException {
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier", 1);

		sale.addItem(inventorySystem, requestedItemDTO);

		double result = sale.pay(11);
		assertEquals(0, result, "Change is incorrect");
	}

	@Test
	public void testPayTooMuch() throws ItemDoesNotExistException {
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier", 1);

		sale.addItem(inventorySystem, requestedItemDTO);

		double result = sale.pay(12);
		assertEquals(1, result, "Change is incorrect");
	}

	@Test
	public void testPayTooLittle() throws ItemDoesNotExistException {
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier", 1);

		sale.addItem(inventorySystem, requestedItemDTO);

		double result = sale.pay(10);
		assertEquals(-1, result, "Change is incorrect");
	}

	@Test
	public void testUpdateInventory() throws ItemDoesNotExistException {
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier", 1);
		sale.addItem(inventorySystem, requestedItemDTO);
		sale.updateInventory(inventorySystem);
		int result = inventorySystem.getQuantity("identifier");
		assertEquals(0, result, "Updated item quantity is incorrect");
	}
	
	@Test
	public void testCheckDiscountNoDatabaseConnectionException() {
		DiscountDatabase discountDatabase = new DiscountDatabase();
		try {
			sale.checkDiscount(discountDatabase, "error");
			fail("Value was returned despite discount database not existing.");
		} catch (NoDatabaseConnectionException e) {
			String expectedMsg = "Unable to perform operation as database could not be reached.";
			assertEquals(e.getMessage(), expectedMsg, "Wrong exception message.");
		}
	}
	
	@Test
	public void testGetDiscountValidCustomerID() throws ItemDoesNotExistException {
		String customerID = "money";
		DiscountDatabase discountDatabase = new DiscountDatabase();
		
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier", 10);
		sale.addItem(inventorySystem, requestedItemDTO);
		
		try {
			sale.checkDiscount(discountDatabase, customerID);
			double result = sale.getDiscountedAmount();
			double expectedResult = 11;
			assertEquals(expectedResult, result, result+" was returned instead of the expected "+expectedResult);
		} catch (NoDatabaseConnectionException e) {
			fail("Database could not be reached despite existing.");
		}
	}
	
	@Test
	public void testGetDiscountTotalAmount() throws ItemDoesNotExistException {
		String customerID = "doesNotExist";
		DiscountDatabase discountDatabase = new DiscountDatabase();
		
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier", 100);
		sale.addItem(inventorySystem, requestedItemDTO);
		
		try {
			sale.checkDiscount(discountDatabase, customerID);
			double result = sale.getDiscountedAmount();
			double expectedResult = 165;
			assertEquals(expectedResult, result, result+" was returned instead of the expected "+expectedResult);
		} catch (NoDatabaseConnectionException e) {
			fail("Database could not be reached despite existing.");
		}
	}
}
