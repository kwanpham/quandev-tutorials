package quandev.com.springbatchdemo.job.partrion;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ColumnRangePartitioner implements Partitioner {


    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {

        int min = 0;
        int max = 0;

        try {
            long lines = Files.lines(Paths.get("temp/transaction.csv")).count();
            max = (int) lines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int targetSize = (max - min) / gridSize + 1;

        Map<String, ExecutionContext> result = new HashMap<>();

        int loop = 0;
        int start = min;
        int end = start + targetSize - 1;

        while (start <= max) {
            ExecutionContext value = new ExecutionContext();
            result.put("partition" + loop, value);

            if (end >= max) {
                end = max;
            }

            value.putInt("minValue", start);
            value.putInt("maxValue", end);

            start += targetSize;
            end += targetSize;

            loop++;
        }
        return result;
    }
}