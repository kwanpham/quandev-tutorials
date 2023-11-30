package quandev.com.springbatchdemo;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;


@SpringBootTest
public class SpringBatchTest {


    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job jobTasklet;


    @Autowired
    Job jobChunk;

    @Autowired
    Job partitionJob;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    @Test
    public void givenTaskletsJob_whenJobEnds_thenStatusCompleted() throws Exception {
        System.out.println("Batch job starting");
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("time", format.format(Calendar.getInstance().getTime())).toJobParameters();
        jobLauncher.run(jobTasklet, jobParameters);
        System.out.println("Batch job executed successfully\n");
    }

    @Test
    public void givenChunkJob_whenJobEnds_thenStatusCompleted() throws Exception {
        System.out.println("Batch job starting");
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("time", format.format(Calendar.getInstance().getTime())).toJobParameters();
        jobLauncher.run(jobChunk, jobParameters);
        System.out.println("Batch job executed successfully\n");
    }

    @Test
    public void givenPartitionJob_whenJobEnds_thenStatusCompleted() throws Exception {
        System.out.println("Batch job starting");
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("time", format.format(Calendar.getInstance().getTime())).toJobParameters();
        jobLauncher.run(partitionJob, jobParameters);
        System.out.println("Batch job executed successfully\n");
    }

}
