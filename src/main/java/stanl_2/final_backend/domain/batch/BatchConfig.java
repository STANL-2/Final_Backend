package stanl_2.final_backend.domain.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    
    private final String JOB_NAME = "checkJob";
    private final String STEP_NAME = "checkStep";


    @Bean
    public Job checkJob(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new JobBuilder(JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(checkStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    @JobScope
    public Step checkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder(STEP_NAME, jobRepository)
                .<SalesHistory,SalesStatistics>chunk(5,transactionManager)
                .reqder(checkReader())
                .processor(checkProcessor())
                .writer(checkWriter())
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemWriter<SalesStatistics> checkWtriter(){
        return new RepositoryItemWriter<SalesStatistics>()
                .repository(SalesStatisticsRepository)
                .methodName("save")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<SalesHistory, SalesStatistics> checkProcessor(){
        return new ItemProcessor<SalesHistory, SalesStatistics>() {
            @Override
            public SalesStatistics process(SalesHistory item) throws Exception {
                return new SalesStatistics(item);
            }
        };
    }

    @Bean
    @StepScope
    public RepositoryItemReader<SalesHistory> checkReader(){
        return new RepositoryItemReaderBuilder<SalesHistory>()
                .name("checkReader")
                .repository(SalesStatisticsRepository)
                .methodName("findAll")
                .pageSize(5)
                .arguments(List.of())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

//    @Bean
//    @StepScope
//    public Tasklet checkTasklet(){
//        return new Tasklet(){
//            @Override
//            public RepeatStatus execute(StepContribution contribution, ChunkContext context) throws Exception {
//                log.info("Spring batch check Suceess");
//                // 원하는 비지니스 모델 추가
//                return RepeatStatus.FINISHED;
//            }
//        };
//    }
}
