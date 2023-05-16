package quandev.com.springbatchdemo.job.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quandev.com.springbatchdemo.repo.TransactionRepo;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class LinesWriter implements Tasklet, StepExecutionListener {
    


    @Autowired
    private TransactionRepo transactionRepo;


    @Override
    public void beforeStep(StepExecution stepExecution) {

        log.debug("Lines Writer initialized.");
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws IOException {

        long sum = transactionRepo.sumAmountAllTransaction();

        String csvFile = "temp/transactionReport.csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

        csvPrinter.printRecord(transactionRepo.count() , sum , LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        csvPrinter.flush();
        writer.close();



        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.debug("Lines Writer ended.");
        return ExitStatus.COMPLETED;
    }
}