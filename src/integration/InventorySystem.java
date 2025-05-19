package integration;

import java.util.ArrayList;
import java.util.List;

import model.Item;
import model.ItemDTO;

/**
 * This class represents the inventory system, it used the Singleton method to ensure that there is always only a single instance of this class.
 */

public class InventorySystem {
	private static InventorySystem inventorySystem = new InventorySystem();
	private List<Item> storedItems;

	/**
	 * Creates a new instance, representing the details of the inventory system.
	 */

	private InventorySystem() {
		this.storedItems = new ArrayList<Item>();
	}
	
	/**
	 * Returns the {@link InventorySystem} object itself.
	 * 
	 * @return The {@link InventorySystem} object.
	 */
	
	public static InventorySystem getInventorySystem() {
		return inventorySystem;
	}

	/**
	 * Adds an item to the storedItems list in the object. This is a temporary
	 * function.
	 * 
	 * @param itemInfo       The DTO containing all the details of the item.
	 * @param storedQuantity The initial quantity of the item.
	 */

	public void addItem(ItemInfoDTO itemInfo, int storedQuantity) {
		storedItems.add(new Item(itemInfo, storedQuantity));
	}

	/**
	 * Fills the objects storedItems list with several predefined items. This is a
	 * temporary function.
	 */

	public void fillDatabase() {
		ItemInfoDTO itemInfo;

		itemInfo = new ItemInfoDTO("abc123", "Oatmeal", "400g, Organic", 0.25, 55);
		addItem(itemInfo, 10);

		itemInfo = new ItemInfoDTO("def456", "Flour", "500g, Rye, fine", 0.12, 35);
		addItem(itemInfo, 8);

		itemInfo = new ItemInfoDTO("ghi789", "Eggs", "8 included, free range", 0.06, 35);
		addItem(itemInfo, 8);
		
		itemInfo = new ItemInfoDTO("aaa111", "Mittens", "Warm, cotton", 0.12, 100);
		addItem(itemInfo, 6);
		
		itemInfo = new ItemInfoDTO("aab112", "Salt", "Fine, iodized", 0.25, 20);
		addItem(itemInfo, 12);
		
		itemInfo = new ItemInfoDTO("aac113", "Yeast", "50g, dry", 0.06, 50);
		addItem(itemInfo, 8);
	}

	/**
	 * Searches the objects storedItems list for a specific item and returns its
	 * {@link ItemInfoDTO}.
	 * 
	 * @param itemIdentifier The item identifier to look for in the list.
	 * 
	 * @return The {@link ItemInfoDTO} corresponding to the itemIdentifier.
	 * 
	 * @throws ItemDoesNotExistException if the searched for item does not exist in the inventory system.
	 */

	public ItemInfoDTO getItemInfo(String itemIdentifier) throws ItemDoesNotExistException {
		Item item = findItem(itemIdentifier);
		if (item == null)
			throw new ItemDoesNotExistException(itemIdentifier);
		return item.getItemInfo();
	}

	/**
	 * Searches the objects storedItems list for a specific item and returns its
	 * quantity.
	 * 
	 * @param itemIdentifier The item identifier to look for in the list.
	 * 
	 * @return The quantity of the specified item.
	 */

	public int getQuantity(String itemIdentifier) {
		Item item = findItem(itemIdentifier);
		if (item == null)
			return 0;
		return item.getQuantity();
	}

	private Item findItem(String itemIdentifier) {
		for (int i = 0; i < storedItems.size(); i++) {
			if (isCorrectItem(storedItems.get(i), itemIdentifier))
				return storedItems.get(i);
		}
		return null;
	}

	private Boolean isCorrectItem(Item item, String itemIdentifier) {
		if (itemIdentifier.equals(item.getItemIdentifier()))
			return true;
		return false;
	}

	/**
	 * Accepts an array of ItemDTOs which are used to decrease the stored quantities
	 * of the sold items.
	 * 
	 * @param itemDTOArray An array containing ItemDTOs containing item information.
	 */

	public void updateInventory(ItemDTO[] itemDTOArray) {
		for (int i = 0; i < itemDTOArray.length; i++) {
			decreaseItemQuantity(itemDTOArray[i]);
		}
	}
	
	/**
	 * Empties the inventory, used in tests as {@link InventorySystem} is a singleton, meaning the same instance of the class is used in each test.
	 */
	
	public void emptyInventory() {
		this.storedItems = new ArrayList<Item>();
	}

	private void decreaseItemQuantity(ItemDTO itemDTO) {
		String itemIdentifier = itemDTO.getItemIdentifier();
		int amountToDecrease = itemDTO.getQuantity();

		Item item = findItem(itemIdentifier);
		item.decreaseQuantity(amountToDecrease);
	}
}
