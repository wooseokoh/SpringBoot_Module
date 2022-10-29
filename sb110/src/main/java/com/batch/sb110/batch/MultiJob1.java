package com.batch.sb110.batch;

import com.batch.sb110.dto.TwoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.separator.SimpleRecordSeparatorPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MultiJob1 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private static final int chunkSize = 5;

    @Bean
    public Job multiJob1_batchBuild(){
        return jobBuilderFactory.get("multiJob1")
                .start(multiJob1_batchStep1(null))
                .build();
    }

    @Bean
    @JobScope
    public Step multiJob1_batchStep1(@Value("#{jobParameters[version]}") String version){
        log.debug("version" + version);
        return stepBuilderFactory.get("multiJob1_batchStep1")
                .<TwoDto, TwoDto>chunk(chunkSize)
                .reader(multiJob1_Reader(null))
                .processor(multiJob1_processor(null))
                .writer(multiJob1_writer(null))
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<TwoDto> multiJob1_writer(@Value("#{jobParameters[outFileName]}") String outFileName) {
        return new FlatFileItemWriterBuilder<TwoDto>()
                .name("multiJob1_writer")
                .resource(new FileSystemResource("sample/" + outFileName))
                .lineAggregator(item -> {
                    return item.getOne() + " --- " + item.getTwo();
                }).build();
    }

    @Bean
    @StepScope
    public ItemProcessor<TwoDto, TwoDto> multiJob1_processor(@Value("#{jobParameters[version]}") String version) {
        log.debug("version" + version);
        return twoDto -> new TwoDto(twoDto.getOne(), twoDto.getTwo());
    }

    @Bean
    @StepScope
    public FlatFileItemReader<TwoDto> multiJob1_Reader(@Value("#{jobParameters[inFileName]}") String inFileName){
        return new FlatFileItemReaderBuilder<TwoDto>()
                .name("multiJob1_Reader")
                .resource(new ClassPathResource("sample/" + inFileName))
                .delimited().delimiter(":")
                .names("one","two")
                .targetType(TwoDto.class)
                .recordSeparatorPolicy(new SimpleRecordSeparatorPolicy() {
                    @Override
                    public String postProcess(String record) {
                        return record.trim();
                    }
                }).build();
    }
}
