package com.lawencon.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author lawencon05
 */
public class DateUtil {

	public static LocalDateTime stringToLocalDateTime(String dateTimeStr) {
		return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_DATE_TIME);
	}

	public static LocalDate stringToLocalDate(String dateStr) {
		return LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
	}

	public static String localDateTimeToString(LocalDateTime dateTime) {
		return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	public static String localDateToString(LocalDate date) {
		return date.format(DateTimeFormatter.ISO_DATE);
	}
}
