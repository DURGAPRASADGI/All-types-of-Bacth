package com.example.Batch.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

//@Service
public class SecoundBatchService {
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Qualifier("secoundJob")
	@Autowired
	Job secoundJob;
	
//	@Scheduled(cron = "0 0/1 * 1/1 * ?")
	public void startSecoundJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobParameters jobParameters=new JobParametersBuilder()
				                          .addLong("currentTime", System.currentTimeMillis())
				                          .toJobParameters();
		JobExecution jobExecution=jobLauncher.run(secoundJob, jobParameters);
		
		System.out.println(jobExecution.getId());
		
		
		
		
	}

}
