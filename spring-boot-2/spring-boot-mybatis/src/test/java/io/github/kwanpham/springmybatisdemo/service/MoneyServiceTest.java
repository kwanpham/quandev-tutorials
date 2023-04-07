package io.github.kwanpham.springmybatisdemo.service;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
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