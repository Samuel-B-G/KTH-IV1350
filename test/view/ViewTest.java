package view;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.NoActiveSaleException;
import controller.OperationFailedException;
import integration.DatabaseCreator;
import model.RequestedItemDTO;

class ViewTest {
	private ByteArrayOutputStream outContent;
	private PrintStream originalSysOut;
	
	DatabaseCreator creator;
	Controller contr;
	View view;
	
	@BeforeEach
	public void setUp() {
		this.creator = new DatabaseCreator();
		this.contr = new Controller(creator);
		this.view = new View(contr);
		originalSysOut = System.out;
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}

	@AfterEach
	public void tearDown() {
		creator.getInventorySystem().emptyInventory();
		this.creator = null;
		this.contr = null;
		this.view = null;
		outContent = null;
		System.setOut(originalSysOut);
	}
	
	@Test
	public void databaseExceptionOutputTest() throws NoActiveSaleException {
		contr.newSale();
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		try {
			contr.checkDiscount("error");
			fail("Exception was not caught as expected.");
		} catch (OperationFailedException e) {
			view.writeToLogAndUI(e.getMessage(), (Exception) e.getCause());
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
			
			String result = outContent.toString();
			String expected = now.format(formatter) + ", ERROR: " + "Could not contact the database.";
			assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		}
	}
	
	@Test
	public void itemDoesNotExistExceptionOutputTest() throws NoActiveSaleException {
		contr.newSale();
		
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO("doesNotExist");
		
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		try {
			contr.addItem(requestedItemDTO);
			fail("Exception was not caught as expected.");
		} catch (OperationFailedException e) {
			view.writeToLogAndUI(e.getMessage(), (Exception) e.getCause());
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
			
			String result = outContent.toString();
			String expected = now.format(formatter) + ", ERROR: " + "Item was not found.";
			assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		}
	}
	
	@Test
	public void noActiveSaleExceptionOutputTest() throws OperationFailedException {
		try {
			contr.endSale();
			fail("Exception was not caught as expected.");
		} catch (NoActiveSaleException e) {
			view.writeToLogAndUI(e.getMessage(), e);
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
			
			String result = outContent.toString();
			String expected = now.format(formatter) + ", ERROR: " + "Operation failed as no sale was active.";
			assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		}
	}
	
	@Test
	public void incorrectInputExceptionOutputTest() throws NoActiveSaleException {
		try {
			Integer.parseInt("Test");
			fail("Exception was not caught as expected.");
		}
		catch (NumberFormatException exception) {
			IncorrectInputException e = new IncorrectInputException("One or more non-numeric characters were inputted into a field expecting a numeric value.");

			view.writeToLogAndUI("Non-numeric characters found in input.", e);
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
			
			String result = outContent.toString();
			String expected = now.format(formatter) + ", ERROR: " + "Non-numeric characters found in input.";
			assertTrue(result.contains(expected), "Expected String '"+expected+"' but instead received String '"+result+"'");
		}
	}
}
