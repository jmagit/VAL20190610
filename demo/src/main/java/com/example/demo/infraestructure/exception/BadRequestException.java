package com.example.demo.infraestructure.exception;

public class BadRequestException extends Exception {
	private static final long serialVersionUID = 1L;

	public BadRequestException(String arg0) {
		super(arg0);
	}

	public BadRequestException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BadRequestException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
