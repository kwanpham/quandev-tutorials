package quandev.com.springbatchdemo.job.chunks;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;
import quandev.com.springbatchdemo.dto.TransactionDto;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Slf4j
public class TransactionReaderChunk implements ItemReader<TransactionDto> , StepExecutionListener {
    

    private List<TransactionDto> lines;

    String filePath;

    Reader reader;
    CSVParser csvParser;
    Iterator<CSVRecord> iterator;

    @Override
    public void beforeStep(StepExecution stepExecution)  {
        lines = new ArrayList<>();
        filePath = "temp/transaction.csv";
        try {
            reader = new FileReader(filePath);
            csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            iterator = csvParser.iterator();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        log.info("Lines Reader initialized.");
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

    @Override
    public TransactionDto read() throws UnexpectedInputException, ParseException, NonTransientResourceException {

        while (iterator.hasNext()) {
            CSVRecord csvRecord = iterator.next();
            TransactionDto transaction = new TransactionDto();
            transaction.setSenderRef(csvRecord.get(0));
            transaction.setAmount(Long.parseLong(csvRecord.get(1)));
            transaction.setStatus(csvRecord.get(2));
            transaction.setCreatedOn(csvRecord.get(3));
            return transaction;
        }

        return null;


    }
}