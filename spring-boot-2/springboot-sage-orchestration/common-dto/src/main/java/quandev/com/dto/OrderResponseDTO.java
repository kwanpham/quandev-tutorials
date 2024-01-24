package quandev.com.dto;

import lombok.Data;
import quandev.com.enums.OrderStatus;

import java.util.UUID;

@Data
public class OrderResponseDTO {

    private UUID orderId;
    private Integer userId;
    private Integer productId;
    private Double amount;
    private OrderStatus status;

}