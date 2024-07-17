package com.pqnoje.ecommerce.helper;

import java.sql.SQLException;

public class SQLExceptionMessagerHelper {

	public static void printStackMessageErrors(SQLException sqlException) {
		System.out.println("Reason: " + sqlException.getMessage());
		System.out.println("Cause: " + sqlException.getCause());
		System.out.println("Error Code: " + sqlException.getErrorCode());
		System.out.println("SQL State: " + sqlException.getSQLState());
		System.out.println("Localized Message: " + sqlException.getLocalizedMessage());
	}

}
