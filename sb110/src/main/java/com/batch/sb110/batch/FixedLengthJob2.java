package com.batch.sb110.batch;

import com.batch.sb110.custom.CustomBeanWrapperFieldExtractor;
import com.batch.sb110.dto.TwoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FixedLengthJob2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private static final int chunkSize = 5;

    @Bean
    public Job fixedLengthJob2_batchBuild(){
        return jobBuilderFactory.get("fixedLengthJob2")
                .start(fixedLengthJob2_batchStep1())
                .build();
    }

    @Bean
    public Step fixedLengthJob2_batchStep1(){
        return stepBuilderFactory.get("fixedLengthJob2_batchStep1")
                .<TwoDto, TwoDto>chunk(chunkSize)
                .reader(fixedLengthJob2_fileReader())
                .writer(fixedLengthJob2_fileWriter(new FileSystemResource("output/fixedLengthJob2_output.xtx")))
                .build();
    }

    @Bean
    public FlatFileItemReader<TwoDto> fixedLengthJob2_fileReader(){
        FlatFileItemReader<TwoDto> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("sample/fixedLengthJob2_input.txt"));
        flatFileItemReader.setLinesToSkip(1);

        DefaultLineMapper<TwoDto> dtoDefaultLineMapper = new DefaultLineMapper<>();

        FixedLengthTokenizer fixedLengthTokenizer = new FixedLengthTokenizer();
        fixedLengthTokenizer.setNames("one", "two");
        fixedLengthTokenizer.setColumns(new Range(1,5), new Range(6,10));

        BeanWrapperFieldSetMapper<TwoDto> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(TwoDto.class);

        dtoDefaultLineMapper.setLineTokenizer(fixedLengthTokenizer);
        dtoDefaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
        flatFileItemReader.setLineMapper(dtoDefaultLineMapper);

        return flatFileItemReader;
    }

    @Bean
    public FlatFileItemWriter<TwoDto> fixedLengthJob2_fileWriter(Resource resource){
        BeanWrapperFieldExtractor<TwoDto> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"one", "two"});
        fieldExtractor.afterPropertiesSet();

        FormatterLineAggregator<TwoDto> lineAggregator = new FormatterLineAggregator<>();
        lineAggregator.setFormat("%-5s###%5s");
        lineAggregator.setFieldExtractor(fieldExtractor);

        return new FlatFileItemWriterBuilder<TwoDto>().name("fixedLengthJob2_fileWriter")
                .resource(resource)
                .lineAggregator(lineAggregator)
                .build();
    }
}
