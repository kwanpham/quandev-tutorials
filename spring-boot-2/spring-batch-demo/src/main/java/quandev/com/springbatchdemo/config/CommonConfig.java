package quandev.com.springbatchdemo.config;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import quandev.com.springbatchdemo.dto.TransactionDto;

@Configuration
public class CommonConfig {

    @Value("${server.tomcat.threads.max}")
    public Integer MAX_TOMCAT_POOL_SIZE;

    @Bean
    public TaskExecutor batchTaskExecutor() {

        int CORE_POOL_SIZE = 60;
        int MAX_POOL_SIZE = MAX_TOMCAT_POOL_SIZE * 3;

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(CORE_POOL_SIZE);
//        executor.setAllowCoreThreadTimeOut(true);
//        executor.setKeepAliveSeconds(300);
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize();
        return executor;
    }

    @Bean
    public FlatFileItemReader<TransactionDto> csvFileReader() {
        //Create reader instance
        FlatFileItemReader<TransactionDto> reader = new FlatFileItemReader<>();

        reader.setResource(new PathResource("temp/transaction.csv"));

        //Set number of lines to skips. Use it if file has header rows.
        reader.setLinesToSkip(0);

        //Configure how each line will be parsed and mapped to different values
        reader.setLineMapper(new DefaultLineMapper() {
            {

                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames("id", "senderRef", "amount", "status", "createdOnString");
                    }
                });
                //Set values in Employee class
                setFieldSetMapper(new BeanWrapperFieldSetMapper<TransactionDto>() {
                    {
                        setTargetType(TransactionDto.class);
                    }
                });
            }
        });
        return reader;
    }
}
