package com.example.demo.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class SimpleJobConfiguration {

    @Bean
    public Job simpleJob(JobRepository jobRepository, Step simpleStep1) {
        return new JobBuilder("simpleJob", jobRepository)
                .start(simpleStep1)
                .build()
                ;
    }

    @Bean
    public Step simpleStep1(JobRepository jobRepository, Tasklet simpleTasklet1, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("simpleStep1", jobRepository)
                .tasklet(simpleTasklet1, platformTransactionManager)
                .build()
                ;

    }

    @Bean
    public Tasklet simpleTasklet1() {
        return ((contribution, chunkContext) -> {
            log.info(">>>> this is simpleStep1");
            return RepeatStatus.FINISHED;
        });
    }
}
