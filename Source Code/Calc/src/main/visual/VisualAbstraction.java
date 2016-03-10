package main.visual;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class VisualAbstraction {

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

}
 