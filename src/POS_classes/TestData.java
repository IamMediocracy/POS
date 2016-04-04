package POS_classes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import POS_classes.AccountValidation;

public class TestData {

	public static void insertTestData() {

		char[] pass = { 'p', 'a', 's', 's', 'w', 'o', 'r', 'd' };

		byte[] password = AccountValidation.toBytes(pass);
		System.out.print("Password in bytes before encryption: ");
		for (int i = 0; i < password.length; i++)
			System.out.print(password[i]);
		System.out.print("\n");

		byte[] salt = UserAccounts.generateSalt();
		byte[] salt2 = UserAccounts.generateSalt();
		byte[] salt3 = UserAccounts.generateSalt();

		byte[] salted = new byte[password.length + 32];
		byte[] salted2 = new byte[password.length + 32];
		byte[] salted3 = new byte[password.length + 32];

		System.out.println("salt as byte array: ");
		printArray(salt);
		System.out.println("salt2 as byte array: ");
		printArray(salt2);
		System.out.println("salt3 as byte array: ");
		printArray(salt3);

		for (int i = 0; i < password.length; i++) {
			salted[i] = password[i];
		}

		salted2 = salted;
		salted3 = salted;

		for (int i = 0; i < salt.length; i++) {
			salted[password.length + i] = salt[i];
		}

		byte[] encryp = UserAccounts.toEncrypt(salted);

		for (int i = 0; i < salt2.length; i++) {
			salted2[password.length + i] = salt2[i];
		}

		byte[] encryp2 = UserAccounts.toEncrypt(salted2);

		for (int i = 0; i < salt3.length; i++) {
			salted3[password.length + i] = salt3[i];
		}

		byte[] encryp3 = UserAccounts.toEncrypt(salted3);

		System.out.print("(bill) Password + Salt 1: ");
		printArray(salted);
		System.out.print("Password1 encrypted: ");
		printArray(encryp);

		System.out.println("(zack) Password + Salt 2: ");
		printArray(salted2);
		System.out.print("Password2 encrypted: ");
		printArray(encryp2);

		System.out.println("(william) Password + Salt 3: ");
		printArray(salted3);
		System.out.print("Password3 encrypted: ");
		printArray(encryp3);

		try {
			DB DB = new DB();
			PreparedStatement pstmt = DB.conn.prepareStatement(
					"INSERT INTO Users (usr_id, usr_password, usr_fname, usr_lname, usr_prm_warehouse, usr_prm_messages, "
							+ "usr_prm_wholesaleorders, usr_prm_workorders, usr_prm_products, usr_prm_shipments, usr_prm_plans,usr_prm_admin, usr_salt, usr_active)"
							+ "Values('bill',?,'Wilhelm','Grimm','1','1','1','1','1','1','1','1',?,'1');");
			// "UPDATE Users Set usr_password=?, usr_salt=? WHERE
			// usr_id='bill'");

			pstmt.setBytes(1, encryp);
			pstmt.setBytes(2, salt);
			pstmt.executeUpdate();
			DB.conn.commit();

			
			pstmt = DB.conn.prepareStatement(
					"INSERT INTO Users (usr_id,usr_password,usr_fname,usr_lname,usr_salt) Values('zack',?,'Zack','Heins',?)");
			// "UPDATE Users Set usr_password=?, usr_salt=? WHERE
			// usr_id='zack'");
			pstmt.setBytes(1, encryp2);
			pstmt.setBytes(2, salt2);
			pstmt.executeUpdate();
			DB.conn.commit();

			
			pstmt = DB.conn
					.prepareStatement("INSERT INTO Users (usr_id,usr_password,usr_fname,usr_lname,usr_salt,usr_active)"
							+ "Values('William',?,'William','Grove',?,'1')");
			// "UPDATE Users Set usr_password=?, usr_salt=? WHERE
			// usr_id='william'");
			pstmt.setBytes(1, encryp3);
			pstmt.setBytes(2, salt3);
			pstmt.executeUpdate();
			DB.conn.commit();
			DB.conn.close();
			DB.closeDB();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return;
	}

	public static void printArray(byte[] array) {
		for (int i = 0; i < array.length; i++)
			System.out.print(array[i]);
		System.out.print("\n");
		return;
	}
	
}
