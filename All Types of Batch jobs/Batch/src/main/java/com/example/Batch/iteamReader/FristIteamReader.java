package com.example.Batch.iteamReader;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
public class FristIteamReader implements ItemReader<Integer> {
	
	List<Integer> lists=Arrays.asList(1,2,3,4,5,6,7,8,9,10);
	int i=0;

	@Override
	public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub
		System.out.println("=============reader===================");

		if(i<lists.size()) {
			int item=lists.get(i);
			i++;
			return item;
		}
		i=0;
		
		return null;
	}

}
