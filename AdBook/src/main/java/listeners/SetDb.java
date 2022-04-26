package listeners;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import connection.DB;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class SetDb
 *
 */
@WebListener
public class SetDb implements ServletContextListener {

	/**
	 * Default constructor.
	 */

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub

		String usersTableSql = "CREATE TABLE \"users\" (\n" + "	\"id\"	INTEGER,\n" + "	\"name\"	TEXT,\n"
				+ "	\"email\"	TEXT NOT NULL UNIQUE,\n" + "	\"password\"	TEXT,\n"
				+ "	PRIMARY KEY(\"id\" AUTOINCREMENT)\n" + ");";

		String addressBookSql = "CREATE TABLE \"addressbook\" (\n" + "	\"id\"	INTEGER,\n" + "	\"user_id\"	INTEGER,\n"
				+ "	\"name\"	TEXT,\n" + "	\"surname\"	TEXT,\n" + "	\"phone\"	TEXT,\n"
				+ "	\"age\"	INTEGER,\n" + "	\"address\"	TEXT,\n" + "	\"created_at\"	TEXT,\n"
				+ "	\"updated_at\"	TEXT,\n" + "	PRIMARY KEY(\"id\" AUTOINCREMENT)\n" + ");";

		try {

			Connection connection = DB.getConnection();

			connection.prepareStatement(usersTableSql).execute();

			PreparedStatement adminUserStatement = connection
					.prepareStatement("INSERT INTO users (name, email, password) values (?, ?,?) ");
			adminUserStatement.setString(1, "admin");
			adminUserStatement.setString(2, "admin@localhost");
			adminUserStatement.setString(3, DigestUtils.sha256Hex("password"));
			adminUserStatement.execute();

			connection.prepareStatement(addressBookSql).execute();

			PreparedStatement addressBookDefaultDataStatement = connection.prepareStatement(
					"INSERT INTO addressbook (user_id, name, surname, phone, age, address, created_at, updated_at) "
							+ "values (?, ?, ?, ?, ?, ?, ?, ?) ");
			addressBookDefaultDataStatement.setInt(1, 1);
			addressBookDefaultDataStatement.setString(2, "Nicholas C.");
			addressBookDefaultDataStatement.setString(3, "Tate");
			addressBookDefaultDataStatement.setString(4, "662-448-3109");
			addressBookDefaultDataStatement.setInt(5, 69);
			addressBookDefaultDataStatement.setString(6, "4379 Mcwhorter Road\nHouston, MS 38851 ");

			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String strDate = dateFormat.format(date);
			addressBookDefaultDataStatement.setString(7, strDate);
			addressBookDefaultDataStatement.setString(8, null);

			addressBookDefaultDataStatement.execute();

			connection.close();

			System.out.println("Database Created");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

		try {
			Files.deleteIfExists(Paths.get(System.getProperty("java.io.tmpdir") + "app.sqlite3"));
			System.out.println("Database deleted");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
