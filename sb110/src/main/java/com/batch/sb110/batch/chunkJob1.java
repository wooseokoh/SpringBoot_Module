package com.batch.sb110.batch;

import com.batch.sb110.domain.Dept;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class chunkJob1 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private int chunkSize = 10;

    @Bean
    public Job chunkJob1_batchBuild(){
        return jobBuilderFactory.get("chunkJob1")
                .start(chunkJob1_step1()).build();
    }

    @Bean
    public Step chunkJob1_step1(){
        return stepBuilderFactory.get("chunkJob1_step1")
                .<Dept, Dept>chunk(chunkSize)
                .reader(chunkJob1_dbItemReader())
                .writer(chunkJob1_printItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Dept> chunkJob1_dbItemReader(){
        return new JpaPagingItemReaderBuilder<Dept>()
                .name("chunkJob1_dbItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT d FROM Dept d order by dept_no asc")
                .build();
    }

    @Bean
    public ItemWriter<Dept> chunkJob1_printItemWriter(){
        return list -> {
            for (Dept dept: list){
                log.debug(dept.toString());
            }
        };
    }
}
