package com.filmrental.controllers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class JobLauncherController {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job importActorJob;

	@GetMapping(value = "executeJob")
	public String executeJob() throws Exception {

		jobLauncher.run(importActorJob,
				new JobParametersBuilder().addLong("timestamp", System.currentTimeMillis()).toJobParameters());

		return "launched";
	}
}
