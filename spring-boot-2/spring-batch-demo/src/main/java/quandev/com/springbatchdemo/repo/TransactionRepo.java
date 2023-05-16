package quandev.com.springbatchdemo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import quandev.com.springbatchdemo.dto.TransactionEntity;

public interface TransactionRepo extends JpaRepository<TransactionEntity, String> {

    @Query("select sum(a.amount) from TransactionEntity a")
    long sumAmountAllTransaction();

}
