package com.cnc.buddyup.common;

public class CFL {
	public static String Q(String input) {
		return input == null ? "''" : "'" + input.replace("'", "''") + "'";
	}
}
