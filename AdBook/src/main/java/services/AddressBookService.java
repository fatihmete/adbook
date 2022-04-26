package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import connection.DB;
import mappers.AddressBookMapper;
import model.AddressBook;

public class AddressBookService {

	public List<AddressBook> getAllAddressBookEntries() throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement prstmt = connection.prepareStatement("SELECT * FROM addressbook order by created_at desc");
		ResultSet rSet = prstmt.executeQuery();
		List<AddressBook> addressBookList = AddressBookMapper.map(rSet);
		if (addressBookList.isEmpty()) {
			return null;
		} else {
			return addressBookList;
		}

	}

	public AddressBook getAdressBookEntryById(Long id) throws SQLException {
		Connection connection = DB.getConnection();
		PreparedStatement prstmt = connection.prepareStatement("SELECT * FROM addressbook where id=?");
		prstmt.setLong(1, id);
		ResultSet rSet = prstmt.executeQuery();
		List<AddressBook> addressBookList = AddressBookMapper.map(rSet);

		if (addressBookList.isEmpty()) {
			return null;
		} else {
			return addressBookList.get(0);
		}

	}

	public void save(AddressBook addressBookEntry) throws SQLException {
		Connection connection = DB.getConnection();

		PreparedStatement newAddressBookEntryStatement = connection.prepareStatement(
				"INSERT INTO addressbook (user_id, name, surname, phone, age, address, created_at, updated_at) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?) ");
		newAddressBookEntryStatement.setInt(1, (int) addressBookEntry.getUser_id());
		newAddressBookEntryStatement.setString(2, addressBookEntry.getName());
		newAddressBookEntryStatement.setString(3, addressBookEntry.getSurname());
		newAddressBookEntryStatement.setString(4, addressBookEntry.getPhone());
		newAddressBookEntryStatement.setInt(5, addressBookEntry.getAge());
		newAddressBookEntryStatement.setString(6, addressBookEntry.getAddress());

		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String strDate = dateFormat.format(date);
		newAddressBookEntryStatement.setString(7, strDate);
		newAddressBookEntryStatement.setString(8, null);
		newAddressBookEntryStatement.executeUpdate();

	}

	public void update(AddressBook addressBookEntry) throws SQLException {
		Connection connection = DB.getConnection();

		PreparedStatement updateAddressBookEntryStatement = connection.prepareStatement(
				"UPDATE addressbook SET name=?, surname=?, phone=?, age=?, address=?, updated_at=? WHERE id=?");
		updateAddressBookEntryStatement.setString(1, addressBookEntry.getName());
		updateAddressBookEntryStatement.setString(2, addressBookEntry.getSurname());
		updateAddressBookEntryStatement.setString(3, addressBookEntry.getPhone());
		updateAddressBookEntryStatement.setInt(4, addressBookEntry.getAge());
		updateAddressBookEntryStatement.setString(5, addressBookEntry.getAddress());

		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String strDate = dateFormat.format(date);
		updateAddressBookEntryStatement.setString(6, strDate);

		updateAddressBookEntryStatement.setLong(7, addressBookEntry.getId());
		updateAddressBookEntryStatement.executeUpdate();

	}

	public void deleteById(int Id) throws SQLException {
		Connection connection = DB.getConnection();

		PreparedStatement deleteAddressBookEntryStatement = connection
				.prepareStatement("DELETE FROM addressbook where id=?");
		deleteAddressBookEntryStatement.setInt(1, Id);
		deleteAddressBookEntryStatement.executeUpdate();

	}
}
