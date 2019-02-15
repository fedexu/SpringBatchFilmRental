package com.filmrental.batch;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.filmrental.data.entity.Actor;
import com.mongodb.MongoClient;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfiguration.class);

	
	// READER
	@Bean
    public FlatFileItemReader<Actor> reader() {
		
		List<String> fieldsNames = new ArrayList<>();
		fieldsNames.add("actorId");
		fieldsNames.add("firstName");
		fieldsNames.add("lastName");
		fieldsNames.add("lastUpdate");
		
		BeanWrapperFieldSetMapper<Actor> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Actor.class);						// a mapper to automatically make the 
		fieldSetMapper.setDistanceLimit(3);								// corrispondance with the entity by fields name
		
        return new FlatFileItemReaderBuilder<Actor>()
            .name("ActorItemReader") 									// reader name
            .resource(new ClassPathResource("sample-data.csv"))			// source of the data (file)
            .delimited()												// how data is divided in the file
            .names(fieldsNames.toArray(new String[0]))				    // how data is composed
            .fieldSetMapper(fieldSetMapper)								// how data is mapped
            .build();
    }
	
	// PROCESSOR
    @Bean
    public ActorItemProcessor processor() {
        return new ActorItemProcessor();								// a processor defines data transformation		
    }
    
    //WRITER
    @Bean
    public ItemWriter<Actor> writer() {
        MongoItemWriter<Actor> writer = new MongoItemWriter<>();		// a wroter to the Mongo database
        try {
            writer.setTemplate(mongoTemplate());
            writer.setCollection("actor");
        } catch (Exception e) {
        	LOGGER.error("Error", e);
        }
        return writer;
    }
    
    // DEFINE THE JOB
    @Bean
    public Job importActorJob(JobBuilderFactory jobBuilderFactory, Step step1, JobExecutionListener listener) {
        return jobBuilderFactory.get("importActorJob")
            .incrementer(new RunIdIncrementer())						// an incrementer is used to maintain execution state
            .listener(listener)											// the end of the step is signaled using this notifier
            .flow(step1)												// defines a flow of execution that is composed of only the step1
            //.next(step1)												// adding steps just means listing them
            .end()														// closes the flow
            .build();													
    }
    
    // DEFINE THE STEP
    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Actor> reader,
            ItemWriter<Actor> writer, ItemProcessor<Actor, Actor> processor) {

    	return stepBuilderFactory.get("step1")						
            .<Actor, Actor> chunk(10)									// data will be processed 10 chunks at a time
            .reader(reader)												// first the reader reader() will be executed
            .processor(processor)										// data will be processed by the processor()
            .writer(writer)											    // and written on db by our writer bean
            .build(); 
    }
    
    // DB UTILITIES
    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(), "FilmRental");
    }

    @Bean
    public MongoOperations mongoTemplate() throws Exception {
    	
        return new MongoTemplate(mongoDbFactory());
    }

}
