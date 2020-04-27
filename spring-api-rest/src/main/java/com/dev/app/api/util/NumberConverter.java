package com.dev.app.api.util;

import java.util.function.Function;
import java.util.function.Predicate;

public class NumberConverter {
	
	public static Double convertToDouble(String strNumber) {
		Function<String, Double> convert = (s) -> {
			if (s == null)
				return 0d;
			String number = s.replaceAll(",", ".");
			if (isNumeric(number))
				return Double.parseDouble(number);
			return 0d;
		};
		return convert.apply(strNumber);
	}
	
	public static boolean isNumeric(String strNumber) {
		
		Predicate<String> validator = (s) -> {
			if (s == null)
				return false;
			String number = s.replaceAll(",", ".");
			return number.matches("[-+]?[0-9]*\\.?[0-9]+");
		};
		return validator.test(strNumber);
	}
}