package com.example.Batch.config;


import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlRootElement(name =  "salesman")
public class Saleman {
	//@JsonProperty("sales_Id")
//	@XmlElement(name = "sales_Id")
	private long salesId;
	private String name;
	private String city;
	private String commission;

}
