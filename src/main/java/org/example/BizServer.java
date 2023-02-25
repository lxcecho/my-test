package org.example;

import com.alibaba.excel.EasyExcel;
import org.example.biz.CecData;
import org.example.biz.CecDataListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BizServer {

    public static void main(String[] args) {
        SpringApplication.run(BizServer.class, args);
    }

}

//public class BizServer {
//
//    public static void main(String[] args) {
////        String line = "S048f|R409000|S048f|R409000|Rff84000005|S048f|R409000|S048f|R409000|Rff84000005|S048f|R409000|S048f|R409000|Rff84000005|S048f|R409000|S048f|R409000|Rff84000005|S048f|R409000|S048f|R409000|Rff84000005|S048f|R409000|S048f|R409000|Rff84000005";
////        System.out.println(line.length());
//
//        String filePath = "D:\\LXCECHOCODE\\my-test\\src\\main\\resources\\key-value.xlsx";
//        EasyExcel.read(filePath, CecData.class, new CecDataListener()).sheet().doRead();
//
//    }
//}