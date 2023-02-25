package org.example.biz;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 读取excel，设置监听器
 *
 * @author lxcecho 909231497@qq.com
 * @since 17:58 25-02-2023
 */
public class CecDataListener extends AnalysisEventListener<CecData> {

    /**
     * 定义一个存储的界限，每读取100条数据就存储一次数据库，防止数据过多时发生溢出
     * 存储完成之后就清空list重新读取新的数据，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    List<CecData> cache = new ArrayList<>(BATCH_COUNT);

    /**
     * 一行一行读取，每读取一行数据时就会调用该方法
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context analysis context
     */
    @Override
    public void invoke(CecData data, AnalysisContext context) {
        System.out.println(data);

        // data 中的 cecMessage 处理
        String cecMessage = data.getCecMessage();
        String newCecMsg = CecMsgTransformUtil.parseCecMessage(cecMessage);

        System.out.println(newCecMsg);

        data.setCecMessage(newCecMsg);

        cache.add(data);
        /**
         * TODO 达到 BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易 OOM
         */
        if (cache.size() == BATCH_COUNT) {
            saveData();
        }
    }

    private void saveData() {
        // TODO 写数据到文件
        generateNewFile(cache);

        // 清空缓存列表重新读取
        cache.clear();
    }

    /**
     * 所有数据读取完毕之后调用，一般用于对最后读取到的数据进行处理
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
//        System.out.println("读取结束");
        saveData();
    }

    /**
     * 读取表头数据
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
//        System.out.println("head: " + headMap);
        super.invokeHeadMap(headMap, context);
    }

    /**
     * 在转换异常，获取其他异常的情况下会调用此接口，抛出异常就停止读取，如果不抛出异常就继续读取
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        System.out.println("解析失败，但是继续解析下一行：" + exception.getMessage());
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException e = (ExcelDataConvertException) exception;
            System.out.println("数据解析异常，所在行：" + e.getRowIndex() + "，所在列：" + e.getColumnIndex() + "，数据是: " + e.getCellData());
        }
        super.onException(exception, context);
    }

    /**
     * 重新生成一个excel文件
     */
    public void generateNewFile(List<CecData> cecReadDataList) {
        // 注意 simpleWrite在数据量不大的情况下可以使用（5000以内，具体也要看实际情况），数据量大参照 重复多次写入
        String fileName = LocalDateTime.now().toString().replace(":","-").replace(".", "-") + ".xlsx";

        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, CecData.class).sheet("NEW-CEC-FILE").doWrite(cecReadDataList);
    }

}
