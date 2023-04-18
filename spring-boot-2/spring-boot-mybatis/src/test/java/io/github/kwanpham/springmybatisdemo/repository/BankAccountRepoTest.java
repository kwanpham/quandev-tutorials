package io.github.kwanpham.springmybatisdemo.repository;

import com.github.javafaker.Faker;
import io.github.kwanpham.springmybatisdemo.model.BankAccountVO;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BankAccountRepoTest {

    @Autowired
    BankAccountRepo bankAccountRepo;

    @Autowired
    EmployeeRepo employeeRepo;

    Faker faker = new Faker();
    @Test
    void bankAccountTestInsert() {
        BankAccountVO vo = new BankAccountVO()
                .setFullName(faker.name().fullName())
                .setBalance(8000L)
                .setVersion(1)
                .setEmploeeId(RandomUtils.nextLong(1 , 199));

        int result = bankAccountRepo.insert(vo);
        System.out.println(result);
    }

    @Test
    void bankAccountTestInsertMany() {

        for (int i = 0 ; i < 300 ; i ++) {
            BankAccountVO vo = new BankAccountVO()
                    .setFullName(faker.name().fullName())
                    .setBalance(RandomUtils.nextLong(1 , 100) * 100)
                    .setVersion(1)
                    .setEmploeeId(RandomUtils.nextLong(1 , 199));

            int result = bankAccountRepo.insert(vo);
            System.out.println(result);
        }

    }



}