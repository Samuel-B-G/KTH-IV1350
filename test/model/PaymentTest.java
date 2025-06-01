package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class PaymentTest {

	@Test
	public void testCreatePaymentCorrectDate() {
		String date = LocalDate.now().toString();
		Payment payment = new Payment(0);
		String dateFromPayment = payment.getTimeOfSale();
		assertEquals(date, dateFromPayment, "Correct date was not stored");
	}

	@Test
	public void testCalculateChangePaidCorrectAmount() {
		Payment payment = new Payment(10);
		double result = payment.calculateChange(10);
		assertEquals(0, result, "Incorrect value was returned");
	}

	@Test
	public void testCalculateChangePaidTooMuch() {
		Payment payment = new Payment(11);
		double result = payment.calculateChange(10);
		assertEquals(1, result, "Incorrect value was returned");
	}

	@Test
	public void testCalculateChangePaidTooLittle() {
		Payment payment = new Payment(9);
		double result = payment.calculateChange(10);
		assertEquals(-1, result, "Incorrect value was returned");
	}
}
