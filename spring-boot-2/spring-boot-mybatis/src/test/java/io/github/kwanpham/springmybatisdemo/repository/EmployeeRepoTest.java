package io.github.kwanpham.springmybatisdemo.repository;


import com.github.javafaker.Faker;
import io.github.kwanpham.springmybatisdemo.model.Employee;
import io.github.kwanpham.springmybatisdemo.model.EmployeeSearch;
import lombok.extern.slf4j.Slf4j;
import net.andreinc.mockneat.MockNeat;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
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

    Faker faker = new Faker();

    @Test
    void findAll() {
        System.out.println(employeeRepo.findAll());
    }

    @Test
    void getById() {
        System.out.println(employeeRepo.findById(500));
    }

    @Test
    void insert() throws IOException {

        Employee employee1 = new Employee()
                .setName(faker.name().name())
                .setPhone(faker.phoneNumber().phoneNumber())
                .setEmail(faker.internet().emailAddress())
                .setGender(faker.dog().gender())
                .setStatus("A")
                .setAvatar(new byte[0])
                .setCreateDate(LocalDateTime.now());

        employeeRepo.insert(employee1);
        System.out.println(employee1.getEmployeeId());
    }

    @Test
    void insertMany() throws IOException {

        byte[] avatarByte = FileUtils.readFileToByteArray(new File("temp/unnamed.jpg"));
        MockNeat mock1 = MockNeat.threadLocal(); // recommended !

        for (int i = 0; i < 200; i++) {
            Employee employee = new Employee()
                    .setName(faker.name().name())
                    .setPhone(faker.phoneNumber().phoneNumber())
                    .setEmail(mock1.emails().domains("hotmail.com", "gmail.com", "infoplusvn.com", "ask.to").get())
                    .setGender(faker.dog().gender())
                    .setStatus(RandomUtils.nextInt(0, 5) + "")
                    .setAvatar(RandomUtils.nextInt(1, 3) == 2 ? avatarByte : new byte[0])
                    .setCreateDate(randomLocalDateTime());

            try {
                employeeRepo.insert(employee);
                System.out.println(employee.getEmployeeId());
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
    void testDynamicSearch() {
        EmployeeSearch employeeSearch = new EmployeeSearch();
        employeeSearch.setPrefixEmail("gmail.com");
        System.out.println(employeeRepo.findByEmployeeSearch(employeeSearch));
        System.out.println(employeeRepo.countByEmployeeSearch(employeeSearch));
    }



    @Test
    void testSelectForUpdate() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0 ; i<5 ; i++) {
            executorService.execute(() -> {
                log.info(employeeRepo.findEmployeeByIdForUpdate(1768L).toString());
            });
        }

        executorService.awaitTermination(5 , TimeUnit.SECONDS);

    }



    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findByStatusAnnotation() {
        System.out.println(employeeRepo.findByStatus("1"));
    }


}