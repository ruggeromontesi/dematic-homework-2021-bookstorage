package com.ruggero.bookstorage.entities.errorsandexception;

public class FieldErrorResponse {
	private String fieldName;
	public FieldErrorResponse(String fieldName, String errorMessage) {
		super();
		this.fieldName = fieldName;
		this.errorMessage = errorMessage;
	}
	private String errorMessage;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
