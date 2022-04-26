package addressbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AddressBook;
import services.AddressBookService;
import services.UserService;
import utils.ValidationUtils;

/**
 * Servlet implementation class EntryUpdate
 */
@WebServlet("/address-book/update")
public class EntryUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EntryUpdate() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String phone = request.getParameter("phone");
		String age = request.getParameter("age");
		String address = request.getParameter("address");
		String entryId = request.getParameter("updateEntryId");

		AddressBookService addressBookService = new AddressBookService();
		PrintWriter writer = response.getWriter();

		if (ValidationUtils.minLenght(name, 3) && ValidationUtils.minLenght(surname, 3)
				&& ValidationUtils.minLenght(phone, 3) && ValidationUtils.minLenght(address, 10)
				&& ValidationUtils.isNumeric(age)) {
			try {
				AddressBook addressBookEntry = addressBookService.getAdressBookEntryById(Long.parseLong(entryId));
				int userId = new UserService().currentUserId(request);

				if ((int) addressBookEntry.getUser_id() == userId) {
					addressBookEntry.setName(name);
					addressBookEntry.setSurname(surname);
					addressBookEntry.setPhone(phone);
					addressBookEntry.setAge(Integer.parseInt(age));
					addressBookEntry.setAddress(address);
					addressBookService.update(addressBookEntry);
					response.setStatus(200);
				} else {
					response.setStatus(401);
				}

			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.setStatus(500);
			}

		} else {
			response.setStatus(500);
		}

		response.setContentType("application/json");
		writer.write("{}");

		writer.close();
	}

}
