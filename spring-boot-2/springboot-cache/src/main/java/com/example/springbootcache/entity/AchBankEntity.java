package com.example.springbootcache.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "ACH_BANK")
public class AchBankEntity {

    @Id
    @Column(name = "BIC_CODE")
    private String bicCode;

    @Column(name = "BANK_NAME", nullable = false)
    private String bankName;

    @Column(name = "BANK_NAME_VN", nullable = false)
    private String bankNameVN;

    @Column(name = "BANK_ID", nullable = false, unique = true)
    private String bankId;

    @Column(name = "BEN_ID", nullable = false, unique = true)
    private String benId;

//    @JsonIgnore
//    @OneToMany(mappedBy = "achBank", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
//    @ToString.Exclude // Khoonhg sử dụng trong toString()
//    // Quan hệ 1-n với đối tượng ở dưới (Person) (1 địa điểm có nhiều người ở)
//    private List<AchBankBinEntity> achBankBinEntityList;


    @Transient
    private List<String> binIds;

}
