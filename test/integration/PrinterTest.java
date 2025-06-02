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

class PrinterTest {
	private Printer printer;
	private ByteArrayOutputStream outContent;
	private PrintStream originalSysOut;
	
	private Sale sale;
	
	@BeforeEach
	public void setUp() throws ItemDoesNotExistException {
		printer = new Printer();
		originalSysOut = System.out;
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		InventorySystem inventorySystem = InventorySystem.getInventorySystem();
		ItemInfoDTO itemInfoDTO = new ItemInfoDTO("identifier", "name", "description", 0.2, 10);
		inventorySystem.addItem(itemInfoDTO, 1);
		
		sale = new Sale();
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("identifier");
		sale.addItem(inventorySystem, requestedItemDTO);
		sale.pay(20);
	}

	@AfterEach
	public void tearDown() {
		printer = null;
		outContent = null;
		System.setOut(originalSysOut);
		sale = null;
	}
	
	@Test
	public void testReceiptTimeOutput() {
		sale.printReceipt(printer);
		String result = outContent.toString();
		LocalDate date = LocalDate.now();
		String expected = "Time of Sale: "+date.toString();
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
	
	@Test
	public void testReceiptItemOutput() {
		sale.printReceipt(printer);
		String result = outContent.toString();
		String expected = "name"+", "+1+" x "+12.0+", "+12.0+" SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
	
	@Test
	public void testReceiptTotalOutput() {
		sale.printReceipt(printer);
		String result = outContent.toString();
		String expected = "Total: "+12.0+" SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
	
	@Test
	public void testReceiptVATOutput() {
		sale.printReceipt(printer);
		String result = outContent.toString();
		String expected = "VAT: "+2.0+" SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
	
	@Test
	public void testReceiptPaymentOutput() {
		sale.printReceipt(printer);
		String result = outContent.toString();
		String expected = "Cash: "+20.0+" SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
	
	@Test
	public void testReceiptChangeOutput() {
		sale.printReceipt(printer);
		String result = outContent.toString();
		String expected = "Change: "+8.0+" SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
}
