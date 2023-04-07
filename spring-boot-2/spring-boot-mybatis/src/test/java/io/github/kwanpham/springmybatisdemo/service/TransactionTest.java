package io.github.kwanpham.springmybatisdemo.service;

import com.github.javafaker.Faker;
import com.zaxxer.hikari.HikariDataSource;
import io.github.kwanpham.springmybatisdemo.model.Employee;
import io.github.kwanpham.springmybatisdemo.repository.EmployeeRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

@SpringBootTest
public class TransactionTest {

    @Autowired
    EmployeeRepo employeeRepo;

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
            System.out.println(ex.toString());
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
            System.out.println(ex.toString());
            connection.rollback();
        } finally {
            connection.close();
        }
    }

}
