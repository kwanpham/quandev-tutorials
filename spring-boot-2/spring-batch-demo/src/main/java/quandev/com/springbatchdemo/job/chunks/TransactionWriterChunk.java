package quandev.com.springbatchdemo.job.chunks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quandev.com.springbatchdemo.dto.TransactionDto;
import quandev.com.springbatchdemo.dto.TransactionEntity;
import quandev.com.springbatchdemo.repo.TransactionRepo;

import java.util.List;

@Slf4j
@Component
public class TransactionWriterChunk implements ItemWriter<TransactionEntity> {

    @Autowired
    private TransactionRepo transactionRepo;


    @Override
    public void write(List<? extends TransactionEntity> list) throws Exception {
        log.info("List Size chunk: " + list.size());
        transactionRepo.saveAll(list);
    }
}
