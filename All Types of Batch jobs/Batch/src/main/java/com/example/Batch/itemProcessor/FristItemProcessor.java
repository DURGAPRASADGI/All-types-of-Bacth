package com.example.Batch.itemProcessor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class FristItemProcessor implements ItemProcessor<Integer, Long>{

	@Override
	public Long process(Integer item) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("=============processor===================");

		return Long.valueOf(item);
	}

}
