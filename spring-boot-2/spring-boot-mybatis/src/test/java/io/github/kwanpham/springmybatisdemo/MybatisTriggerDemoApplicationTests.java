package io.github.kwanpham.springmybatisdemo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class MybatisTriggerDemoApplicationTests {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0 ; i<5 ; i++) {
            executorService.execute(() -> {
                System.out.println(jdbcTemplate.update("CALL TEST.PRO_WITHDRAW_MONEY(500,2000)"));
                countDownLatch.countDown();
            });
        }


        countDownLatch.await();
        System.out.println("Done!!!");

    }

    @Test
    void contextLoads2() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(5);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0 ; i<5 ; i++) {
            executorService.execute(() -> {
                try {
                    withDrawMoney(2000, countDownLatch);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }


        countDownLatch.await();
        System.out.println("Done!!!");

    }

    @Autowired
    private PlatformTransactionManager transactionManager;

    public void withDrawMoney( long p_money , CountDownLatch latch) throws InterruptedException {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();

        TransactionStatus status = transactionManager.getTransaction(definition);

        Long money = jdbcTemplate.queryForObject("select money from employee where employee_id = 600 for update" , Long.class);
        Thread.sleep(2000);
        if (money >= p_money ) {
            jdbcTemplate.update("UPDATE EMPLOYEE SET MONEY = ("+money+" - "+p_money+")");
            transactionManager.commit(status);
            System.out.println("Da tru: " + p_money);
        } else {
            System.out.println("---> Error Khong du tien " + money);
//            transactionManager.rollback(status);

        }
        latch.countDown();
    }


}
