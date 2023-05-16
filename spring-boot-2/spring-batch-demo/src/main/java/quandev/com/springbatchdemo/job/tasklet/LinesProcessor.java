package quandev.com.springbatchdemo.job.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quandev.com.springbatchdemo.dto.TransactionEntity;
import quandev.com.springbatchdemo.repo.TransactionRepo;

import java.util.List;

@Component
@Slf4j
public class LinesProcessor implements Tasklet, StepExecutionListener {

    @Autowired
    private TransactionRepo transactionRepo;

    private List<TransactionEntity> transactionList;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution
                .getJobExecution()
                .getExecutionContext();
        this.transactionList = (List<TransactionEntity>) executionContext.get("lines");
        log.info(transactionList.toString());
        log.info("Lines Processor initialized.");
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        transactionRepo.saveAll(transactionList);
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution.getJobExecution().getExecutionContext().remove("lines ");
        log.debug("Lines Processor ended.");
        return ExitStatus.COMPLETED;
    }
}