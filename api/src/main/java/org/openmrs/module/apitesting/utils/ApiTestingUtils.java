package org.openmrs.module.apitesting.utils;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;

public class ApiTestingUtils {
	
	/**
	 * Generates a random number 0 or 1
	 * 
	 * @return a random number
	 */
	public static int getRandomZeroOrOne() {
		return ThreadLocalRandom.current().nextInt(2);
	}
	
	/**
	 * Split a string using the given delimiter
	 * 
	 * @param input
	 * @param delimiter
	 * @return a list of strings
	 */
	public static List<String> splitString(String input, String delimiter) {
		return Arrays.asList(input.split(delimiter));
	}
	
	/**
	 * Convert a string to an integer
	 * 
	 * @param input
	 * @return
	 */
	public static int convertStringToInt(String input) {
		try {
			return Integer.parseInt(input.trim()); // Trim spaces and convert to integer
		}
		catch (NumberFormatException e) {
			// Log error (optional)
			System.err.println("API TESTING MODULE: EID Lab MOCK: Error converting to integer: " + e.getMessage());
			return 0; // Return 0 in case of an error
		}
	}
}
