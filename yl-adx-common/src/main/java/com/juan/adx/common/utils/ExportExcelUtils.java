package com.juan.adx.common.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;

import com.juan.adx.model.dto.manage.DataStatisticsSlotDailyDto;

public class ExportExcelUtils {

    /**
     * @param workbook
     * @param sheetTitle （sheet的名称）
     * @param headers    （表格的标题）
     * @param result     （表格的数据）
     * @throws Exception
     * @Title: exportExcel
     * @Description: 导出Excel的方法
     */
    public static void exportExcel(HSSFWorkbook workbook, String sheetTitle, List<String> headers, List<List<String>> result) {
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(sheetTitle);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.PALE_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        font.setCharSet(HSSFFont.SYMBOL_CHARSET);
        // 把字体应用到当前的样式
        style.setFont(font);

        // 指定当单元格内容显示不下时自动换行
        style.setWrapText(true);

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers.get(i));
            cell.setCellValue(text.toString());
        }
        // 遍历集合数据，产生数据行
        if (result != null) {
            int index = 1;
            for (List<String> m : result) {
                row = sheet.createRow(index);
                int cellIndex = 0;
                for (String str : m) {
                    HSSFCell cell = row.createCell(cellIndex);
                    cell.setCellValue(String.valueOf(str));
                    cellIndex++;
                }
                index++;
            }
        }
    }


    /**
     * 设置表格样式
     *
     * @param workbook      表格
     * @param sheetNo       要设置的sheet页码，从0开始
     * @param headRowCnt    需要设置头部样式的行数，从第0行开始
     * @param columnsWidths 要设置的每一列的宽度
     * @param headStyle     头部样式
     * @param contentStyle  正式样式
     */
    public static void setExcelStyle(HSSFWorkbook workbook, int sheetNo, int headRowCnt, List<Integer> columnsWidths, HSSFCellStyle headStyle, HSSFCellStyle contentStyle) {

        HSSFSheet sheet = workbook.getSheetAt(sheetNo);
        int colNums = columnsWidths.size();

        //设置头部样式
        for (int rowNo = 0; rowNo < headRowCnt; rowNo++) {
            HSSFRow row = sheet.getRow(rowNo);
            row.setHeightInPoints(25);
            for (int colNo = 0; colNo < colNums; colNo++) {
                row.getCell(colNo).setCellStyle(headStyle);
            }
        }

        //设置每一列宽度
        for (int colIndex = 0; colIndex < colNums; colIndex++) {
            int colWidth = columnsWidths.get(colIndex);
            sheet.setColumnWidth(colIndex, colWidth);
        }

        //设置内容样式
        int totalRowCnt = sheet.getPhysicalNumberOfRows();
        for (int rowNo = headRowCnt; rowNo < totalRowCnt; rowNo++) {
            HSSFRow row = sheet.getRow(rowNo);
            for (int colNum = 0; colNum < colNums; colNum++) {
                row.getCell(colNum).setCellStyle(contentStyle);
            }
        }

    }

    /**
     * 设置表格样式
     *
     * @param workbook      表格
     * @param sheetNo       要设置的sheet页码，从0开始
     * @param headRowCnt    需要设置头部样式的行数，从第0行开始
     * @param columnsWidths 要设置的每一列的宽度
     * @param headStyle     头部样式
     * @param contentStyle  正式样式
     */
    public static void setExcelStyleHasBlank(HSSFWorkbook workbook, int sheetNo, int headRowCnt, List<Integer> columnsWidths, HSSFCellStyle headStyle, HSSFCellStyle contentStyle) {

        HSSFSheet sheet = workbook.getSheetAt(sheetNo);
        int colNums = columnsWidths.size();

        //设置头部样式
        for (int rowNo = 0; rowNo < headRowCnt; rowNo++) {
            HSSFRow row = sheet.getRow(rowNo);
            row.setHeightInPoints(25);
            for (int colNo = 0; colNo < colNums; colNo++) {
                row.getCell(colNo).setCellStyle(headStyle);
            }
        }

        //设置每一列宽度
        for (int colIndex = 0; colIndex < colNums; colIndex++) {
            int colWidth = columnsWidths.get(colIndex);
            sheet.setColumnWidth(colIndex, colWidth);
        }

        //设置内容样式
        int totalRowCnt = sheet.getPhysicalNumberOfRows();
        for (int rowNo = headRowCnt; rowNo < totalRowCnt; rowNo++) {
            HSSFRow row = sheet.getRow(rowNo);
            for (int colNum = 0; colNum < colNums; colNum++) {
                if(row.getCell(colNum) == null){
                    break;
                }
                row.getCell(colNum).setCellStyle(contentStyle);
            }
        }

    }

    public static void exportExcelSheet(Workbook workbook, List<List<String>> result, int index) {
        Sheet sheet = workbook.getSheetAt(0);
        if (result != null) {
            for (List<String> m : result) {
                Row row = sheet.createRow(index);
                row.setHeightInPoints(20);//设置行高
                int cellIndex = 0;
                for (String str : m) {
                    Cell cell = row.createCell(cellIndex);
                    cell.setCellValue(str);
                    cellIndex++;
                }
                index++;
            }
        }
    }

    public static void exportExcelSheet(Workbook workbook, List<List<String>> result, int sheetIndex, int rowIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        if (result != null) {
            for (List<String> m : result) {
                Row row = sheet.createRow(rowIndex);
                row.setHeightInPoints(20);//设置行高
                int cellIndex = 0;
                for (String str : m) {
                    Cell cell = row.createCell(cellIndex);
                    cell.setCellValue(str);
                    cellIndex++;
                }
                rowIndex++;
            }
        }
    }

    public static void exportExcelCommonHead(Workbook workbook, String sheetTitle, List<String> headers) {
        exportExcelCommonHead(workbook, sheetTitle, headers, 0);
    }

    public static void exportExcelCommonHead(Workbook workbook, String sheetTitle, List<String> headers, int sheetIndex) {
        exportExcelCommonHead(workbook, sheetTitle, headers, sheetIndex, 0);
    }
    

    public static void exportExcelCommonHead(Workbook workbook, String sheetTitle, List<String> headers, int sheetIndex, int rowIndex) {
        Sheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetIndex, sheetTitle);
        CellStyle style = workbook.createCellStyle();
        //设置单元格背景颜色，使用自定义的RGB颜色值
        XSSFColor color = new XSSFColor(new java.awt.Color(169, 208, 142), new DefaultIndexedColorMap());
        style.setFillForegroundColor(color);
        //设置单元格背景颜色，使用颜色枚举类
        //style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.PALE_BLUE.getIndex());
		//style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置单元格上、下、左、右的边框
		/*style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);*/
        style.setAlignment(HorizontalAlignment.LEFT);//设置单元格内容左右对齐样式
        style.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格内容垂直对齐样式
        //设置字体样式
        Font font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        font.setFontName("等线");
        style.setFont(font);
        style.setWrapText(true);
        Row row = sheet.createRow(rowIndex);
        row.setHeightInPoints(20);//设置行高
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers.get(i));
            cell.setCellValue(text.toString());
            sheet.setColumnWidth(i, text.getString().length() * 1000);// 设置第i列宽度为n个字符
        }
    }
    
    
    
    public static void main(String[] args) {
		List<DataStatisticsSlotDailyDto> dataList = new ArrayList<DataStatisticsSlotDailyDto>();
		DataStatisticsSlotDailyDto dailyDto = new DataStatisticsSlotDailyDto();
		dailyDto.setId(10);
		dailyDto.setDspPartnerName("测试预算方");
		dailyDto.setSspPartnerName("云聚合");
		dailyDto.setDate(LocalDateUtils.getNowSeconds());
		dailyDto.setAppName("测试应用");
		dailyDto.setAdSlotId(3);
		dailyDto.setAdSlotName("ssss吞吞吐吐");
		dailyDto.setBudgetId(6);
		dailyDto.setBudgetName("预算-0914");
		dailyDto.setSspEstimateIncome(0d);
		dailyDto.setDspEstimateIncome(0d);
		dataList.add(dailyDto);
		
		List<List<String>> result = new ArrayList<List<String>>();
		for (DataStatisticsSlotDailyDto dataDto : dataList) {
			result.add(
					Arrays.asList(
							String.valueOf(dataDto.getId()),
							dataDto.getDspPartnerName(),
							dataDto.getSspPartnerName(),
							LocalDateUtils.formatSecondsToString(dataDto.getDate(), LocalDateUtils.DATE_FORMATTER),
							dataDto.getAppName(),
							String.valueOf(dataDto.getAdSlotId()),
							dataDto.getAdSlotName(),
							String.valueOf(dataDto.getBudgetId()),
							dataDto.getBudgetName(),
							String.valueOf(dataDto.getSspEstimateIncome().longValue()),
							String.valueOf(dataDto.getDspEstimateIncome().longValue())
					));
		}
		Workbook  workbook = new SXSSFWorkbook();
		List<String> headers = new ArrayList<String>();
		headers.add("ID");
		headers.add("预算方名称");
		headers.add("流量方名称");
		headers.add("数据日期");
		headers.add("应用名称");
		headers.add("广告位ID");
		headers.add("广告位名称");
		headers.add("预算ID");
		headers.add("预算名称");
		headers.add("渠道预估收益");
		headers.add("广告主预估收益");
		List<String> enHeaders = new ArrayList<String>();
		enHeaders.add("ID");
		enHeaders.add("BUDGET_NAME");
		enHeaders.add("SSP_NAME");
		enHeaders.add("DATE");
		enHeaders.add("APP_NAME");
		enHeaders.add("SLOT_ID");
		enHeaders.add("SLOT_NAME");
		enHeaders.add("BUDGET_ID");
		enHeaders.add("BUDGET_NAME");
		enHeaders.add("SSP_ESTIMATE_INCOME");
		enHeaders.add("DSP_ESTIMATE_INCOME");
		result.add(0, enHeaders);
		ExportExcelUtils.exportExcelCommonHead(workbook, "PD广告收益", headers, 0, 0);
		ExportExcelUtils.exportExcelSheet(workbook, result ,0, 1);
		
		try {
			OutputStream outputStream = new FileOutputStream("C:\\Users\\Waker\\Desktop\\slot-daily-template.xlsx");
			workbook.write(outputStream);
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(workbook);
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		
	}
}
