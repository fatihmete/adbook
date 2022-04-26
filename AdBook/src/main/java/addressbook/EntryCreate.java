package addressbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
 * Servlet implementation class EntryCreate
 */
@WebServlet("/address-book/create")
public class EntryCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EntryCreate() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String phone = request.getParameter("phone");
		String age = request.getParameter("age");
		String address = request.getParameter("address");
		PrintWriter writer = response.getWriter();

		AddressBookService addressBookService = new AddressBookService();
		if (ValidationUtils.minLenght(name, 3) && ValidationUtils.minLenght(surname, 3)
				&& ValidationUtils.minLenght(phone, 3) && ValidationUtils.minLenght(address, 10)
				&& ValidationUtils.isNumeric(age)) {

			int userId = new UserService().currentUserId(request);

			AddressBook addressBook = new AddressBook();
			addressBook.setName(name);
			addressBook.setSurname(surname);
			addressBook.setAge(Integer.parseInt(age));
			addressBook.setPhone(phone);
			addressBook.setAddress(address);
			addressBook.setUser_id(userId);

			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			String strDate = dateFormat.format(date);

			addressBook.setCreated_at(strDate);
			addressBook.setUpdated_at(null);

			try {

				addressBookService.save(addressBook);
				response.setStatus(200);

			} catch (SQLException e) {
				e.printStackTrace();
				response.setStatus(400);
			}

		} else {

			response.setStatus(401);

		}
		response.setContentType("application/json");
		writer.write("{}");

		writer.close();
	}

}
