package com.ibm.bluemix.demo;

public class MessageResponse {
	private String userInput;
	private String message;
	private String classLabel;
	private String confidence;

	public String getUserInput() {
		return userInput;
	}

	public void setUserInput(String userInput) {
		this.userInput = userInput;
	}

	public String getMessage() {
		return message;
	}

	public String getClassLabel() {
		return classLabel;
	}

	public void setClassLabel(String classLabel) {
		this.classLabel = classLabel;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getConfidence() {
		return confidence;
	}

	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}

}
