package quandev.com.dto;

import lombok.Data;
import quandev.com.enums.InventoryStatus;

import java.util.UUID;

@Data
public class InventoryResponseDTO {

    private UUID orderId;
    private Integer userId;
    private Integer productId;
    private InventoryStatus status;

}