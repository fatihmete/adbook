package utils;

import java.util.HashMap;

import jakarta.servlet.http.Cookie;

public class CookieUtils {

	public static HashMap<String, String> parseCookie(Cookie[] cookies) {
		HashMap<String, String> cookieMap = new HashMap<String, String>();

		for (Cookie cookie : cookies) {
			cookieMap.put(cookie.getName(), cookie.getValue());
		}
		return cookieMap;

	}
}
