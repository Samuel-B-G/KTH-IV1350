package integration;

import java.util.ArrayList;
import java.util.List;

import model.Item;
import model.ItemDTO;

/**
 * This class represents the inventory system.
 */

public class InventorySystem {
	private List<Item> storedItems;

	/**
	 * Creates a new instance, representing the details of the inventory system.
	 */

	public InventorySystem() {
		this.storedItems = new ArrayList<Item>();
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

		itemInfo = new ItemInfoDTO("def456", "Flour", "500 g, Rye, fine", 0.12, 35);
		addItem(itemInfo, 8);

		itemInfo = new ItemInfoDTO("ghi789", "Eggs", "8 included, free range", 0.06, 35);
		addItem(itemInfo, 8);
	}

	/**
	 * Searches the objects storedItems list for a specific item and returns its
	 * {@link ItemInfoDTO}.
	 * 
	 * @param itemIdentifier The item identifier to look for in the list.
	 * 
	 * @return The {@link ItemInfoDTO} corresponding to the itemIdentifier.
	 */

	public ItemInfoDTO getItemInfo(String itemIdentifier) {
		Item item = findItem(itemIdentifier);
		if (item == null)
			return null;
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

	private void decreaseItemQuantity(ItemDTO itemDTO) {
		String itemIdentifier = itemDTO.getItemIdentifier();
		int amountToDecrease = itemDTO.getQuantity();

		Item item = findItem(itemIdentifier);
		item.decreaseQuantity(amountToDecrease);
	}
}
