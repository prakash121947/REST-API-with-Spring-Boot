package com.example.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InQueryRequest {

	private List<String> firstNames;

	public List<String> getFirstNames() {
		return firstNames;
	}

	public void setFirstNames(List<String> firstNames) {
		this.firstNames = firstNames;
	}
	
	
}
