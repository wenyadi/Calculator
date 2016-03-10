package test;

import java.util.HashMap;
import java.util.Map;

public class testParser {
 
	/**
	 * @param args
	 * 
	 * 
	 */

	// <number> = <digit> | <tenDigit> | <twentyDigit> | <twentyDigit> <digit> ;
	// //1-99
	// <threeNumber> = <digit> <hundred> | <digit> <hundred> <number> | <number>
	// ; //100-999
	// <sixNumber> = <digit> <thousand> | <threeNumber> <thousand> <threeNumber>
	// | <threeNumber> ; //1000-999,999

	static HashMap<String, Integer> twentyDigit = new HashMap<>();
	static HashMap<String, Integer> tenDigit = new HashMap<>();
	static HashMap<String, Integer> digit = new HashMap<>();

	public static void main(String[] args) {

		digit.put("one", 1);
		digit.put("two", 2);
		digit.put("three", 3);
		digit.put("four", 4);
		digit.put("five", 5);
		digit.put("six", 6);
		digit.put("seven", 7);
		digit.put("eight", 8);
		digit.put("nine", 9);

		tenDigit.put("ten", 10);
		tenDigit.put("eleven", 11);
		tenDigit.put("twelve", 12);
		tenDigit.put("thirteen", 13);
		tenDigit.put("fourteen", 14);
		tenDigit.put("fifteen", 15);
		tenDigit.put("sixteen", 16);
		tenDigit.put("seventeen", 17);
		tenDigit.put("eighteen", 18);
		tenDigit.put("nineteen", 19);

		twentyDigit.put("twenty", 20);
		twentyDigit.put("thirty", 30);
		twentyDigit.put("forty", 40);
		twentyDigit.put("fifty", 50);
		twentyDigit.put("sixty", 60);
		twentyDigit.put("seventy", 70);
		twentyDigit.put("eighty", 80);
		twentyDigit.put("ninety", 90);

//		String one = "one";
//		String oneHundred = "five hundred";
//		String oneThousand = "twelve thousand";
//		String twentyThree = "twenty three";
//		String three = "one hundred thirty";
//		String threee = "twenty three thousand";
//		String four = "one hundred twenty one";
//		String five = "two hundred thousand two hundred twenty one";
//		String six = "three hundred forty two thousand one hundred thirty four";

//		parser(one);
//		parser(twentyThree);
//		parser(oneHundred);
//		parser(oneThousand);
//		parser(three);
//		parser(threee);
//		parser(four);
//		parser(five);
//		parser(six);
		
		parser("one");
		parser("ten");
		parser("twenty one");
		parser("one hundred one");
		parser("one hundred");
		parser("five hundred twenty one");
		parser("five hundred twenty");
		parser("one thousand twenty five");
		parser("twenty five thousand twenty five");
		parser("nine hundred ninety nine thousand nine hundred ninety nine");
		parser("five hundred sixty seven thousand");
	}

	public static Integer number(String input) {
		Integer result = 0;
		String test[] = input.split("\\s+");

		// Dealing with 1 phase;
		if (test.length == 1) {
			String element = test[0];
			// range 1-19, 20, 30, 40, 50 ,60, 70 , 80, 90
			for (Map.Entry<String, Integer> map : digit.entrySet()) {
				String key = map.getKey();
				Integer value = map.getValue();
				if (element.equalsIgnoreCase(key)) {
					result = value;
				}
			}
			for (Map.Entry<String, Integer> map : tenDigit.entrySet()) {
				String key = map.getKey();
				Integer value = map.getValue();
				if (element.equalsIgnoreCase(key)) {
					result = value;
				}
			}
			for (Map.Entry<String, Integer> map : twentyDigit.entrySet()) {
				String key = map.getKey();
				Integer value = map.getValue();
				if (element.equalsIgnoreCase(key)) {
					result = value;
				}
			}
		}

		// dealing with 2 phase
		if (test.length == 2) {
			String First = test[0];
			String Second = test[1];

			// range 21-99
			for (Map.Entry<String, Integer> map : twentyDigit.entrySet()) {
				String key = map.getKey();
				Integer value = map.getValue();
				if (First.equalsIgnoreCase(key)) {
					result += value;
				}
			}

			for (Map.Entry<String, Integer> map2 : digit.entrySet()) {
				String key2 = map2.getKey();
				Integer value2 = map2.getValue();
				if (Second.equalsIgnoreCase(key2)) {
					result += value2;
				}
			}
		}

		return result;
	}

	public static Integer threeNumber(String input) {
		Integer result = 0;
		String test[] = input.split("hundred");

		if (test.length == 1) {
			String First = test[0];

			result = number(First.trim());
			result = result * 100;
		}

		// dealing with 2 phase
		if (test.length == 2) {
			String First = test[0];
			String Second = test[1];

			result = number(First.trim());
			result = result * 100;
			result = result + number(Second.trim());

		}

		return result;
	}

	public static Integer sixNumber(String input) {
		Integer result = 0;
		String test[] = input.split("thousand");

		if (test.length == 2) {
			String First = test[0];
			String Second = test[1];

			if (First.contains("hundred")) {
				result = threeNumber(First.trim());
			} else {
				result = result + number(First.trim());
			}
			
			result = result * 1000;
			
			if (Second.contains("hundred")) {
				result = result + threeNumber(Second.trim());
			} else {
				result = result + number(Second.trim());
			}
		}

		if (test.length == 1) {
			String First = test[0];
			if (First.contains("hundred")) {
				result = threeNumber(First.trim());
			} else {
				result = result + number(First.trim());
			}
			
			result = result * 1000;
		}

		return result;
	}

	public static String parser(String input) {
		Integer result = 0;
		boolean hundredFlag = false, thousandFlag = false;
		String test[] = input.split("\\s+");

		System.out.println(test.length);
		for (int i = 0; i < test.length; i++) {
			String elem = test[i];
			if (elem.equalsIgnoreCase("hundred")) {
				hundredFlag = true;
			}
			if (elem.equalsIgnoreCase("thousand")) {
				thousandFlag = true;
			}
			System.out.println(i + elem);
		}

		// dealing with 4 phase
		result = number(input);

		if (hundredFlag && !thousandFlag) {
			result = threeNumber(input);
		}

		if (result == 0 || thousandFlag) {
			result = sixNumber(input);
		}

		System.out.println(result);
		return result.toString();
	}
}
 