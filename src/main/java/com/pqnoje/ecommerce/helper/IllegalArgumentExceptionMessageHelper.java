package com.pqnoje.ecommerce.helper;

public class IllegalArgumentExceptionMessageHelper {
	
	public static void printStackMessageErrors(IllegalArgumentException exception) {
		System.out.println("IllegalArgumentException Reason: " + exception.getMessage());
	}

}
