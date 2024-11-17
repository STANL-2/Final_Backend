package stanl_2.final_backend.domain.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import stanl_2.final_backend.domain.schedule.command.domain.aggregate.entity.Schedule;

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
                .<>chunk(5,transactionManager)
                .reqder(checkReader())
                .processor(checkProcessor())
                .writer(checkWriter())
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemWriter<> checkWtriter(){
        return new RepositoryItemWriter<>()
                .repository
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
