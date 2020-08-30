package com.java.informationstatistic.tools;

import com.java.informationstatistic.model.Result;
import com.java.informationstatistic.service.CarResultService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * excel导出类
 *
 * @author luyu
 * @since 20200724
 * @version v1.0
 *
 * copyright 18994139782@163.com
 */
public class ExcelUtil {
    /**
     * 日志打印
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 生成excel
     * @param response   回应体对象
     * @param beginTime 开始时间
     * @param frequency 几个月的数据
     * @param platform 平台
     */
    public static void generateExcel(HttpServletResponse response, String beginTime, String platform, int frequency, CarResultService carResultService){
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=statistics.xlsx");
        OutputStream os  = null;
        try{
            SXSSFWorkbook hwb = getExcelData(beginTime,platform,frequency,carResultService);
           os = response.getOutputStream();
           hwb.write(os);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }finally {
            if(os!=null){
                try{
                    os.close();
                }catch (Exception e){

                }

            }
        }
    }

    /**
     * 将数据写入excel中
     * @return 返回对象信息
     */
    private static SXSSFWorkbook getExcelData(String beginTime,String platform,int frequency,CarResultService carResultService){
        //创建工作空间
        SXSSFWorkbook hw = new SXSSFWorkbook();
        //生成样式
        CellStyle style = hw.createCellStyle();
        //水平居中
        style.setAlignment(CellStyle.ALIGN_CENTER);
        //垂直居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 生成一个字体
        Font font = hw.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("宋体");
        // 把字体 应用到当前样式
        style.setFont(font);

        for(int i = 0;i<frequency;i++) {
            if (i != 0) {
                //每次增加一个月
                beginTime = generationMonth(beginTime);
            }
            double total = 0.0;
            Map<String, String> params = new HashMap<>();
            params.put("beginTime", beginTime + "-01");
            params.put("endTime", beginTime + "-31");
            params.put("platform", platform);
            if (Integer.valueOf(beginTime.split("-")[0])>=2020) {
                total = carResultService.queryResultInfo(params);
            } else {
                total = carResultService.queryAllResultInfo(params);
            }
            total /= StringInfo.EXCEL_SIZE;
            if (total > 1) {
                for(int l =0;l<Math.ceil(total);l++){
                    dealExcel(hw,beginTime,style,l,true,platform,carResultService);
                }
            }else {
                dealExcel(hw,beginTime,style,0,false,platform,carResultService);
            }
        }
        return hw;
    }

    /**
     * 获取下个月的时间
     * @param month
     * @return
     */
    private static String generationMonth(String month){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try{
            Date date = sdf.parse(month);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH,1);
            return sdf.format(calendar.getTime());
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 将数据写入excel
     * @param hw 工作对象
     * @param beginTime 开始时间
     * @param style 样式
     * @param pageNumber 页数
     * @param flag 标识
     */
    private static void dealExcel(SXSSFWorkbook hw,String beginTime,CellStyle style,int pageNumber,boolean flag,
                           String platform,CarResultService carResultService){
        Map<String,String> param = new HashMap<>();
        param.put("beginTime",beginTime+"-01");
        param.put("endTime",beginTime+"-31");
        param.put("platform",platform);
        List<Result> printData = null;
        if(flag){
            //需要分页
            param.put("firstIndex",pageNumber * StringInfo.EXCEL_SIZE+"");
            param.put("pageSize",StringInfo.EXCEL_SIZE+"");
        }else{
            param.put("firstIndex","0");
            param.put("pageSize",StringInfo.EXCEL_SIZE+"");
        }
        if (Integer.valueOf(beginTime.split("-")[0])>=2020) {
            printData = carResultService.queryResultLimit(param);
        } else {
            printData = carResultService.queryAllResultLimit(param);
        }
        if(printData==null||printData.isEmpty()){
            return;
        }
        Sheet sheet = hw.createSheet(beginTime+"("+pageNumber+")");
        //创建表
        sheet.setDefaultColumnWidth(20);
        sheet.setDefaultRowHeightInPoints(20);
        //创建行
        Row row = sheet.createRow(0);
        // 添加表头数据
        for (int j = 0; j <StringInfo.TITLE.length; j++) {
            Cell cell = row.createCell(j);
            cell.setCellValue(StringInfo.TITLE[j]);
            cell.setCellStyle(style);
        }

        // 添加单元格数据
        for (int k = 0; k < printData.size();k++) {
            row = sheet.createRow(k + StringInfo.ONE);
            if(printData.get(k)==null){
                continue;
            }
            row.createCell(0).setCellValue(printData.get(k).getBrand());
            row.createCell(1).setCellValue(printData.get(k).getBrandLevel());
            row.createCell(2).setCellValue(printData.get(k).getNeed());
            row.createCell(3).setCellValue(printData.get(k).getNeedLevel());
            row.createCell(4).setCellValue(printData.get(k).getChannel());
            row.createCell(5).setCellValue(printData.get(k).getSentiment());
            row.createCell(6).setCellValue(printData.get(k).getTime());
            row.createCell(7).setCellValue(printData.get(k).getNum());
        }
    }
}
