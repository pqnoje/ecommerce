package com.pqnoje.ecommerce.helper;

public class ExceptionMessageHelper {
	
	public static void printStackMessageErrors(Exception exception) {
		System.out.println("Reason: " + exception.getMessage());
	}

}
