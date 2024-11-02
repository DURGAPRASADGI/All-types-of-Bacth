package com.example.Batch.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JsonWriter {
	private long salesId;
	private String name;
	private String city;
	private String commission;

}
