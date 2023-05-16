//package quandev.com.springbatchdemo.util;
//
//import com.opencsv.CSVReader;
//import com.opencsv.CSVWriter;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.exception.ExceptionUtils;
//import quandev.com.springbatchdemo.dto.Transaction;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//@Slf4j
//public class FileUtils {
//
//
//    private String fileName;
//    private CSVReader csvReader;
//    private CSVWriter csvWriter;
//    private FileReader fileReader;
//    private FileWriter fileWriter;
//    private File file;
//
//    public FileUtils(String fileName) {
//        this.fileName = fileName;
//    }
//
//    public Transaction readLine() {
//        try {
//            if (csvReader == null) initReader();
//            String[] line = csvReader.readNext();
//            if (line == null) return null;
//
//            Transaction transaction = new Transaction();
//
//            transaction.setSenderRef(line[0]);
//            transaction.setAmount(Long.parseLong(line[1]));
//            transaction.setStatus(line[2]);
//            transaction.setCreatedOn(LocalDateTime.from(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss").parse(line[4])));
//
//            return transaction;
//        } catch (Exception e) {
//            log.info(ExceptionUtils.getStackTrace(e));
//            log.error("Error while reading line in file: " + this.fileName);
//            return null;
//        }
//    }
//
//    public void writeLine(Transaction line) {
//        try {
//            if (csvWriter == null) initWriter();
//            String[] lineStr = new String[2];
//            lineStr[0] = line.getName();
//            lineStr[1] = line
//                    .getAge()
//                    .toString();
//            csvWriter.writeNext(lineStr);
//        } catch (Exception e) {
//            log.error("Error while writing line in file: " + this.fileName);
//        }
//    }
//
//    private void initReader() throws Exception {
//        if (file == null) file = new File(fileName);
//        if (fileReader == null) fileReader = new FileReader(file);
//        if (csvReader == null) csvReader = new CSVReader(fileReader);
//    }
//
//    private void initWriter() throws Exception {
//        if (file == null) {
//            file = new File(fileName);
//            file.createNewFile();
//        }
//        if (fileWriter == null) fileWriter = new FileWriter(file, true);
//        if (csvWriter == null) csvWriter = new CSVWriter(fileWriter);
//    }
//
//    public void closeWriter() {
//        try {
//            csvWriter.close();
//            fileWriter.close();
//        } catch (Exception e) {
//            log.error("Error while closing writer.");
//        }
//    }
//
//    public void closeReader() {
//        try {
//            csvReader.close();
//            fileReader.close();
//        } catch (Exception e) {
//            log.error("Error while closing reader.");
//        }
//    }
//
//}