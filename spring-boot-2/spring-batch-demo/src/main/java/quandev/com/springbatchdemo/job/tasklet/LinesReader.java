package quandev.com.springbatchdemo.job.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import quandev.com.springbatchdemo.dto.TransactionDto;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LinesReader implements Tasklet, StepExecutionListener {
    

    private List<TransactionDto> lines;

    String filePath;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        lines = new ArrayList<>();
        filePath = "temp/transaction.csv";
        log.info("Lines Reader initialized.");
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws IOException {
        Reader reader = new FileReader(filePath);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();
        for (CSVRecord csvRecord : csvRecords) {
            TransactionDto transaction = new TransactionDto();
            transaction.setId(Long.valueOf(csvRecord.get(0)));
            transaction.setSenderRef(csvRecord.get(1));
            transaction.setAmount(Long.parseLong(csvRecord.get(2)));
            transaction.setStatus(csvRecord.get(3));
            transaction.setCreatedOn(csvRecord.get(4));
            lines.add(transaction);
        }

        reader.close();
        csvParser.close();

        return RepeatStatus.FINISHED;
    }


    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution
                .getJobExecution()
                .getExecutionContext()
                .put("lines", this.lines);
        log.debug("Lines Reader ended.");
        return ExitStatus.COMPLETED;
    }
}