package quandev.com.springbatchdemo.dto;

import lombok.Data;

@Data
public class TransactionDto {

    private Long id;

    private String senderRef;

    private long amount;

    private String status;

    private String createdOn;


}
