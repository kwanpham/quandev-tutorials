package quandev.com.springbatchdemo.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import quandev.com.springbatchdemo.dto.TransactionDto;
import quandev.com.springbatchdemo.dto.TransactionEntity;
import quandev.com.springbatchdemo.job.chunks.AggregateChunk;
import quandev.com.springbatchdemo.job.chunks.TransactionProcessorChunk;
import quandev.com.springbatchdemo.job.chunks.TransactionReaderChunk;
import quandev.com.springbatchdemo.job.chunks.TransactionWriterChunk;

import java.util.concurrent.Future;


@Configuration
public class TransactionChunkBatchConfig {


    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    TransactionProcessorChunk transactionProcessorChunk;

    @Autowired
    TransactionReaderChunk transactionReaderChunk;

    @Autowired
    TransactionWriterChunk transactionWriterChunk;

    @Autowired
    AggregateChunk aggregateChunk;

    @Autowired
    FlatFileItemReader<TransactionDto> csvFileReader;

    @Autowired
    TaskExecutor batchTaskExecutor;

    @Bean
    public AsyncItemProcessor<TransactionDto, TransactionEntity> asyncTransactionProcessorChunk() {
        AsyncItemProcessor<TransactionDto, TransactionEntity> asyncItemProcessor = new AsyncItemProcessor<>();
        asyncItemProcessor.setDelegate(transactionProcessorChunk);
        asyncItemProcessor.setTaskExecutor(batchTaskExecutor);

        return asyncItemProcessor;
    }

    @Bean
    public AsyncItemWriter<TransactionEntity> asyncTransactionWriterChunk() {
        AsyncItemWriter<TransactionEntity> asyncItemWriter = new AsyncItemWriter<>();
        asyncItemWriter.setDelegate(transactionWriterChunk);
        return asyncItemWriter;
    }

    @Bean
    protected Step processReadAndSaveDb() {
        return steps.get("processReadAndSaveDb")
                .<TransactionDto, TransactionEntity>chunk(10)
                .reader(csvFileReader)
                .processor(transactionProcessorChunk)
                .writer(transactionWriterChunk)
                .taskExecutor(batchTaskExecutor)
                .build();
    }

    @Bean
    protected Step processAggregate() {
        return steps.get("processAggregate")
                .tasklet(aggregateChunk)
                .build();
    }

    @Bean
    public Step asyncProcessReadAndSaveDb() {
        return steps
                .get("Asynchronous Processing : Read -> Process -> Write ")
                .<TransactionDto, Future<TransactionEntity>>chunk(10)
                .reader(csvFileReader)
                .processor(asyncTransactionProcessorChunk())
                .writer(asyncTransactionWriterChunk())
                .build();
    }

    @Bean
    public Job jobChunk() {
        return jobs
                .get("chunksJob")
                .incrementer(new RunIdIncrementer())
                .start(asyncProcessReadAndSaveDb())
                .next(processAggregate())
                .build();
    }


}
