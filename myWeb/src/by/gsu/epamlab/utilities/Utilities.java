package by.gsu.epamlab.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utilities {
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM");
	private static final DateTimeFormatter SLASH_DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	public static String getPasswordHash(String password) {
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// Add password bytes to digest
			md.update(password.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	// get DD.MM
	public String getTodayDate() {
		LocalDate today = LocalDate.now();
		return DATE_FORMAT.format(today);
	}

	// get DD.MM
	public String getTomorrowDate() {
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		return DATE_FORMAT.format(tomorrow);
	}

	// get MM/DD/YYYY
	public String getSlashTodayDate() {
		LocalDate today = LocalDate.now();
		return SLASH_DATE_FORMAT.format(today);
	}

	// get MM/DD/YYYY
	public String getSlashTomorrowDate() {
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		return SLASH_DATE_FORMAT.format(tomorrow);
	}

	// get YYYY-MM-DD
	public static Date getTomorrow() {
		DateTimeFormatter sqlDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		return Date.valueOf(sqlDateFormat.format(tomorrow));
	}	
}