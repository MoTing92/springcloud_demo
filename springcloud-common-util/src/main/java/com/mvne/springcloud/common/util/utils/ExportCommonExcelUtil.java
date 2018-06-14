package com.mvne.springcloud.common.util.utils;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExportCommonExcelUtil {
	
	@SuppressWarnings({ "resource", "unlikely-arg-type" })
	public static HSSFWorkbook creatExcelXls(int rows,int columns,Map<String, List<String>> map,String title){
		if(map.size() != rows-1 || map.get(0).size() != columns) {
			return null;
		}
		// 第一步，创建一个webbook（工作簿），对应一个Excel文件 
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet(title);  
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
        
        HSSFCellStyle style5 = wb.createCellStyle(); 
        font2.setFontHeightInPoints((short)14);
        style5.setFont(font2);
        
        //合并单元格
        CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, columns-1);
		sheet.addMergedRegion(region1);
		
		HSSFCell cell_1_1 = row1.createCell(0);  
        cell_1_1.setCellValue(title);  
        cell_1_1.setCellStyle(style1);
        HSSFRow row;
        int i = 1;
        for (Entry<String, List<String>> entry : map.entrySet()) {
        	if(i >= rows+1) {
				break;
			}
        	row = sheet.createRow(i);
        	List<String> columnsValue = entry.getValue();
        	HSSFCell cell;
        	int j = 0;
        	for (String string : columnsValue) {
				if(j >= columns) {
					break;
				}
        		cell = row.createCell(j);
				cell.setCellValue(string);
				j++;
			}
        	i++;
		}
        return wb;
    }
	
	@SuppressWarnings("unlikely-arg-type")
	public static XSSFWorkbook createExcelXlsx(int rows,int columns,Map<String, List<String>> map,String title) {
		if(map.size() != rows-1 || map.get(0).size() != columns) {
			return null;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        XSSFSheet sheet = wb.createSheet(title);  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        XSSFRow row1 = sheet.createRow(0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        
        // 创建一个水平居中格式
        XSSFCellStyle style1 = wb.createCellStyle();
        XSSFFont font1 = wb.createFont();
        font1.setFontName("黑体");
        font1.setFontHeightInPoints((short)20);
        font1.setBold(true);
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style1.setFont(font1);
       
        // 创建一个垂直/水平居中格式
        XSSFCellStyle style2 = wb.createCellStyle();
        XSSFFont font2 = wb.createFont();
        font2.setFontHeightInPoints((short)14);
        style2.setFont(font2);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        //创建一个文本单元格
        XSSFCellStyle style3 = wb.createCellStyle();  
        XSSFDataFormat format = wb.createDataFormat();  
        style3.setDataFormat(format.getFormat("@"));  
        
        XSSFCellStyle style5 = wb.createCellStyle(); 
        font2.setFontHeightInPoints((short)14);
        style5.setFont(font2);
        
        //合并单元格
        CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, columns-1);
		sheet.addMergedRegion(region1);
		
		XSSFCell cell_1_1 = row1.createCell(0);  
        cell_1_1.setCellValue(title);  
        cell_1_1.setCellStyle(style1);
        XSSFRow row;
        int i = 1;
        for (Entry<String, List<String>> entry : map.entrySet()) {
        	if(i >= rows+1) {
				break;
			}
        	row = sheet.createRow(i);
        	List<String> columnsValue = entry.getValue();
        	XSSFCell cell;
        	int j = 0;
        	for (String string : columnsValue) {
				if(j >= columns) {
					break;
				}
        		cell = row.createCell(j);
				cell.setCellValue(string);
				j++;
			}
        	i++;
		}
        return wb;
	}
	
	public static void exportExcelXlsx(HttpServletResponse response,XSSFWorkbook wb) {
		OutputStream out = null;
        try{  
        	out = response.getOutputStream();
        	SimpleDateFormat fms = new SimpleDateFormat("yyyyMMddHHmmss");
        	Date date = new Date();
            String fileName = fms.format(date)+".xlsx";// 文件名  
//            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setContentType("application/x-msdownload");  
            response.setHeader("Content-Disposition", "attachment; filename="  
                                                    + URLEncoder.encode(fileName, "UTF-8"));  
            wb.write(out);  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }
	}
	
	public static void exportExcelXls(HttpServletResponse response,HSSFWorkbook wb) {
		OutputStream out = null;
        try{  
        	out = response.getOutputStream();
        	SimpleDateFormat fms = new SimpleDateFormat("yyyyMMddHHmmss");
        	Date date = new Date();
            String fileName = fms.format(date)+".xls";// 文件名  
//            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setContentType("application/x-msdownload");  
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
//		Map<String, List<String>> map = new HashMap<>();
//		for (int i = 0; i < 2; i++) {
//			ArrayList<String> row = new ArrayList<String>();
//			for (int j = 0; j < 3; j++) {
//				row.add((i+1)+"-"+(j+1));
//			}
//		}
//		XSSFWorkbook wb = ExportCommonExcelUtil.createExcelXlsx(2, 3, map, "测试");
//		ExportCommonExcelUtil.exportExcelXlsx(response, wb);
	}
}
