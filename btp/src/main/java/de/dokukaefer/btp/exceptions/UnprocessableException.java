package de.dokukaefer.btp.exceptions;

public class UnprocessableException extends Throwable {

	private int code;
	
	public UnprocessableException() {
		this(500);
	}
	
	public UnprocessableException(int code) {
		this(code, "Error while processing the request", null);
	}
	
	public UnprocessableException(int code, String message) {
		this(code, message, null);
	}
	
	public UnprocessableException(int code, String message, Throwable throwable) {
		super(message, throwable);
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
