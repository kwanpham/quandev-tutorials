package quandev.com.dto;

import lombok.Data;
import quandev.com.enums.PaymentStatus;

import java.util.UUID;

@Data
public class PaymentResponseDTO {
    private Integer userId;
    private UUID orderId;
    private Double amount;
    private PaymentStatus status;
}