package com.filmrental.scheduled;

import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
	
	 private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
	 private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	 @Autowired
		JobLauncher jobLauncher;
	 
	 @Autowired
		Job importActorJob;
	 	
	 @Scheduled(fixedDelay = 10000) 
	 public String executeJob() throws Exception {

			jobLauncher.run(importActorJob,
					new JobParametersBuilder().addLong("timestamp", System.currentTimeMillis()).toJobParameters());

			return "launched";
		}
}
