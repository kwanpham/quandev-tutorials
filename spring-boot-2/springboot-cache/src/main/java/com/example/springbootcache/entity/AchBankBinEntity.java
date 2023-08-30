package com.example.springbootcache.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ACH_BANK_BIN")
public class AchBankBinEntity {

    @Id
    @Column(name = "BIN_ID" ,  nullable = false)
    private String binId;

    @Column(name = "BIC_CODE" ,  nullable = false)
    private String bicCode;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "bic_code") // thông qua khóa ngoại address_id
//    private AchBankEntity achBankEntity;

}
