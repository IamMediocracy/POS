package POS_classes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TestData {

	public static void insertTestData() {


		for (int i = 0; i < 5; i++) {
			char[] pass = { '1','1','1','1' };
			byte[] password = AccountValidation.toBytes(pass);
			byte[] salt = AccountValidation.generateSalt();
			byte[] salted = new byte[password.length + 32];

			for (int j = 0; j < password.length; j++) {
				salted[j] = password[j];
			}

			for (int j = 0; j < salt.length; j++) {
				salted[password.length + j] = salt[j];
			}

			byte[] encryp = AccountValidation.toEncrypt(salted);

			DB DB;
			try {
				DB = new DB();
				int user=0;
				switch(i){
				case 0:
					user = 1000;
					break;
				case 1:
					user = 1001;
					break;
				case 2:
					user = 1002;
					break;
				case 3:
					user = 1003;
					break;
				case 4:
					user = 1004;
					break;
				case 5:
					user = 1005;
					break;
				}
				String sql = "UPDATE user Set usr_password=?, usr_salt=? WHERE usr_id=?;";
				PreparedStatement pstmt = DB.conn.prepareStatement(sql);
				pstmt.setBytes(1,encryp);
				pstmt.setBytes(2,salt);
				pstmt.setInt(3, user);
				pstmt.executeUpdate();
//				DB.conn.commit();
				DB.closeDB();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static void printArray(byte[] array) {
		for (int i = 0; i < array.length; i++)
			System.out.print(array[i]);
		System.out.print("\n");
		return;
	}

}
