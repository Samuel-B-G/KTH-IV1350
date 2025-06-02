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
		this.inventorySystem = InventorySystem.getInventorySystem();
	}

	@AfterEach
	public void tearDown() {
		inventorySystem.emptyInventory();
		this.inventorySystem = null;
	}

	@Test
	public void testAddItemValidIdentifier() throws ItemDoesNotExistException {
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0, 0);
		int quantity = 1;
		inventorySystem.addItem(itemInfoDTO, quantity);

		ItemInfoDTO result = inventorySystem.getItemInfo("identifier");

		assertEquals(itemInfoDTO, result, "Incorrect item or no item was found");
	}

	@Test
	public void testAddItemInvalidIdentifierException() {
		String itemIdentifier = "doesNotExist";

		try {
			inventorySystem.getItemInfo(itemIdentifier);
			fail("Non-existing item was returned.");
		} catch (ItemDoesNotExistException e) {
			String expectedMsg = "No item with the item identifier '"+itemIdentifier+"' exists in the inventory system.";
			assertEquals(e.getMessage(), expectedMsg, "Wrong exception message.");
		}
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
	public void updateInventory() throws ItemDoesNotExistException {
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
