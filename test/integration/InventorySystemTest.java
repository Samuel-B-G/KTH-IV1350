package integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.RequestedItemDTO;
import model.Sale;

class InventorySystemTest {
	InventorySystem inventorySystem;

	@BeforeEach
	public void setUp() {
		this.inventorySystem = new InventorySystem();
	}

	@AfterEach
	public void tearDown() {
		this.inventorySystem = null;
	}

	@Test
	public void testAddItemValidIdentifier() {
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0, 0);
		int quantity = 1;
		inventorySystem.addItem(itemInfoDTO, quantity);

		ItemInfoDTO result = inventorySystem.getItemInfo("identifier");

		assertEquals(itemInfoDTO, result, "Incorrect item or no item was found");
	}

	@Test
	public void testGetItemInfoInvalidIdentifier() {
		ItemInfoDTO result = inventorySystem.getItemInfo("doesNotExist");

		assertEquals(null, result, "Did not return null as expected");
	}

	@Test
	public void testGetQuantity() {
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0, 0);
		int quantity = 1;
		inventorySystem.addItem(itemInfoDTO, quantity);

		int result = inventorySystem.getQuantity("identifier");

		assertEquals(1, result, "Incorrect quantity returned, did it get the wrong item?");
	}

	@Test
	public void updateInventory() {
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0, 0);
		int quantity = 1;
		inventorySystem.addItem(itemInfoDTO, quantity);

		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier", 1);
		Sale sale = new Sale();
		sale.addItem(inventorySystem, requestedItemDTO);
		sale.updateInventory(inventorySystem);

		int result = inventorySystem.getQuantity("identifier");
		assertEquals(0, result, "Incorrect quantity after update");
	}
}
