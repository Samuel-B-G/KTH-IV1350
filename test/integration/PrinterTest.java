package integration;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import model.RequestedItemDTO;
import model.Sale;

public class PrinterTest {
	private Printer printer;
	private ByteArrayOutputStream outContent;
	private PrintStream originalSysOut;
	
	@BeforeEach
	public void setUp() {
		printer = new Printer();
		originalSysOut = System.out;
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}

	@AfterEach
	public void tearDown() {
		printer = null;
		outContent = null;
		System.setOut(originalSysOut);
	}
	
	@Test
	public void testCreateNewReceipt() throws ItemDoesNotExistException {
		InventorySystem inventorySystem = InventorySystem.getInventorySystem();
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0.2, 10);
		inventorySystem.addItem(itemInfoDTO, 1);
		
		Sale sale = new Sale();
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier");
		sale.addItem(inventorySystem, requestedItemDTO);
		
		double paidAmount = 20;
		
		double change = sale.pay(paidAmount);
		sale.printReceipt(printer);
		String result = outContent.toString();
		
		LocalDate date = LocalDate.now();
		String expected = "Time of Sale: "+date.toString();
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		
		int quantity = requestedItemDTO.getQuantity();
		double priceWithVAT = itemInfoDTO.getPrice()+itemInfoDTO.getPrice()*itemInfoDTO.getVATRate();
		expected = itemInfoDTO.getName()+", "+quantity+" x "+priceWithVAT+", "+priceWithVAT*quantity+" SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		
		expected = "Total: "+priceWithVAT*quantity+" SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");

		expected = "VAT: "+itemInfoDTO.getPrice()*itemInfoDTO.getVATRate()+" SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		
		expected = "Cash: "+paidAmount+" SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");

		expected = "Change: "+change+" SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
}
