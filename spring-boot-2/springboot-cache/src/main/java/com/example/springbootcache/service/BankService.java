package com.example.springbootcache.service;

import com.example.springbootcache.entity.AchBankBinEntity;
import com.example.springbootcache.entity.AchBankEntity;
import com.example.springbootcache.repo.AchBankBinRepo;
import com.example.springbootcache.repo.AchBankRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class BankService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    AchBankRepo achBankRepo;

    @Autowired
    AchBankBinRepo achBankBinRepo;

    @PostConstruct
    public void allBankToDatabase() throws Exception {
        List<AchBankEntity> achBankEntities = Arrays.asList(objectMapper.readValue(ResourceUtils.getURL("classpath:bank.json"), AchBankEntity[].class));
        achBankRepo.saveAll(achBankEntities);

        List<AchBankBinEntity> achBankBinEntities = new ArrayList<>();
        for (AchBankEntity achBankEntity : achBankEntities) {

            for (String bin : achBankEntity.getBinIds()) {
                AchBankBinEntity achBankBinEntity = new AchBankBinEntity();
                achBankBinEntity.setBicCode(achBankEntity.getBicCode());
                achBankBinEntity.setBinId(bin);
                achBankBinEntities.add(achBankBinEntity);

            }
        }
        achBankBinRepo.saveAll(achBankBinEntities);
        log.info("Init {} banks done" , achBankEntities.size());
        log.info("Init {} bins of banks done" , achBankBinRepo.count());
    }

//    public String getBankByBin() {
//
//    }


}
