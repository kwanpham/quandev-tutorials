package com.example.springbootcache.controller;

import com.example.springbootcache.repo.AchBankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

@RestController
public class BankController {

    @Autowired
    AchBankRepo achBankRepo;

    @GetMapping("/benId/{binId}")
    public String findBenIdByBinId(@PathVariable String binId) {
        String temp = achBankRepo.findBenIdByBinId(binId);
        if (StringUtils.isEmptyOrWhitespace(temp)) {
            return "Nothing found";
        }
        return temp;
    }

    @GetMapping("/bicCode/{benId}")
    public String findBicCodeByBenId(@PathVariable String benId) {
        String temp = achBankRepo.findBicCodeByBenId(benId);
        if (StringUtils.isEmptyOrWhitespace(temp)) {
            return "Nothing found";
        }
        return temp;
    }

    @GetMapping("/bankId/{bicCode}")
    public String findBankIdByBicCode(@PathVariable String bicCode) {
        String temp = achBankRepo.findBankIdByBicCode(bicCode);
        if (StringUtils.isEmptyOrWhitespace(temp)) {
            return "Nothing found";
        }
        return temp;
    }

}
