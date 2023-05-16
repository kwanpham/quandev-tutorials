package quandev.com.springbatchdemo.job.chunks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import quandev.com.springbatchdemo.dto.TransactionDto;
import quandev.com.springbatchdemo.dto.TransactionEntity;

@Component
@Slf4j
public class TransactionProcessorChunk implements ItemProcessor<TransactionDto, TransactionEntity>, StepExecutionListener {




    @Override
    public void beforeStep(StepExecution stepExecution) {

        log.info("Lines Processor initialized.");
    }


    @Override
    public TransactionEntity process(TransactionDto transaction) throws InterruptedException {

        return null;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.debug("Lines Processor ended.");
        return ExitStatus.COMPLETED;
    }


}