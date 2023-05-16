package quandev.com.springbatchdemo;

import com.github.javafaker.Faker;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import quandev.com.springbatchdemo.dto.TransactionEntity;
import quandev.com.springbatchdemo.repo.TransactionRepo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class SpringBatchDemoApplicationTests {

    @Autowired
    TransactionRepo transactionRepo;

    @Test
    void createTransactionFile() throws IOException {

        String csvFile = "temp/transaction.csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

        for (int i = 0; i < 100; i++) {

            String senderRef = System.currentTimeMillis() + RandomStringUtils.randomAlphanumeric(10);
            String amount = RandomUtils.nextLong(1, 9000000) + "00";
            String status = RandomUtils.nextBoolean() ? "SUCCESS" : "FAILED";
            String createdOn = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss").format(LocalDateTime.now());
            csvPrinter.printRecord(i , senderRef, amount, status, createdOn);

        }

        csvPrinter.flush();
        writer.close();

    }

}
