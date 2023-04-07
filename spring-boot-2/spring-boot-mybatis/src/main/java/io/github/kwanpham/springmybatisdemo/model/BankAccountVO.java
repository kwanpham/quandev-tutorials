package io.github.kwanpham.springmybatisdemo.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@KeySequence("SEQ_BANK_ACCOUNT")
@TableName("BANK_ACCOUNT")
public class BankAccountVO {


    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    private String fullName;

    private Long balance;

    @Version
    private Integer version;

    private Long emploeeId;


}
