package com.example.Batch.itemWriter;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.example.Batch.config.Saleman;

@Component
public class SalesWritter implements ItemWriter<Saleman>{

	@Override
	public void write(Chunk<? extends Saleman> chunk) throws Exception {
		// TODO Auto-generated method stub
		for(Saleman sa:chunk) {
			System.out.println(sa);
		}
	}

}
