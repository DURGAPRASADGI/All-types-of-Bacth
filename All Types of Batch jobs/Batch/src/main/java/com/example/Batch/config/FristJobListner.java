package com.example.Batch.config;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Service;

@Service

public class FristJobListner implements JobExecutionListener{

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		
		System.out.println("before job "+jobExecution.getJobInstance().getJobName());
		System.out.println(jobExecution.getJobParameters());
		System.out.println(jobExecution.getExecutionContext());
		jobExecution.getExecutionContext().put("name", "Durga");
		System.out.println("==========================================");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		System.out.println("after job "+jobExecution.getJobInstance().getJobName());
		System.out.println(jobExecution.getJobParameters());
		System.out.println(jobExecution.getExecutionContext());
		jobExecution.getExecutionContext().put("name", "Durga");
		System.out.println("==========================================");
	}

}
