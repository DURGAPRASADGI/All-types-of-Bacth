package com.example.Batch.itemProcessor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.Batch.config.JsonWriter;
import com.example.Batch.config.Saleman;

@Component
public class JsonProcessor  implements ItemProcessor<Saleman, JsonWriter>{

	@Override
	public JsonWriter process(Saleman item) throws Exception {
		// TODO Auto-generated method stub
		JsonWriter jsonWriter=new JsonWriter();
		jsonWriter.setSalesId(item.getSalesId());
		jsonWriter.setName(item.getName());
		jsonWriter.setCity(item.getCity());
		jsonWriter.setCommission(item.getCommission());
		
		return jsonWriter;
	}
	

}
