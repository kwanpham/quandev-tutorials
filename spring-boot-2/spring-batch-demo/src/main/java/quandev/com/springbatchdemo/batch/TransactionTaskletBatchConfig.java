package quandev.com.springbatchdemo.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quandev.com.springbatchdemo.job.tasklet.LinesProcessor;
import quandev.com.springbatchdemo.job.tasklet.LinesReader;
import quandev.com.springbatchdemo.job.tasklet.LinesWriter;

@Configuration
@EnableBatchProcessing
public class TransactionTaskletBatchConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    LinesProcessor linesProcessor;

    @Autowired
    LinesWriter linesWriter;



    @Bean
    protected Step readTransactionLines() {
        return steps
                .get("readLines")
                .tasklet((stepContribution, chunkContext) -> null)
                .build();
    }

    @Bean
    protected Step processLines() {
        return steps
                .get("processLines")
                .tasklet(linesProcessor)
                .build();
    }

    @Bean
    protected Step writeLines() {
        return steps
                .get("writeLines")
                .tasklet(linesWriter)
                .build();
    }

    @Bean
    public Job jobTasklet() {
        return jobs
                .get("taskletsJob")
                .start(readTransactionLines())
                .next(processLines())
                .next(writeLines())
                .build();
    }

}