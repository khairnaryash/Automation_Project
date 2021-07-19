package com.automation.Exceptions;

public class InvalidValueException extends RuntimeException{

	public InvalidValueException(String reason) {
		super(reason);
	}
}
