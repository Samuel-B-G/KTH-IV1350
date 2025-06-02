package startup;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Controller;
import integration.DatabaseCreator;

class StartupTest {
	private ByteArrayOutputStream outContent;
	private PrintStream originalSysOut;
	
	DatabaseCreator creator;
	Controller contr;
	Startup startup;
	
	@BeforeEach
	public void setUp() {
		this.creator = new DatabaseCreator();
		this.contr = new Controller(creator);
		
		originalSysOut = System.out;
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		Startup.main(null);
	}

	@AfterEach
	public void tearDown() {
		creator.getInventorySystem().emptyInventory();
		this.creator = null;
		this.contr = null;
		
		outContent = null;
		System.setOut(originalSysOut);
	}
	
	@Test
	public void addItemOutputTest() {
		String result = outContent.toString();
		
		String expected = "Add " + 2 + " item(s) with item ID: " + "abc123";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		
		expected = "Item name: " + "Oatmeal";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		
		expected = "Item cost: " + 55.0 + " SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		
		expected = "Item description: " + "400g, Organic";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		
		expected = "Total cost (incl VAT): " + 137.5 + " SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		
		expected = "VAT: " + 25.0 + "%";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
	
	@Test
	public void endSaleOutputTest() {
		String result = outContent.toString();
		result = result.substring(result.indexOf("Total cost (incl VAT): 137.5 SEK"),result.length());
		
		String expected = "Total cost (incl VAT): 137.5 SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
	
	@Test
	public void discountOutputTest() {
		String result = outContent.toString();
		
		String expected = "Total cost (incl VAT): 123.75 SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
	
	@Test
	public void paymentOutputTest() {
		String result = outContent.toString();
		
		String expected = "Change to give the customer: 76.25 SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
	
	@Test
	public void printReceiptOutputTest() {
		String result = outContent.toString();
		
		LocalDate date = LocalDate.now();
		String expected = "Time of Sale: "+date.toString();
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");

		expected = "Oatmeal"+", "+"2"+" x "+"68.75"+", "+"137.5"+" SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		
		expected = "Total: "+"123.75"+" SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");

		expected = "VAT: "+"27.5"+" SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		
		expected = "Cash: "+"200.0"+" SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");

		expected = "Change: "+"76.25"+" SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
	
	@Test
	public void totalRevenueOutputTest() {
		String result = outContent.toString();
		
		String expected = "Total Revenue: 123.75 SEK";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
	
	@Test
	public void noActiveSaleExceptionOutputTest() {
		String result = outContent.toString();
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
		String time = now.format(formatter);
		
		String expected = time + ", ERROR: " + "Operation failed as no sale was active.";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
	
	@Test
	public void invalidItemExceptionOutputTest() {
		String result = outContent.toString();
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
		String time = now.format(formatter);
		
		String expected = time + ", ERROR: " + "Item was not found.";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
	
	@Test
	public void noDatabaseConnectionExceptionOutputTest() {
		String result = outContent.toString();
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
		String time = now.format(formatter);
		
		String expected = time + ", ERROR: " + "Could not contact the database.";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
	
	@Test
	public void incorrectInputExceptionOutputTest() {
		String result = outContent.toString();
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
		String time = now.format(formatter);
		
		String expected = time + ", ERROR: " + "Non-numeric characters found in input.";
		assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
	}
}
