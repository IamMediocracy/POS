package POS_classes;

import java.sql.SQLException;
import java.sql.Statement;

public class TestData {

	public static void insertTestData() {
		
		int[] userID = {1001,1002,1003,1004,1005};
		
		for (int i=1;i <= userID.length;i++){
			char[] pass = {'1','0','0',(char) i};
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
			String sql = "UPDATE user Set usr_password='"+encryp+"', usr_salt='"+salt+"' WHERE usr_id='"+userID[i-1]+"';";
			Statement stmt = DB.conn.createStatement();
			stmt.executeQuery(sql);
			DB.conn.commit();
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
