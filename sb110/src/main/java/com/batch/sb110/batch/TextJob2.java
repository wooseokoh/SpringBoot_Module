package com.batch.sb110.batch;

import com.batch.sb110.custom.CustomPassThroughLineAggregator;
import com.batch.sb110.dto.OneDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TextJob2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private static final int chunkSize = 5;

    @Bean
    public Job textJob2_batchBuild(){
        return jobBuilderFactory.get("textJob2")
                .start(textJob2_batchStep1())
                .build();
    }

    @Bean
    public Step textJob2_batchStep1(){
        return stepBuilderFactory.get("textJob2_batchStep1")
                .<OneDto, OneDto>chunk(chunkSize)
                .reader(textJob2_fileReader())
                .writer(textJob2_fileWriter()) // 커스텀은 processor를 사용하는것이 정답.
                .build();
    }

    @Bean
    public FlatFileItemReader<OneDto> textJob2_fileReader(){
        FlatFileItemReader<OneDto> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("sample/textJob2_input.txt"));
        flatFileItemReader.setLineMapper((((line, lineNumber) -> new OneDto(lineNumber +"=="+ line))));
        return flatFileItemReader;
    }

    @Bean
    public FlatFileItemWriter textJob2_fileWriter(){
        return new FlatFileItemWriterBuilder<OneDto>()
                .name("textJob2_fileWriter")
                .resource(new FileSystemResource("output/textJob2_output.txt"))
                .lineAggregator(new CustomPassThroughLineAggregator<>())
                .build();
    }
}