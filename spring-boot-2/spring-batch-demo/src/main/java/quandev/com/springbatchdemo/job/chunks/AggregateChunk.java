package quandev.com.springbatchdemo.job.chunks;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quandev.com.springbatchdemo.repo.TransactionRepo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class AggregateChunk implements Tasklet {

    @Autowired
    TransactionRepo transactionRepo;


    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.info("Aggregate transaction");
//        Thread.sleep(50000);
        long sum = transactionRepo.sumAmountAllTransaction();

        String csvFile = "temp/transactionReport.csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

        csvPrinter.printRecord(transactionRepo.count(), sum, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        csvPrinter.flush();
        writer.close();
        return RepeatStatus.FINISHED;
    }
}
