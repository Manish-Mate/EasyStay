package com.book.exception;

import org.springframework.transaction.InvalidIsolationLevelException;

public class InvalidBookingRequestException extends RuntimeException {

	public InvalidBookingRequestException(String msg) {
		super(msg);
	}
}
