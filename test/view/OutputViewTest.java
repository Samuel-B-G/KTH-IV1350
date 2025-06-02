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

public class OutputViewTest {
	private ByteArrayOutputStream outContent;
	private PrintStream originalSysOut;
	
	DatabaseCreator creator;
	Controller contr;
	OutputView outputView;
	
	@BeforeEach
	public void setUp() {
		this.creator = new DatabaseCreator();
		this.contr = new Controller(creator);
		this.outputView = new OutputView(contr, new BoundsDTO(0,0,0,0));
		contr.addSaleObserver(outputView);
		originalSysOut = System.out;
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}

	@AfterEach
	public void tearDown() {
		creator.getInventorySystem().emptyInventory();
		this.creator = null;
		this.contr = null;
		this.outputView = null;
		outContent = null;
		System.setOut(originalSysOut);
	}
	
	@Test
	public void newItemOutputTest() throws OperationFailedException, NoActiveSaleException {
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0.1, 10);
		creator.getInventorySystem().addItem(itemInfoDTO, 0);

		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier");
		
		contr.newSale();
		contr.addItem(requestedItemDTO);
		
		String result = outContent.toString();
		
		String expected = "Add " + requestedItemDTO.getQuantity() + " item(s) with item ID: " + requestedItemDTO.getItemIdentifier();
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		
		expected = "Item name: " + itemInfoDTO.getName();
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		
		expected = "Item cost: " + 10.0 + " SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		
		expected = "Item description: " + itemInfoDTO.getItemDescription();
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		
		expected = "Total cost (incl VAT): " + 11.0 + " SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		
		expected = "VAT: " + 10.0 + "%";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
	
	@Test
	public void endSaleOutputTest() throws OperationFailedException, NoActiveSaleException {
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0.1, 10);
		creator.getInventorySystem().addItem(itemInfoDTO, 0);

		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier");
		
		contr.newSale();
		contr.addItem(requestedItemDTO);

		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		contr.endSale();
		String result = outContent.toString();
		String expected = "Total cost (incl VAT): " + 11.0 + " SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
	
	@Test
	public void paymentOutputTest() throws OperationFailedException, NoActiveSaleException {
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0.1, 10);
		creator.getInventorySystem().addItem(itemInfoDTO, 0);

		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier");
		
		contr.newSale();
		contr.addItem(requestedItemDTO);
		
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		contr.pay(20);
		String result = outContent.toString();
		String expected = "Change to give the customer: " + 9.0 + " SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
}
