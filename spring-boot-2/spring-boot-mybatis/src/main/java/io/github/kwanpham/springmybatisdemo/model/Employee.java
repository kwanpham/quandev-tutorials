package io.github.kwanpham.springmybatisdemo.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Created by https://github.com/kwanpham
 */
@Data
@Accessors(chain = true)
public class Employee {

    private long employeeId;
    private String name;
    private String phone;
    private String email;
    private String gender;
    private String status;
    private byte[] avatar;
    private long money;
    private LocalDateTime createDate;

}
