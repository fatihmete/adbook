package addressbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AddressBook;
import services.AddressBookService;

/**
 * Servlet implementation class Entry
 */
@WebServlet("/address-book/all")
public class Entry extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Entry() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AddressBookService addressBookService = new AddressBookService();
		PrintWriter writer = response.getWriter();
		response.setContentType("application/json");

		try {
			List<AddressBook> allAddressBookEntries = addressBookService.getAllAddressBookEntries();
			ObjectMapper json = new ObjectMapper();
			String jsonResponse = json.writeValueAsString(allAddressBookEntries);
			// writer.write("{ \"data\" : " + jsonResponse + " }");
			writer.write(jsonResponse);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}

}
