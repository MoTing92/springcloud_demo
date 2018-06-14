package com.mvne.springcloud.common.util.utils;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.mvne.springcloud.common.util.common.ExcelConstant;


public class ExportExcelUtil {
	
	@SuppressWarnings("resource")
	public static void exportExcel(HttpServletResponse response){
		
		// 第一步，创建一个webbook（工作簿），对应一个Excel文件  
		HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("实卡验收实卡信息表");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row1 = sheet.createRow(0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        
        // 创建一个水平居中格式
        HSSFCellStyle style1 = wb.createCellStyle();
        HSSFFont font1 = wb.createFont();
        font1.setFontName("黑体");
        font1.setFontHeightInPoints((short)20);
        font1.setBold(true);
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style1.setFont(font1);
       
        // 创建一个垂直/水平居中格式
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont font2 = wb.createFont();
        font2.setFontHeightInPoints((short)14);
        style2.setFont(font2);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        //创建一个文本单元格
        HSSFCellStyle style3 = wb.createCellStyle();  
        HSSFDataFormat format = wb.createDataFormat();  
        style3.setDataFormat(format.getFormat("@"));
        
        HSSFCellStyle style4 = wb.createCellStyle();  
        //设置单元格格式为时间格式yyyy/MM/dd HH:mm:ss
        style4.setDataFormat((short)184);
        
        HSSFCellStyle style5 = wb.createCellStyle(); 
        font2.setFontHeightInPoints((short)14);
        style5.setFont(font2);
        
        //合并单元格
        CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 17);
		sheet.addMergedRegion(region1);
		//设置列宽
		sheet.setColumnWidth(0, 6800);
		sheet.setColumnWidth(1, 6800);
		sheet.setColumnWidth(7, 3200);
		sheet.setColumnWidth(12, 5000);
		sheet.setColumnWidth(13, 5000);
		sheet.setColumnWidth(14, 5000);
		sheet.setColumnWidth(15, 3000);
		sheet.setColumnWidth(16, 3200);
		
		
		//创建表格
		String[] columns = ExcelConstant.COLUMN;
		ArrayList<String> columnList = new ArrayList<>();
		for (int i = 0; i < columns.length; i++) {
			columnList.add(columns[i]);
		}
		HSSFCell cell_1_1 = row1.createCell(0);  
        cell_1_1.setCellValue("实卡验收实卡信息表");  
        cell_1_1.setCellStyle(style1);
        
        HSSFRow row2 = sheet.createRow(1);
        row2.setRowStyle(style2);
        HSSFCell cell_0 = row2.createCell(0);
        cell_0.setCellValue(columnList.get(0));
        cell_0.setCellStyle(style3);
        
        HSSFCell cell_1 = row2.createCell(1);
        cell_1.setCellValue(columnList.get(1));
        cell_1.setCellStyle(style3);
        
        HSSFCell cell_2 = row2.createCell(2);
        cell_2.setCellValue(columnList.get(2));
        cell_2.setCellStyle(style3);
        
        HSSFCell cell_3 = row2.createCell(3);
        cell_3.setCellValue(columnList.get(3));
        cell_3.setCellStyle(style3);
        
        HSSFCell cell_4 = row2.createCell(4);
        cell_4.setCellValue(columnList.get(4));
        cell_4.setCellStyle(style3);
        
        HSSFCell cell_5 = row2.createCell(5);
        cell_5.setCellValue(columnList.get(5));
        cell_5.setCellStyle(style3);
        
        HSSFCell cell_6 = row2.createCell(6);
        cell_6.setCellValue(columnList.get(6));
        cell_6.setCellStyle(style3);
        
        HSSFCell cell_7 = row2.createCell(7);
        cell_7.setCellValue(columnList.get(7));
        cell_7.setCellStyle(style3);
        
        HSSFCell cell_8 = row2.createCell(8);
        cell_8.setCellValue(columnList.get(8));
        cell_8.setCellStyle(style3);
        
        HSSFCell cell_9 = row2.createCell(9);
        cell_9.setCellValue(columnList.get(9));
        cell_9.setCellStyle(style3);
        
        HSSFCell cell_10 = row2.createCell(10);
        cell_10.setCellValue(columnList.get(10));
        cell_10.setCellStyle(style3);
        
        HSSFCell cell_11 = row2.createCell(11);
        cell_11.setCellValue(columnList.get(11));
        cell_11.setCellStyle(style3);
        
        HSSFCell cell_12 = row2.createCell(12);
        cell_12.setCellValue(columnList.get(12));
        cell_12.setCellStyle(style3);
        
        HSSFCell cell_13 = row2.createCell(13);
        cell_13.setCellValue(columnList.get(13));
        cell_13.setCellStyle(style3);
        
        HSSFCell cell_14 = row2.createCell(14);
        cell_14.setCellValue(columnList.get(14));
        cell_14.setCellStyle(style3);
        
        HSSFCell cell_15 = row2.createCell(15);
        cell_15.setCellValue(columnList.get(15));
        cell_15.setCellStyle(style3);
        
        HSSFCell cell_16 = row2.createCell(16);
        cell_16.setCellValue(columnList.get(16));
        cell_16.setCellStyle(style3);
        
        HSSFCell cell_17 = row2.createCell(17);
        cell_17.setCellValue(columnList.get(17));
        cell_17.setCellStyle(style3);
        
        // 第五步，将文件存到指定位置  
        OutputStream out = null;
        try{  
        	out = response.getOutputStream();
        	SimpleDateFormat fms = new SimpleDateFormat("yyyyMMddHHmm");
        	Date date = new Date();
            String fileName = "实卡验收实卡信息表"+fms.format(date)+".xls";// 文件名  
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
//            response.setContentType("application/x-msdownload");  
            response.setHeader("Content-Disposition", "attachment; filename="  
                                                    + URLEncoder.encode(fileName, "UTF-8"));  
            wb.write(out);  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
    }
	
	public static void main(String[] args) {
		System.out.println("!!!!!!!!");
	}
}
