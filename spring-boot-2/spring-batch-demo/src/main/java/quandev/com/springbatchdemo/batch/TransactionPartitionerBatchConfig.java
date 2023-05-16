package quandev.com.springbatchdemo.batch;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.StepExecutionAggregator;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import quandev.com.springbatchdemo.dto.TransactionDto;
import quandev.com.springbatchdemo.dto.TransactionEntity;
import quandev.com.springbatchdemo.job.chunks.TransactionProcessorChunk;
import quandev.com.springbatchdemo.job.chunks.TransactionReaderChunk;
import quandev.com.springbatchdemo.job.chunks.TransactionWriterChunk;
import quandev.com.springbatchdemo.job.partrion.ColumnRangePartitioner;
import quandev.com.springbatchdemo.repo.TransactionRepo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Slf4j
@Configuration
public class TransactionPartitionerBatchConfig {


    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    TransactionProcessorChunk transactionProcessorChunk;

    @Autowired
    TransactionReaderChunk transactionReaderChunk;

    @Autowired
    TransactionWriterChunk transactionWriterChunk;

    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    FlatFileItemReader<TransactionDto> csvFileReader;

    @Autowired
    TaskExecutor batchTaskExecutor;


    @Bean
    public ColumnRangePartitioner partitioner() {
        return new ColumnRangePartitioner();
    }

    @Bean
    public PartitionHandler partitionHandler() {
        TaskExecutorPartitionHandler taskExecutorPartitionHandler = new TaskExecutorPartitionHandler();
        taskExecutorPartitionHandler.setGridSize(4);
        taskExecutorPartitionHandler.setTaskExecutor(batchTaskExecutor);
        taskExecutorPartitionHandler.setStep(slaveStep());
        return taskExecutorPartitionHandler;
    }

    @Bean
    public Step slaveStep() {
        return stepBuilderFactory.get("slaveStep").<TransactionDto, TransactionEntity>chunk(10)
                .reader(csvFileReader)
                .processor(transactionProcessorChunk)
                .writer(transactionWriterChunk)
                .build();
    }

    @Bean
    public Step masterStep() {
        return stepBuilderFactory.get("masterSTep").
                partitioner(slaveStep().getName(), partitioner())
                .partitionHandler(partitionHandler())
                .aggregator(new StepExecutionAggregator() {
                    @SneakyThrows
                    @Override
                    public void aggregate(StepExecution stepExecution, Collection<StepExecution> collection) {
                        log.info("stepExecution: " + stepExecution.toString());
                        log.info("collection size: " + collection.size());
                        collection.forEach(item -> {
                            log.info(stepExecution.toString());
                        });

                        log.info("Aggregate transaction");


                        long sum = transactionRepo.sumAmountAllTransaction();

                        String csvFile = "temp/transactionReport.csv";
                        BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
                        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
                        csvPrinter.printRecord(transactionRepo.count(), sum, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                        csvPrinter.flush();
                        writer.close();


                    }
                })
                .build();
    }

    @Bean
    public Job partitionJob() {
        return jobBuilderFactory.get("partitionJob")
                .flow(masterStep()).end().build();

    }


}
