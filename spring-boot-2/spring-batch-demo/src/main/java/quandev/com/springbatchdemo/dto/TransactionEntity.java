package quandev.com.springbatchdemo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
@Table(name = "Transaction")
@Entity
public class TransactionEntity {


    private Long id;

    @Id
    private String senderRef;

    private long amount;

    private String status;

    private LocalDateTime createdOn;

}
