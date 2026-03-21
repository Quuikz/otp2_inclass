package org.otp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocalizationTest {

	private final PrintStream originalOut = System.out;
	private Locale originalDefaultLocale;

	@BeforeEach
	void setUp() {
		originalDefaultLocale = Locale.getDefault();
		Locale.setDefault(Locale.ENGLISH);
	}

	@AfterEach
	void tearDown() {
		System.setOut(originalOut);
		Locale.setDefault(originalDefaultLocale);
	}

	@Test
	void setQuantity_retriesUntilPositiveWholeNumber() {
		ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", Locale.UK);
		setScannerInput("abc\n0\n3\n");

		int quantity = Localization.setQuantity(bundle);

		assertEquals(3, quantity);
	}

	@Test
	void setPrice_retriesUntilNonNegativeNumber() {
		ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", Locale.UK);
		setScannerInput("oops\n-2\n1.25\n");

		double price = Localization.setPrice(bundle);

		assertEquals(1.25, price, 0.0001);
	}

	@Test
	void main_calculatesSingleItemTotalCostCorrectly() {
		String input = String.join("\n",
				"en", // locale
				"1", // items
				"Apple", "3", "2.50" // quantity * price = 7.50
		) + "\n";

		String output = runMainWithInput(input);

		assertTrue(output.contains("7.50"));
	}

	@Test
	void main_calculatesCartTotalForMultipleItemsCorrectly() {
		String input = String.join("\n",
				"en", // locale
				"2", // items
				"Milk", "2", "1.50", // 3.00
				"Bread", "1", "2.00" // 2.00
		) + "\n";

		String output = runMainWithInput(input);

		assertTrue(output.contains("5.00"));
	}

	private static void setScannerInput(String input) {
		Localization.scanner = new java.util.Scanner(
				new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
		);
	}

	private static String runMainWithInput(String input) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
		setScannerInput(input);

		Localization.main(new String[0]);

		return output.toString(StandardCharsets.UTF_8);
	}
}