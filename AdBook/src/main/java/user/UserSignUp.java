package user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import services.UserService;
import utils.ValidationUtils;

/**
 * Servlet implementation class UserSignUp
 */
@WebServlet("/signup")
public class UserSignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserService userService = new UserService();
		if (userService.isAuth(request)) {
			response.sendRedirect(request.getContextPath() + "/address-book");
			return;
		} else {
			request.getRequestDispatcher("/user/signup.jsp").forward(request, response);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		PrintWriter writer = response.getWriter();
		UserService userService = new UserService();

		if (ValidationUtils.isEmail(email) && ValidationUtils.minLenght(name, 3)
				&& ValidationUtils.minLenght(password, 6)) {

			User user = new User();
			user.setName(name);
			user.setEmail(email);
			user.setPassword(password);

			try {

				User newUser = userService.save(user);
				userService.doLogin(newUser, response);
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
