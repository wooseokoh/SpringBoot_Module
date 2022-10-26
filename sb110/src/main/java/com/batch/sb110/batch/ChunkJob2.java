package com.batch.sb110.batch;

import com.batch.sb110.domain.Dept;
import com.batch.sb110.domain.Dept2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ChunkJob2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private int chunkSize = 10;

    @Bean
    public Job chunkJob2_batchBuild(){
        return jobBuilderFactory.get("chunkJob2")
                .start(chunkJob2_step1()).build();
    }

    @Bean
    public Step chunkJob2_step1(){
        return stepBuilderFactory.get("chunkJob2_step1")
                .<Dept, Dept2>chunk(chunkSize)
                .reader(chunkJob2_dbItemReader())
                .processor(chunkJob2_processor())
                .writer(chunkJob2_printItemWriter())
                .build();
    }

    private ItemProcessor<Dept, Dept2> chunkJob2_processor() {
        return dept -> {
            return new Dept2(dept.getDeptNo(), "NEW_" + dept.getDName(), "NEW_" + dept.getLoc());
        };
    }

    @Bean
    public JpaPagingItemReader<Dept> chunkJob2_dbItemReader(){
        return new JpaPagingItemReaderBuilder<Dept>()
                .name("chunkJob2_dbItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT d FROM Dept d order by dept_no asc")
                .build();
    }

    @Bean
    public JpaItemWriter<Dept2> chunkJob2_printItemWriter(){
        JpaItemWriter<Dept2> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }
}
