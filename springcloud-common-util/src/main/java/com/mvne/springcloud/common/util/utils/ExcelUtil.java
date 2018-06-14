package com.mvne.springcloud.common.util.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	
	public static Map<Object, List<String[]>> imports(InputStream inputStream, String fileType) {
		
		Map<Object, List<String[]>> ExcelMap = new HashMap<Object, List<String[]>>();
		try {
			Workbook workbook = null;
			if (fileType.equals(".xls")) {
				workbook = new HSSFWorkbook(inputStream);
			} else if (fileType.equals(".xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			}

			// 循环工作表Sheet
			for (int sheetCount = 0; sheetCount < workbook.getNumberOfSheets(); sheetCount++) {
				Sheet sheet = workbook.getSheetAt(sheetCount);
				if (sheet == null) {
					continue;
				}

				// 循环行Row
				List<String[]> Rowlist = new ArrayList<String[]>();
				for (int rowCount = 2; rowCount <= sheet.getLastRowNum(); rowCount++) {
					Row row = sheet.getRow(rowCount);
					if (row == null) {
						continue;
					}

					// 循环列Cell
					List<String> cellList = new ArrayList<String>();
					for (int cellCount = 0; cellCount <= row.getLastCellNum(); cellCount++) {
						Cell cell = row.getCell(cellCount);
						String cellData = cell == null ? "" : cell.toString();
						System.err.println(cellData);
						cellList.add(cellData);
					}
					Rowlist.add(cellList.toArray(new String[cellList.size()]));
				}
				ExcelMap.put(sheet.getSheetName(), Rowlist);
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ExcelMap;
	}
	
	public static List<Map<String, String>> getExcelValue(InputStream in,String[] columns,String extName) {
        Workbook wb =null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String,String>> list = null;
        String cellData = null;
        wb = readExcel(in,extName);
        if(wb != null){
            //用来存放表中数据
            list = new ArrayList<Map<String,String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(1);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            for (int i = 2; i<rownum; i++) {
                Map<String,String> map = new LinkedHashMap<String,String>();
                row = sheet.getRow(i);
                int numOfBlankCell = CheckRowNull(row,colnum);
                System.err.println("该行为空的但愿格数目为："+numOfBlankCell+",即改行数据为空，结束循环，不再往下读取。");
                if(numOfBlankCell >= colnum) {
                	break;
                }
                if(row !=null){
                    for (int j=0;j<colnum;j++){
//                        cellData = (String) getCellFormatValue(row.getCell(j));
                        cellData = getCellValue(row.getCell(j));
//                    	System.out.println(row.getCell(j).getCellType());
                        map.put(columns[j], cellData);
                    }
                }else{
                    break;
                }
                list.add(map);
            }
        }
        //遍历解析出来的list
//        for (Map<String,String> map : list) {
//            for (Entry<String,String> entry : map.entrySet()) {
//                System.out.print(entry.getKey()+":"+entry.getValue()+",   ");
//            }
//            System.out.println();
//        }
		return list;

    }
    //读取excel
    public static Workbook readExcel(InputStream in,String extName){
        Workbook wb = null;
        if(in==null){
            return null;
        }
        String extString = extName.substring(extName.lastIndexOf("."));
        try {
            if(".xls".equals(extString)){
                return wb = new HSSFWorkbook(in);
            }else if(".xlsx".equals(extString)){
                return wb = new XSSFWorkbook(in);
            }else{
                return wb = null;
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
    public static Object getCellFormatValue(Cell cell){
        Object cellValue = null;
        if(cell!=null){
            //判断cell类型
            switch(cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC:{
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            }
            case Cell.CELL_TYPE_FORMULA:{
                //判断cell是否为日期格式
                if(DateUtil.isCellDateFormatted(cell)){
                    //转换为日期格式YYYY-mm-dd
                    cellValue = cell.getDateCellValue();
                }else{
                    //数字
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            }
            case Cell.CELL_TYPE_STRING:{
                cellValue = cell.getRichStringCellValue().getString();
                break;
            }
            default:
                cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }
    
    public static String getCellValue(Cell cell) {
        if (cell == null)
            return "";
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return cell.getCellFormula();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            short format = cell.getCellStyle().getDataFormat();
            System.out.println("format:"+format+"--------value:"+cell.getNumericCellValue());
            SimpleDateFormat sdf = null;
            //22d的Excel单元格时间格式为yyyy/MM/dd HH:mm,180为WPS编辑的时间格式为yyyy-MM-dd HH:mm:ss
            if (format == 180 || format == 22 || format == 14 || format == 31 || format == 57 || format == 58  
                    || (176<=format && format<=178) || (182<=format && format<=196) 
                    || (210<=format && format<=213) || (208==format ) ) { // 日期
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            } else if (format == 20 || format == 32 || format==183 || (200<=format && format<=209) ) { // 时间
                sdf = new SimpleDateFormat("HH:mm");
            } else { // 不是日期格式
            	DecimalFormat df = new DecimalFormat("0");    
            	String cellText = df.format(cell.getNumericCellValue());
            	return cellText;
//                return String.valueOf(cell.getNumericCellValue());
            }
            double value = cell.getNumericCellValue();
            Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
            if(date==null || "".equals(date)){
                return "";
            }
            String result="";
            try {
                result = sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
            return result;
        }
        return "";
    }
    
    //判断行为空  
    private static int CheckRowNull(Row row,int numOfColumn){  
    	int num = 0;  
    	/*Iterator<Cell> cellItr =row.iterator();  
    	while(cellItr.hasNext()){  
    		Cell c =cellItr.next();                          
    		if(c.getCellType() == HSSFCell.CELL_TYPE_BLANK){  
    			num++;  
    		}  
    	}*/ 
    	for (int i = 0; i < numOfColumn; i++) {
    		Cell cell = row.getCell(i);
			if(cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK 
					|| StringUtils.isEmpty(getCellValue(cell))) {
				num++;
			}
		}
    	return num;  
    } 
}
