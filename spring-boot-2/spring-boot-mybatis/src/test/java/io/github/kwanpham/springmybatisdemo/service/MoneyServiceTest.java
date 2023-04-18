package io.github.kwanpham.springmybatisdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MoneyServiceTest {

    @Autowired
    MoneyService moneyService;


    @Test
    public void testTransfer() throws Exception {
        moneyService.withDrawMoneyOptimisticLock(121L, 500);
    }


    @Test
    public void testMutilTransferOnAAccount() {

        int threadNumber = 100;
        ExecutorService threadPool = Executors.newFixedThreadPool(threadNumber);

        for (int i = 0 ; i < threadNumber ; i++) {
            int finalI = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        moneyService.withDrawMoneyOptimisticLock(1L, 3000);
                        log.info("Transfer Success i " + finalI);
                    } catch (Exception e) {
                        log.error("Transfer Error with i {}  , error : {}" , finalI, e.toString());
                    }
                }
            });
        }

        threadPool.shutdown();

        // Wait until all threads are finish
        while (!threadPool.isTerminated()) {
            // Running ...
        }

        log.info("Done");

    }

    @Test
    public void testMutilTransferOnAAccountPessimisticLock() {

        int threadNumber = 100;
        ExecutorService threadPool = Executors.newFixedThreadPool(threadNumber);

        for (int i = 0 ; i < threadNumber ; i++) {
            int finalI = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        moneyService.withDrawMoneyPessimisticLock(1L, 3000);
                        log.info("Transfer Success i " + finalI);
                    } catch (Exception e) {
                        log.error("Transfer Error with i {}  , error : {}" , finalI, e.toString());
                    }
                }
            });
        }

        threadPool.shutdown();

        // Wait until all threads are finish
        while (!threadPool.isTerminated()) {
            // Running ...
        }

        log.info("Done");

    }

    @Test
    public void testMutilTransferOnAAccountPessimisticLock2() {

        int threadNumber = 10;
        ExecutorService threadPool = Executors.newFixedThreadPool(threadNumber);

        for (int i = 0 ; i < threadNumber ; i++) {
            int finalI = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        moneyService.withDrawMoneyPessimisticLock2(1L, 3000);
                        log.info("Transfer Success i " + finalI);
                    } catch (Exception e) {
                        log.error("Transfer Error with i {}  , error : {}" , finalI, e.toString());
                    }
                }
            });
        }

        threadPool.shutdown();

        // Wait until all threads are finish
        while (!threadPool.isTerminated()) {
            // Running ...
        }

        log.info("Done");

    }


}