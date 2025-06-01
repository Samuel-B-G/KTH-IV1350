package integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.DiscountDTO;
import model.ItemDTO;

class DiscountDatabaseTest {
	DiscountDatabase discountDatabase;
	
	@BeforeEach
	public void setUp() {
		this.discountDatabase = new DiscountDatabase();
	}

	@AfterEach
	public void tearDown() {
		this.discountDatabase = null;
	}
	
	@Test
	public void testGetDiscountNoDatabaseConnectionException() {
		String customerID = "error";
		DiscountDTO discountDTO = new DiscountDTO(customerID, 0, new ItemDTO[0]);
		try {
			discountDatabase.getDiscount(discountDTO);
			fail("Value was returned despite discount database not existing.");
		} catch (NoDatabaseConnectionException e) {
			String expectedMsg = "Unable to perform operation as database could not be reached.";
			assertEquals(e.getMessage(), expectedMsg, "Wrong exception message.");
		}
	}
	
	@Test
	public void testGetDiscountValidCustomerID() {
		String customerID = "money";
		DiscountDTO discountDTO = new DiscountDTO(customerID, 0, new ItemDTO[0]);
		discountDatabase.setDiscountStrategy(new DiscountCustomerIdentification());
		try {
			double result = discountDatabase.getDiscount(discountDTO);
			double expectedResult = 0.1;
			assertEquals(expectedResult, result, result+" was returned instead of the expected "+expectedResult);
		} catch (NoDatabaseConnectionException e) {
			fail("Database could not be reached despite existing.");
		}
	}
	
	@Test
	public void testGetDiscountTotalAmount() {
		String customerID = "doesNotExist";
		DiscountDTO discountDTO = new DiscountDTO(customerID, 500, new ItemDTO[0]);
		discountDatabase.setDiscountStrategy(new DiscountTotalAmount());
		try {
			double result = discountDatabase.getDiscount(discountDTO);
			double expectedResult = 0.1;
			assertEquals(expectedResult, result, result+" was returned instead of the expected "+expectedResult);
		} catch (NoDatabaseConnectionException e) {
			fail("Database could not be reached despite existing.");
		}
	}
}
