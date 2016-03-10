package main.audio;

import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class AudioAbstraction {

	// <digit> = one | two | three | four | five | six | seven | eight | nine ;
	// <tenDigit> = ten | eleven | twelve | thirteen | fourteen | fifteen |
	// sixteen | seventeen | eighteen | nineteen ;
	// <twentyDigit> = twenty | thirty | forty | fifty | sixty | seventy |
	// eighty | ninety ;
	
	static HashMap<String, Integer> twentyDigit = new HashMap<>();
	static HashMap<String, Integer> tenDigit = new HashMap<>();
	static HashMap<String, Integer> digit = new HashMap<>();

	private ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");


	public String eval(String matlab_expression) {
		if (matlab_expression == null) {
			return "NULL";
		}
		String js_parsable_expression = matlab_expression.replaceAll("\\((\\-?\\d+)\\)\\^(\\-?\\d+)", "(Math.pow($1,$2))").replaceAll(
				"(\\d+)\\^(\\-?\\d+)", "Math.pow($1,$2)");
		try {
			return engine.eval(js_parsable_expression).toString();
		} catch (javax.script.ScriptException e1) {
			return "Invalid Expression"; // Invalid Expression
		}
	}

	private static Integer number(String input) {
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

	private static Integer threeNumber(String input) {
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

	private static Integer sixNumber(String input) {
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

	public String parser(String input) {
		
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

		Integer result = 0;
		boolean hundredFlag = false, thousandFlag = false;
		String test[] = input.split("\\s+");

		for (int i = 0; i < test.length; i++) {
			String elem = test[i];
			if (elem.equalsIgnoreCase("hundred")) {
				hundredFlag = true;
			}
			if (elem.equalsIgnoreCase("thousand")) {
				thousandFlag = true;
			}
		}

		// dealing with 4 phase
		result = number(input);

		if (hundredFlag && !thousandFlag) {
			result = threeNumber(input);
		}

		if (result == 0 || thousandFlag) {
			result = sixNumber(input);
		}

		return result.toString();
	}

	public String lookUp(String input) {
		String result = null;
		if (input.equalsIgnoreCase("oh") || input.equalsIgnoreCase("zero")) {
			result = "0";
		} else if (input.equalsIgnoreCase("one")) {
			result = "1";
		} else if (input.equalsIgnoreCase("two")) {
			result = "2";
		} else if (input.equalsIgnoreCase("three")) {
			result = "3";
		} else if (input.equalsIgnoreCase("four")) {
			result = "4";
		} else if (input.equalsIgnoreCase("five")) {
			result = "5";
		} else if (input.equalsIgnoreCase("six")) {
			result = "6";
		} else if (input.equalsIgnoreCase("seven")) {
			result = "7";
		} else if (input.equalsIgnoreCase("eight")) {
			result = "8";
		} else if (input.equalsIgnoreCase("nine")) {
			result = "9";
		} else if (input.equalsIgnoreCase("ten")) {
			result = "10";
		} else if (input.equalsIgnoreCase("eleven")) {
			result = "11";
		} else if (input.equalsIgnoreCase("twelve")) {
			result = "12";
		} else if (input.equalsIgnoreCase("thirteen")) {
			result = "13";
		} else if (input.equalsIgnoreCase("fourteen")) {
			result = "14";
		} else if (input.equalsIgnoreCase("fifteen")) {
			result = "15";
		} else if (input.equalsIgnoreCase("sixteen")) {
			result = "16";
		} else if (input.equalsIgnoreCase("seventeen")) {
			result = "17";
		} else if (input.equalsIgnoreCase("eighteen")) {
			result = "18";
		} else if (input.equalsIgnoreCase("nineteen")) {
			result = "19";
		} else {
		}

		return result;
	}
}
