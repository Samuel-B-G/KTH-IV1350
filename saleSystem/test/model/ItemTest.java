package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import integration.ItemInfoDTO;

public class ItemTest {
	
	@Test
	public void testIncreaseQuantityToPositive() {
		ItemInfoDTO itemDTO = new ItemInfoDTO("","","",0,0);
		Item item = new Item(itemDTO);
		item.increaseQuantity(1);
		double result = item.getQuantity();
		assertEquals(1, result, "Incorrect quantity");
	}
	
	@Test
	public void testIncreaseQuantityToNegative() {
		ItemInfoDTO itemDTO = new ItemInfoDTO("","","",0,0);
		Item item = new Item(itemDTO);
		item.increaseQuantity(-1);
		double result = item.getQuantity();
		assertEquals(-1, result, "Incorrect quantity");
	}
	
	@Test
	public void testCalculateIndividualVATPrice() {
		double price = 10;
		double vatRate = 0.1;
		ItemInfoDTO itemDTO = new ItemInfoDTO("","","",vatRate,price);
		Item item = new Item(itemDTO);
		double result = item.calculateIndividualVATPrice();
		assertEquals(1, result, "Incorrect VAT price");
	}
	
	@Test
	public void testCalculateIndividualPriceAfterVAT() {
		double price = 10;
		double vatRate = 0.1;
		ItemInfoDTO itemDTO = new ItemInfoDTO("","","",vatRate,price);
		Item item = new Item(itemDTO);
		double result = item.calculateIndividualPriceAfterVAT();
		assertEquals(11, result, "Incorrect price after VAT");
	}
}
