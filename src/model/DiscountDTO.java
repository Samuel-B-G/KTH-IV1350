package model;

public class DiscountDTO {
	private final String customerID;
	private final double totalAmount;
	private final ItemDTO[] itemDTOList;
	
	public DiscountDTO(String customerID, double totalAmount, ItemDTO[] itemDTOList){
		this.customerID = customerID;
		this.totalAmount = totalAmount;
		this.itemDTOList = itemDTOList;
	}

	public String getCustomerID() {
		return customerID;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public ItemDTO[] getItemDTOList() {
		return itemDTOList;
	}
}
