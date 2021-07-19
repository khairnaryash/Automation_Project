package com.automation.Exceptions;

import org.openqa.selenium.NoSuchElementException;

public class ElementNotFound extends NoSuchElementException{

	public ElementNotFound(String reason) {
		super(reason);
	}

}
