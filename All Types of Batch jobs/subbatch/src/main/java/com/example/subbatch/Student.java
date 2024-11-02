package com.example.subbatch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
	private long id;
	private String firstName;
	private String lastName;
	private String email;

}
