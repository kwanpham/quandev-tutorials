package io.github.kwanpham.springmybatisdemo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MoneyServiceTest {

    @Autowired
    MoneyService moneyService;

    @Test
    public void testTransfer() throws Exception {
        moneyService.withDrawMoney(121L, 500);
    }

}