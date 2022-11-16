package com.lawencon.community.util;
import java.util.Random;

public class GenerateCodeUtil {

		public static String generateCode() {
			final int leftLimit = 48;
			final int rightLimit = 122;
			final int targetStringLength = 6;
			final Random random = new Random();
			final String ticketNo = random.ints(leftLimit, rightLimit + 1)
					.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString()
					.toUpperCase();
			return ticketNo;
		}
	
}
