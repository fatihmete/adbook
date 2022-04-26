package user;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import services.UserService;
import utils.ValidationUtils;

/**
 * Servlet implementation class UserLogin
 */
@WebServlet("/login")
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserLogin() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.getRequestDispatcher("/user/login.jsp").forward(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		PrintWriter writer = response.getWriter();
		if (ValidationUtils.isEmail(email) && ValidationUtils.minLenght(password, 6)) {

			try {
				UserService userService = new UserService();
				User user = userService.getUserByCreds(email, password);
				if (user != null) {
					userService.doLogin(user, response);

					response.setStatus(200);
				} else {
					response.setStatus(401);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.setStatus(400);
			}
		} else {
			response.setStatus(400);
		}

		response.setContentType("application/json");
		writer.write("{}");

		writer.close();

	}

}
