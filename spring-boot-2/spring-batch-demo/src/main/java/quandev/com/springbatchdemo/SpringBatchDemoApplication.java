package quandev.com.springbatchdemo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@SpringBootApplication
public class SpringBatchDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchDemoApplication.class, args);
    }



    @Override
    public void run(String... args) throws Exception {
//        System.out.println("Batch job starting");
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("time", format.format(Calendar.getInstance().getTime())).toJobParameters();
//        jobLauncher.run(job, jobParameters);
//        System.out.println("Batch job executed successfully\n");
    }
}
