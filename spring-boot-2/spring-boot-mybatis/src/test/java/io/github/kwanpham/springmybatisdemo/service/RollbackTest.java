package io.github.kwanpham.springmybatisdemo.service;

import com.github.javafaker.Faker;
import io.github.kwanpham.springmybatisdemo.model.BankAccountVO;
import io.github.kwanpham.springmybatisdemo.model.Employee;
import io.github.kwanpham.springmybatisdemo.repository.BankAccountRepo;
import io.github.kwanpham.springmybatisdemo.repository.EmployeeRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
public class RollbackTest {

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    BankAccountRepo bankAccountRepo;

    Faker faker = new Faker();

    @Test
    void testRollbackMultilTable() {
        BankAccountVO vo = new BankAccountVO()
                .setFullName(faker.name().fullName())
                .setBalance(8000L);

        int result = bankAccountRepo.insert(vo);
        System.out.println(result);

        Employee employee1 = new Employee()
                .setName(faker.name().name())
                .setPhone("950.976.8225")
                .setEmail(faker.internet().emailAddress())
                .setGender(faker.dog().gender())
                .setStatus("A")
                .setCreateDate(LocalDateTime.now());

        employeeRepo.insert(employee1);
        System.out.println(employee1.getEmployeeId());

    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    void testRollbackOneTable() {
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



}
