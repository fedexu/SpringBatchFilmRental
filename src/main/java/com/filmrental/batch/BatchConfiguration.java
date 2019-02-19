package com.filmrental.batch;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.filmrental.batch.mappers.ActorMapper;
import com.filmrental.batch.processors.ActorItemProcessor;
import com.filmrental.data.entity.Actor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfiguration.class);

	// READER
	@Bean
    public FlatFileItemReader<Actor> reader() {
		
		LOGGER.info("Starting item reader from file");
		
		List<String> fieldsNames = new ArrayList<>();					
		fieldsNames.add("firstName");
		fieldsNames.add("lastName");
		fieldsNames.add("lastUpdate");
		
		FlatFileItemReader<Actor> reader = new FlatFileItemReader<>();	 // an item reader reads lines that are then fed to a line mapper that
		reader.setResource(new ClassPathResource("sample-data.csv"));	 // maps the content of the file on the
	    																 // destination entity using a tokenizer, that splits the
	    DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(); // lines (default with comma) and associates the entity fields
	    tokenizer.setNames(fieldsNames.toArray(new String[0]));			 // by position	
	    
	    DefaultLineMapper<Actor> lineMapper = new DefaultLineMapper<>();
	    lineMapper.setFieldSetMapper(new ActorMapper());	    
	    lineMapper.setLineTokenizer(tokenizer);
	    reader.setLineMapper(lineMapper);
	    
	    return reader;
    }
	
	
	// PROCESSOR
    @Bean
    public ActorItemProcessor processor() {
    	
    	LOGGER.info("Starting item processor");
        
    	return new ActorItemProcessor();								 // a processor class that defines data transformation		
    }
    
    //WRITER
    @Bean
    public JpaItemWriter<Actor> writer(EntityManagerFactory entityManagerFactory) {
    	
    	LOGGER.info("Starting item writer");                                                               
    	
    	JpaItemWriter<Actor> writer = new JpaItemWriter<>();			 // item writer to output data, in this case the destination
    	writer.setEntityManagerFactory(entityManagerFactory);			 // is a mysql database, entities are persisted with jpa
        return writer;
    }
    
    // DEFINE A JOB
    @Bean
    public Job importActorJob(JobBuilderFactory jobBuilderFactory, Step step1, JobExecutionListener listener) {
        return jobBuilderFactory.get("importActorJob")
            .incrementer(new RunIdIncrementer())						 // an incrementer is used to maintain execution state
            .listener(listener)											 // the end of the step is signaled using this notifier
            .flow(step1)												 // defines a flow of execution that is composed of only the step1
            //.next(step1)												 // adding steps just means listing them
            .end()														 // closes the flow
            .build();													  
    }
    
    // DEFINE A STEP
    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Actor> reader,
            ItemWriter<Actor> writer, ItemProcessor<Actor, Actor> processor) {

    	return stepBuilderFactory.get("step1")						
            .<Actor, Actor> chunk(10)									 // data will be processed 10 chunks at a time
            .reader(reader)												 // first the reader reader() will be executed
            .processor(processor)										 // data will be processed by the processor()
            .writer(writer)											     // and written on db by our writer bean
            .build(); 
    }
}
