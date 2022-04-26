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

/**
 * Servlet implementation class EntryDelete
 */
@WebServlet("/address-book/delete")
public class EntryDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EntryDelete() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String entryId = request.getParameter("entryId");
		AddressBookService addressBookService = new AddressBookService();
		PrintWriter writer = response.getWriter();
		try {
			AddressBook addressBookEntry = addressBookService.getAdressBookEntryById(Long.parseLong(entryId));
			int userId = new UserService().currentUserId(request);

			if (addressBookEntry.getUser_id() == userId) {

				addressBookService.deleteById(Integer.parseInt(entryId));
				response.setStatus(200);

			} else {
				response.setStatus(401);
			}

		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			response.setStatus(400);
		}
		response.setContentType("application/json");
		writer.write("{}");

		writer.close();

	}

}
