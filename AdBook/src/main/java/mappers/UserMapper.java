package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserMapper {

	public static List<User> map(ResultSet rSet) throws SQLException {
		List<User> userList = new ArrayList<User>();
		while (rSet.next()) {
			User user = new User();
			user.setId(rSet.getLong("id"));
			user.setName(rSet.getString("name"));
			user.setEmail(rSet.getString("email"));
			user.setPassword(rSet.getString("password"));
			userList.add(user);
		}
		return userList;
	}
}
