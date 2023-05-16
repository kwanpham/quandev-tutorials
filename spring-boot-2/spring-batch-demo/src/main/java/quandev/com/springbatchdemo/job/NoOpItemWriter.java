package quandev.com.springbatchdemo.job;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoOpItemWriter implements ItemWriter {


    @Override
    public void write(List list) throws Exception {

    }
}