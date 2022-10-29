package com.batch.sb110.batch;

import com.batch.sb110.domain.Dept;
import com.batch.sb110.domain.Two;
import com.batch.sb110.dto.TwoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.separator.SimpleRecordSeparatorPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CsvToJpaJob3 {

    private final ResourceLoader resourceLoader;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private int chunkSize = 10;

    @Bean
    public Job csvToJpaJob3_batchBuild(){
        return jobBuilderFactory.get("csvToJpaJob3")
                .start(csvToJpaJob3_batchStep1())
                .build();
    }

    @Bean
    public Step csvToJpaJob3_batchStep1(){
        return stepBuilderFactory.get("csvToJpaJob3_batchStep1")
                .<TwoDto, Two>chunk(chunkSize)
                .reader(csvToJpaJob3_Reader(null))
                .processor(csvToJpaJob3_processor())
                .writer(csvToJpaJob3_dbItemWriter())
                .build();
    }

    @Bean
    private ItemProcessor<TwoDto, Two> csvToJpaJob3_processor() {
        return twoDto ->
            new Two(twoDto.getOne(), twoDto.getTwo());
    }

    @Bean
    public JpaItemWriter<Two> csvToJpaJob3_dbItemWriter(){
        JpaItemWriter<Two> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<TwoDto> csvToJpaJob3_Reader(@Value("#{jobParameters[inFileName]}") String inFileName){
        return new FlatFileItemReaderBuilder<>()
                .name("csvToJpaJob3_Reader")
                .resource(new FileSystemResource(inFileName))
                .delimited().delimiter(":")
                .names("one","two")
                .targetType(TwoDto.class)
                .recordSeparatorPolicy()
                .build();
    }
}
