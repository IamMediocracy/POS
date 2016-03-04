package POS_classes;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Random;

public class AccountValidation{
	
	// convert char[] to byte[] for security purposes
	protected byte[] toBytes(char[] chars) {
		CharBuffer charBuffer = CharBuffer.wrap(chars);
		ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
		byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
		Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
		Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
		return bytes;
	}

	protected byte[] toEncrypt(byte[] password){
		
		byte[] digested = null;
		
		try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.reset();
	        digested = md.digest(password);
		} catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
		
	        return digested;
	}
	
	protected boolean compareLogin(String username, byte[] usrEnteredPassword) {
		DB DB = new DB();
		Statement stmt;
		String sql = "SELECT usr_active FROM Users WHERE usr_id = '" + username + "';";
		
		try {
			stmt = DB.conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			if(rs.getBoolean("usr_active")==false){
				System.out.println("login failed");
				stmt.close();
				return false;
			}
			sql = "SELECT usr_password FROM Users WHERE usr_id = '" + username + "';";
			rs = stmt.executeQuery(sql);
			rs.next();
			byte[] pass = rs.getBytes("usr_password");
			if (Arrays.equals(usrEnteredPassword, pass)) {
				for (int i = 0; i < usrEnteredPassword.length; i++)
					System.out.print(usrEnteredPassword[i]);
				System.out.print("\n");
				for (int i = 0; i < pass.length; i++)
					System.out.print(pass[i]);
				System.out.print("\n");
				System.out.println("Good to go");
				DB.closeDB();
				return true;
			} else {
				for (int i = 0; i < usrEnteredPassword.length; i++)
					System.out.print(usrEnteredPassword[i]);
				System.out.print("\n");
				for (int i = 0; i < pass.length; i++)
					System.out.print(pass[i]);
				System.out.print("\n");
				System.out.println("No way Jose");
				DB.closeDB();
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	protected byte[] toSalt(String username, byte[] password) {
		DB DB = new DB();
		Statement stmt;
		String sql = "SELECT usr_salt FROM Users WHERE usr_id = '" + username + "';";

		System.out.println("In toSalt");

		byte[] salt = null;

		try {
			stmt = DB.conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			salt = rs.getBytes(1);
			System.out.print("Salt used on entered pass: ");

			for (int i = 0; i < salt.length; i++) {
				System.out.print(salt[i]);
			}
			System.out.print("\n");
			DB.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] salted = new byte[password.length + salt.length];

		for (int i = 0; i < password.length; i++) {
			salted[i] = password[i];
		}

		for (int i = 0; i < salt.length; i++) {
			salted[password.length + i] = salt[i];
		}

		System.out.print("Entered Password salted: ");
		for (int i = 0; i < salted.length; i++)
			System.out.print(salted[i]);
		System.out.print("\n");

		return salted;
	}

	protected byte[] generateSalt() {
		final Random r = new SecureRandom();
		byte[] salt = new byte[32];
		r.nextBytes(salt);
		return (salt);
	}

}
