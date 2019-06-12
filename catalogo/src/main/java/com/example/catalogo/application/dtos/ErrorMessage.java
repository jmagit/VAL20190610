package com.example.catalogo.application.dtos;

import java.io.Serializable;

public class ErrorMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	private String error, message;

	public ErrorMessage(String error, String message) {
		this.error = error;
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
