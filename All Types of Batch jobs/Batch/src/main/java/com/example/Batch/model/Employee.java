package com.example.Batch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {
	
	private long id;
	//@JsonProperty("frist_name")
	private String firstName;
	private String lastName;
	private String email;
	
	
}
