package view;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.NoActiveSaleException;
import controller.OperationFailedException;
import integration.DatabaseCreator;
import integration.ItemInfoDTO;
import model.RequestedItemDTO;

public class TotalRevenueViewTest {
	private ByteArrayOutputStream outContent;
	private PrintStream originalSysOut;
	
	DatabaseCreator creator;
	Controller contr;
	TotalRevenueView totalRevenueView;
	
	@BeforeEach
	public void setUp() {
		this.creator = new DatabaseCreator();
		this.contr = new Controller(creator);
		this.totalRevenueView = new TotalRevenueView(new BoundsDTO(0,0,0,0));
		contr.addTotalRevenueObserver(totalRevenueView);
		originalSysOut = System.out;
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}

	@AfterEach
	public void tearDown() {
		creator.getInventorySystem().emptyInventory();
		this.creator = null;
		this.contr = null;
		this.totalRevenueView = null;
		outContent = null;
		System.setOut(originalSysOut);
	}
	
	@Test
	public void testUpdateTotalRevenue() throws OperationFailedException, NoActiveSaleException {
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0.2, 10);
		creator.getInventorySystem().addItem(itemInfoDTO, 0);

		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier");
		
		contr.newSale();
		contr.addItem(requestedItemDTO);
		contr.pay(20);
		String result = outContent.toString();
		
		String expected = "Total Revenue: " + 12.0 + " SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		
		contr.newSale();
		contr.addItem(requestedItemDTO);
		contr.pay(20);
		result = outContent.toString();
		expected = "Total Revenue: " + 24.0 + " SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
}
