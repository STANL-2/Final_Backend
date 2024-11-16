package stanl_2.final_backend.domain.batch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job checkJob;

    @Autowired
    public BatchScheduler(JobLauncher jobLauncher, Job checkJob) {
        this.jobLauncher = jobLauncher;
        this.checkJob = checkJob;
    }


    @Scheduled(cron = "0 03 00 * * ?", zone = "Asia/Seoul")
    public void testJobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
                                    JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters = new JobParameters(
                Collections.singletonMap("requestTime", new JobParameter(System.currentTimeMillis(), Long.class))
        );

        jobLauncher.run(checkJob, jobParameters);
    }
}
