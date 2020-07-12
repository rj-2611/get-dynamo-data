package com.assignment.aws.lambdaFunction.model;

import java.util.ArrayList;
import java.util.List;

public class Response {
	List<Feedback> response = new ArrayList<>();

	public List<Feedback> getResponse() {
		return response;
	}

	public void setResponse(List<Feedback> response) {
		this.response = response;
	}
	
}
