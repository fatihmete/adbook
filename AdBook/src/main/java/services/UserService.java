package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import connection.DB;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.UserMapper;
import model.User;
import utils.CookieUtils;

public class UserService {

	public UserService() {

	}

	public void doLogin(User user, HttpServletResponse reponse) {

		Cookie loginCookie = new Cookie("login", userSha256Value(user));
		loginCookie.setMaxAge(3600 * 24 * 15);
		Cookie userCookie = new Cookie("user", user.getId().toString());
		userCookie.setMaxAge(3600 * 24 * 15);

		reponse.addCookie(loginCookie);
		reponse.addCookie(userCookie);

	}

	public void doLogout(HttpServletResponse reponse) {

		Cookie loginCookie = new Cookie("login", "");
		loginCookie.setMaxAge(0);
		Cookie userCookie = new Cookie("user", "");
		userCookie.setMaxAge(0);

		reponse.addCookie(loginCookie);
		reponse.addCookie(userCookie);
	}

	public Boolean isAuth(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest) request;

		Cookie[] cookies = req.getCookies();
		if (cookies == null) {
			return false;
		}
		HashMap<String, String> cookieMap = CookieUtils.parseCookie(cookies);
		if (!cookieMap.containsKey("user") || !cookieMap.containsKey("login")) {
			return false;
		}

		try {
			User user = getUserById(Long.parseLong(cookieMap.get("user")));
			if (user == null) {
				return false;
			}

			if (!userSha256Value(user).equals(cookieMap.get("login"))) {
				return false;
			} else {
				request.setAttribute("user", user);
				return true;
			}
		} catch (NumberFormatException e) {

			e.printStackTrace();
			return false;

		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		}
	}

	public String userSha256Value(User user) {
		return DigestUtils.sha256Hex(user.getName() + user.getPassword() + user.getId().toString()).toString();
	}

	public User getUserById(Long id) throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement prstmt = connection.prepareStatement("SELECT * FROM users where id=?");
		prstmt.setLong(1, id);
		ResultSet rSet = prstmt.executeQuery();
		List<User> userList = UserMapper.map(rSet);
		if (userList.isEmpty()) {
			return null;
		} else {
			return userList.get(0);
		}

	}

	public int currentUserId(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		HashMap<String, String> cookieMap = CookieUtils.parseCookie(cookies);
		return Integer.parseInt(cookieMap.get("user"));
	}

	public User getUserByCreds(String email, String password) throws SQLException {

		Connection connection = DB.getConnection();
		PreparedStatement prstmt = connection.prepareStatement("SELECT * FROM users where email=? and password=?");
		prstmt.setString(1, email);
		prstmt.setString(2, DigestUtils.sha256Hex(password));
		ResultSet rSet = prstmt.executeQuery();
		List<User> userList = UserMapper.map(rSet);
		if (userList.isEmpty()) {
			return null;
		} else {
			return userList.get(0);
		}

	}

	public User save(User user) throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement prstmt = connection
				.prepareStatement("INSERT INTO users (name, email, password) values (?,?,?)");
		prstmt.setString(1, user.getName());
		prstmt.setString(2, user.getEmail());
		prstmt.setString(3, DigestUtils.sha256Hex(user.getPassword()));
		prstmt.executeUpdate();

		User newUser = getUserByCreds(user.getEmail(), user.getPassword());
		System.out.println(newUser.toString());
		return newUser;

	}
}
