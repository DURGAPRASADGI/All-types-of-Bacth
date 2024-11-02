package com.example.Batch.config;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FristStepListner implements StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		System.out.println("before step "+ stepExecution.getStepName());
		System.out.println(stepExecution.getExecutionContext());
		System.out.println(stepExecution.getJobExecution().getJobInstance().getJobName());
		System.out.println(stepExecution.getJobParameters());
		System.out.println("=====================================================");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		System.out.println("after step "+ stepExecution.getStepName());
		System.out.println(stepExecution.getExecutionContext());
		System.out.println(stepExecution.getJobExecution().getJobInstance().getJobName());
		System.out.println(stepExecution.getJobParameters());
		System.out.println("=====================================================");
		return StepExecutionListener.super.afterStep(stepExecution);
	}

}
