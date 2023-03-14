package io.github.kwanpham.springmybatisdemo.repository;

import com.github.javafaker.Faker;
import com.zaxxer.hikari.HikariDataSource;
import io.github.kwanpham.springmybatisdemo.model.Employee;
import io.github.kwanpham.springmybatisdemo.model.EmployeeSearch;
import lombok.extern.slf4j.Slf4j;
import net.andreinc.mockneat.MockNeat;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.*;

@Slf4j
@SpringBootTest
class EmployeeRepoTest {

    @Autowired
    EmployeeRepo employeeRepo;

    @Test
    void findAll() {
        System.out.println(employeeRepo.findAll());
    }

    @Test
    void getById() {
        System.out.println(employeeRepo.findById(500));
    }

    @Test
    void insert() {
        Faker faker = new Faker();
        Employee employee1 = new Employee()
                .setName(faker.name().name())
                .setPhone(faker.phoneNumber().phoneNumber())
                .setEmail(faker.internet().emailAddress())
                .setGender(faker.dog().gender())
                .setStatus("A")
                .setCreateDate(LocalDateTime.now());

        System.out.println(employee1.getEmployeeId());
    }

    @Test
    void insertMany() {


        MockNeat mock1 = MockNeat.threadLocal(); // recommended !
        Faker faker = new Faker();
        for (int i = 0; i < 1000; i++) {

            Employee employee1 = new Employee()
                    .setName(faker.name().name())
                    .setPhone(faker.phoneNumber().phoneNumber())
                    .setEmail(mock1.emails().domains("hotmail.com", "gmail.com", "infoplusvn.com", "ask.to").get())
                    .setGender(faker.dog().gender())
                    .setStatus(RandomUtils.nextInt(0, 5) + "")
                    .setCreateDate(randomLocalDateTime());

            try {
                employeeRepo.insert(employee1);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public LocalDateTime randomLocalDateTime() {
        Instant hundredYearsAgo = Instant.now().minus(Duration.ofDays(100 * 365));
        Instant now = Instant.now();
        long startSeconds = hundredYearsAgo.getEpochSecond();
        long endSeconds = now.getEpochSecond();
        long random = ThreadLocalRandom.current().nextLong(startSeconds, endSeconds);

        return LocalDateTime.ofInstant(Instant.ofEpochSecond(random), ZoneId.systemDefault());
    }


    @Test
    void testDysnamic() {
        EmployeeSearch employeeSearch = new EmployeeSearch();
        employeeSearch.setPrefixEmail("gmail.com");
        System.out.println(employeeRepo.findByEmployeeSearch(employeeSearch));
        System.out.println(employeeRepo.countByEmployeeSearch(employeeSearch));
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    void testRollbacnk() {
        Faker faker = new Faker();
        Employee employee1 = new Employee()
                .setName(faker.name().name())
                .setPhone(faker.phoneNumber().phoneNumber())
                .setEmail(faker.internet().emailAddress())
                .setGender(faker.dog().gender())
                .setStatus("A")
                .setCreateDate(LocalDateTime.now());

        employeeRepo.insert(employee1);
        employeeRepo.insert(employee1);
    }

    @Test
    void testSelectForUpdate() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0 ; i<5 ; i++) {
            executorService.execute(() -> {
                log.info(employeeRepo.findByIdForUpdate(500).toString());
            });
        }

        executorService.awaitTermination(5 , TimeUnit.SECONDS);

    }


    @Autowired
    private PlatformTransactionManager transactionManager;


    @Test
    void test() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(DefaultTransactionDefinition.ISOLATION_READ_COMMITTED);

        TransactionStatus status = transactionManager.getTransaction(definition);
        try {
            Faker faker = new Faker();
            Employee employee1 = new Employee()
                    .setName(faker.name().name())
                    .setPhone(faker.phoneNumber().phoneNumber())
                    .setEmail(faker.internet().emailAddress())
                    .setGender(faker.dog().gender())
                    .setStatus("A")
                    .setCreateDate(LocalDateTime.now());

            employeeRepo.insert(employee1);
            employeeRepo.insert(employee1);
        } catch (Exception ex) {
            log.error(ex.toString());
            transactionManager.rollback(status);
        }
    }


    @Autowired
    HikariDataSource dataSource;

    @Test
    void test2() throws SQLException {

        // ko cùng connection nên ko rollback dc

        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        try {
            Faker faker = new Faker();
            Employee employee1 = new Employee()
                    .setName(faker.name().name())
                    .setPhone(faker.phoneNumber().phoneNumber())
                    .setEmail(faker.internet().emailAddress())
                    .setGender(faker.dog().gender())
                    .setStatus("A")
                    .setCreateDate(LocalDateTime.now());

            employeeRepo.insert(employee1);
            employeeRepo.insert(employee1);
            connection.commit();

        } catch (Exception ex) {
            log.error(ex.toString());
            connection.rollback();
        } finally {
            connection.close();
        }
    }



    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findByStatusAnostation() {
        System.out.println(employeeRepo.findByStatus("1"));
    }
}