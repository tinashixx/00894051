package com.example.springbatchexample.trigger;

import javax.sql.DataSource;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.springbatchexample.job.SimpleJob;

@Configuration
public class QuartzConfig {

	@Bean
	public JobDetail simpleJobDetail() {
		return JobBuilder.newJob(SimpleJob.class).withIdentity("simpleJob").storeDurably().build();
	}

	@Bean
	public Trigger simpleJobTrigger() {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5)
				.repeatForever();

		return TriggerBuilder.newTrigger().forJob(simpleJobDetail()).withIdentity("simpleTrigger")
				.withSchedule(scheduleBuilder).build();
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
