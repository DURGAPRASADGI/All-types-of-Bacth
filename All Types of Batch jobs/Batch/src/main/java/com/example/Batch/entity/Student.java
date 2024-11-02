package com.example.Batch.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Student {
	@Id
	private long id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	private String email;
	@Column(name = "dept_id")
	private long deptId;
	@Column(name = "is_active")
	private String isActive;

}
