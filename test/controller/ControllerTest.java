package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import integration.DatabaseCreator;
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
	public void testAddItemNoQuantitySpecified() {
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0.1, 10);
		contr.inventorySystem.addItem(itemInfoDTO, 0);

		contr.newSale();
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier");
		ItemAndRunningTotalDTO item = contr.addItem(requestedItemDTO);
		int result = item.getQuantity();
		assertEquals(1, result, "Quantity did not default to 1 as expected");
	}

	@Test
	public void testAddItemValidIdentifier() {
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0.1, 10);
		contr.inventorySystem.addItem(itemInfoDTO, 0);

		contr.newSale();
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier", 1);
		ItemAndRunningTotalDTO item = contr.addItem(requestedItemDTO);
		String result = item.getItemIdentifier();
		assertEquals("identifier", result, "Incorrect item was returned");
	}

	@Test
	public void testAddItemInvalidIdentifier() {
		contr.newSale();
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier", 1);
		ItemAndRunningTotalDTO item = contr.addItem(requestedItemDTO);
		assertEquals(null, item, "Item was returned despite invalid identifier");
	}

	@Test
	public void testEndSaleNoItems() {
		contr.newSale();
		double result = contr.endSale();
		assertEquals(0, result, "Incorrect final amount was returned");
	}

	@Test
	public void testEndSale() {
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
	public void testEndSaleNull() {
		contr.sale = null;
		double result = contr.endSale();
		assertEquals(null, result, "Null was not returned as expected");
	}

	@Test
	public void testPayCorrectAmount() {
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
	public void testPayTooMuch() {
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
	public void testPayTooLittle() {
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0.1, 10);
		contr.inventorySystem.addItem(itemInfoDTO, 0);

		contr.newSale();

		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier", 1);
		contr.addItem(requestedItemDTO);

		contr.endSale();

		double result = contr.pay(10);
		assertEquals(-1, result, "Change is incorrect");
	}

	/*
	 * Skipped for now as alternative flow 9a is not included in Seminar 3
	 * 
	 * @Test public void checkDiscount() {
	 * 
	 * }
	 */

}
