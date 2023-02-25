package org.example.controller;

import cn.hutool.json.JSONObject;
import com.alibaba.excel.EasyExcel;
import org.example.biz.CecData;
import org.example.biz.CecDataListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;

/**
 * @author lxcecho 909231497@qq.com
 * @since 20:07 25-02-2023
 */
@RestController
@RequestMapping("file")
public class FileController {
    @Value("${file.upload.url}")
    private String uploadFilePath;

    @Value("${file.download.url}")
    private String downloadFilePath;

    @RequestMapping("/upload")
    public String httpUpload(@RequestParam("files") MultipartFile files[]) {
        JSONObject object = new JSONObject();

        String fileName = files[0].getOriginalFilename();  // 文件名
        String pathname = uploadFilePath + '/' + fileName;
        File dest = new File(pathname);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            files[0].transferTo(dest);
        } catch (Exception e) {
            object.put("failed", -1);
            object.put("result", "There is an error, please upload again~");
            return object.toString();
        }

        EasyExcel.read(pathname, CecData.class, new CecDataListener()).sheet().doRead();

        object.put("success", 0);
        object.put("result", "File uploaded successfully~");
        return object.toString();
    }

    @RequestMapping("/download")
    public String fileDownLoad(HttpServletResponse response, @RequestParam("fileName") String fileName) {
        File file = new File(downloadFilePath + '/' + fileName);
        if (!file.exists()) {
            return "The download file does not exist~";
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(file.toPath()));) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            return "Download failed!";
        }
        return "Download Success!";
    }

}
