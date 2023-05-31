package com.example.springbatchexample.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SimpleJob implements Job {
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Hello from SimpleJob!");
	}
}
