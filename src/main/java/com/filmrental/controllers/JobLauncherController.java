package com.filmrental.controllers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobLauncherController {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job importActorJob;

	@RequestMapping(method = RequestMethod.GET, value = "/executeJob", produces = "application/json")
	public String executeJob() throws Exception {

		jobLauncher.run(importActorJob,
				new JobParametersBuilder().addLong("timestamp", System.currentTimeMillis()).toJobParameters());

		return "launched";
	}
	

}
