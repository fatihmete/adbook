package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.AddressBook;

public class AddressBookMapper {

	public static List<AddressBook> map(ResultSet rSet) throws SQLException {
		List<AddressBook> addressBookList = new ArrayList<AddressBook>();
		while (rSet.next()) {
			AddressBook addressBook = new AddressBook();
			addressBook.setId(rSet.getLong("id"));
			addressBook.setUser_id(rSet.getLong("user_id"));
			addressBook.setName(rSet.getString("name"));
			addressBook.setSurname(rSet.getString("surname"));
			addressBook.setPhone(rSet.getString("phone"));
			addressBook.setAge(rSet.getInt("age"));
			addressBook.setAddress(rSet.getString("address"));
			addressBook.setCreated_at(rSet.getString("created_at"));
			addressBook.setUpdated_at(rSet.getString("updated_at"));
			addressBookList.add(addressBook);
		}
		return addressBookList;
	}
}
