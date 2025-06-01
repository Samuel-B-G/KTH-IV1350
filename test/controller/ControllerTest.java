package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import integration.DatabaseCreator;
import integration.ItemDoesNotExistException;
import integration.ItemInfoDTO;
import model.ItemAndRunningTotalDTO;
import model.RequestedItemDTO;

class ControllerTest {
	DatabaseCreator creator;
	Controller contr;

	@BeforeEach
	public void setUp() {
		this.creator = new DatabaseCreator();
		this.contr = new Controller(creator);
	}

	@AfterEach
	public void tearDown() {
		contr.inventorySystem.emptyInventory();
		this.creator = null;
		this.contr = null;
	}

	@Test
	public void testNewSale() {
		contr.newSale();
		double result = contr.sale.getTotalAmount();
		assertEquals(0, result, "Sale did not get initialized correctly");
	}

	@Test
	public void testAddItemNoQuantitySpecified() throws OperationFailedException, NoActiveSaleException {
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0.1, 10);
		contr.inventorySystem.addItem(itemInfoDTO, 0);

		contr.newSale();
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier");
		ItemAndRunningTotalDTO item = contr.addItem(requestedItemDTO);
		int result = item.getQuantity();
		assertEquals(1, result, "Quantity did not default to 1 as expected");
	}

	@Test
	public void testAddItemValidIdentifier() throws OperationFailedException, NoActiveSaleException {
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0.1, 10);
		contr.inventorySystem.addItem(itemInfoDTO, 0);

		contr.newSale();
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier", 1);
		ItemAndRunningTotalDTO item = contr.addItem(requestedItemDTO);
		String result = item.getItemIdentifier();
		assertEquals("identifier", result, "Incorrect item was returned");
	}

	@Test
	public void testEndSaleNoItems() throws OperationFailedException, NoActiveSaleException {
		contr.newSale();
		double result = contr.endSale();
		assertEquals(0, result, "Incorrect final amount was returned");
	}

	@Test
	public void testEndSale() throws OperationFailedException, NoActiveSaleException {
		contr.newSale();

		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0.1, 10);
		contr.inventorySystem.addItem(itemInfoDTO, 0);

		contr.newSale();
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier", 1);
		contr.addItem(requestedItemDTO);

		double result = contr.endSale();
		assertEquals(11, result, "Incorrect final amount was returned");
	}

	@Test
	public void testPayCorrectAmount() throws OperationFailedException, NoActiveSaleException {
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0.1, 10);
		contr.inventorySystem.addItem(itemInfoDTO, 0);

		contr.newSale();

		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier", 1);
		contr.addItem(requestedItemDTO);

		contr.endSale();

		double result = contr.pay(11);
		assertEquals(0, result, "Change is incorrect");
	}

	@Test
	public void testPayTooMuch() throws OperationFailedException, NoActiveSaleException {
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0.1, 10);
		contr.inventorySystem.addItem(itemInfoDTO, 0);

		contr.newSale();

		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier", 1);
		contr.addItem(requestedItemDTO);

		contr.endSale();

		double result = contr.pay(12);
		assertEquals(1, result, "Change is incorrect");
	}

	@Test
	public void testPayTooLittle() throws OperationFailedException, NoActiveSaleException {
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0.1, 10);
		contr.inventorySystem.addItem(itemInfoDTO, 0);

		contr.newSale();

		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier", 1);
		contr.addItem(requestedItemDTO);

		contr.endSale();

		double result = contr.pay(10);
		assertEquals(-1, result, "Change is incorrect");
	}
	
	@Test
	public void testCheckDiscountNoDatabaseConnectionException() throws NoActiveSaleException {
		contr.newSale();
		String customerID = "error";
		try {
			contr.checkDiscount(customerID);
			fail("Discount was found despite discount database not existing.");
		} catch (OperationFailedException e) {
			assertEquals(e.getMessage(), "Could not contact the database.", "Wrong exception message.");
		}
	}
	
	@Test
	public void testGetDiscountValidCustomerID() throws ItemDoesNotExistException, OperationFailedException, NoActiveSaleException {
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0, 10);
		contr.inventorySystem.addItem(itemInfoDTO, 0);
		
		contr.newSale();
		
		String customerID = "money";
		
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier", 10);
		contr.addItem(requestedItemDTO);
		
		contr.checkDiscount(customerID);
		double result = contr.sale.getDiscountedAmount();
		double expectedResult = 10;
		assertEquals(expectedResult, result, result+" was returned instead of the expected "+expectedResult);
	}
	
	@Test
	public void testGetDiscountTotalAmount() throws ItemDoesNotExistException, OperationFailedException, NoActiveSaleException {
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0.1, 10);
		contr.inventorySystem.addItem(itemInfoDTO, 0);
		
		contr.newSale();
		
		String customerID = "doesNotExist";
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier", 50);
		contr.addItem(requestedItemDTO);
		contr.checkDiscount(customerID);
		double result = contr.sale.getDiscountedAmount();
		double expectedResult = 55;
		assertEquals(expectedResult, result, result+" was returned instead of the expected "+expectedResult);
	}
	
	@Test
	public void testEndSaleNoExistingSaleException() throws OperationFailedException {
		contr.sale = null;
		try {
			contr.endSale();
			fail("Sale was ended despite no sale existing.");
		} catch (NoActiveSaleException e) {
			assertEquals(e.getMessage(), "Operation failed as no sale was active.", "Wrong exception message.");
		}
	}

	@Test
	public void testAddItemInvalidIdentifierException() throws NoActiveSaleException {
		contr.newSale();
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("doesNotExist");
		try {
			contr.addItem(requestedItemDTO).getPrice();
			fail("Managed to add a nonexistent item");
		} catch (OperationFailedException e) {
			System.out.println(e.getMessage());
			assertEquals(e.getMessage(), "Item was not found.", "Wrong exception message.");
		}
	}

}
