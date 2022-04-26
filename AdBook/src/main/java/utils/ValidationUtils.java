package utils;

import java.util.regex.Pattern;

public class ValidationUtils {

	public static boolean isEmail(String email) {
		return patternMatches(email, "^(.+)@(.+)$");
	}

	public static boolean minLenght(String field, int lenght) {

		return field.length() >= lenght;

	}

	public static boolean maxLenght(String field, int lenght) {

		return field.length() <= lenght;

	}

	public static boolean isNumeric(String field) {
		return patternMatches(field, "-?\\d+(\\.\\d+)?");

	}

	public static boolean patternMatches(String text, String regexPattern) {
		return Pattern.compile(regexPattern).matcher(text).matches();
	}

}
