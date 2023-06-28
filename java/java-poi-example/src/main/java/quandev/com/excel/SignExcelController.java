package quandev.com.excel;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.javalin.http.staticfiles.Location;
import io.javalin.util.FileUtil;
import jakarta.servlet.annotation.WebFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SignExcelController {

    public static void main(String[] args) {

        TestAddSignKey testAddSignKey = new TestAddSignKey();

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public", Location.CLASSPATH);
        }).start(8080);
        // Register a filter to set UTF-8 encoding for requests and responses


        app.post("/upload-example", ctx -> {

            List<UploadedFile> uploadedFiles = ctx.uploadedFiles("files");

            for (UploadedFile file : uploadedFiles) {
                FileUtil.streamToFile(ctx.uploadedFile("files").content(), "upload/" + file.filename());
                testAddSignKey.signExcel("upload/" + file.filename());
            }
            downloadFile(ctx);
        });
    }

    private static void downloadFile(Context ctx) {
        String filePath = "temp/result.xlsx"; // Replace with the actual path to your file

        File file = new File(filePath);

        if (!file.exists() || file.isDirectory()) {
            ctx.status(404).result("File not found");
            return;
        }

        try {
            InputStream inputStream = new FileInputStream(file);

            ctx.contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                    .result(inputStream);

//            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Failed to download file");
        }
    }



}
