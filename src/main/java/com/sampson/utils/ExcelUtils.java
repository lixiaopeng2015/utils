package main.java.com.sampson.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * ProjectName: util
 * CreateUser:  lixiaopeng
 * CreateTime : 2016/5/17 15:14
 * Email: lixiaopeng913@sina.com
 * ModifyUser: lixiaopeng
 * Class Description:
 * To change this template use File | Settings | File Template
 */
public class ExcelUtils {
    private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
    public static final String xls = ".xlsx";

    /**
     * 采用XSSF方式导出excel（2007以上的版本采用此种方式）
     *
     * @param sheetName
     * @param titles
     * @param dataSet
     */
    public void exportExcel(String sheetName, String[] titles, List<Object[]> dataSet){
        //声明工作薄
        Workbook workbook = new XSSFWorkbook();

        Map<String,CellStyle> styles = createStyles(workbook);
        Sheet sheet = workbook.createSheet(sheetName);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        sheet.setDefaultColumnWidth(18);

        Row headerRow = sheet.createRow(0);
        //设置行高
        headerRow.setHeightInPoints(25.75f);
        Cell headerCell;
        for (int i = 0; i < titles.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(titles[i]);
            headerCell.setCellStyle(styles.get("header"));
        }

        //写入excel
        writeExcel(sheet, styles, dataSet);

        //开始执行导出操作
        String xlsFile = sheetName+"_"+System.currentTimeMillis()+xls;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(xlsFile);
            workbook.write(out);
            out.close();
            workbook.close();
        } catch (FileNotFoundException e) {
            logger.error("Not Found" + xlsFile+" exception " + e.getMessage() + " at " + ExcelUtils.class);
        } catch (IOException e) {
            logger.error("IO Exception" + e.getMessage() + " at " + ExcelUtils.class);
        }

    }

    /**
     * create a library of cell styles
     */
    private static Map<String, CellStyle> createStyles(Workbook workbook){
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style;
        //表头样式
        Font headerFont = workbook.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerFont.setFontName("微软雅黑");
        headerFont.setFontHeightInPoints((short)10);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        style = createBorderedStyle(workbook);
        //单元格居中
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        //单元格背景色
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        style.setWrapText(false);//不需要自动换行，如果需要自动换行，设置为true
        styles.put("header", style);

        //普通单元格样式
        Font normalFont = workbook.createFont();
        normalFont.setFontHeight((short)5);
        style = createBorderedStyle(workbook);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setWrapText(false);
        createBorderedStyle(workbook);
        styles.put("cell", style);

        return styles;
    }

    //创建带黑边框样式的单元格
    private static CellStyle createBorderedStyle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }

    //写入excel
    private void writeExcel(Sheet sheet,Map<String, CellStyle> styles,List<Object[]> dataSet){
        Row row;
        Cell cell;
        int rowNum = 1;
        for (int i = 0; i < dataSet.size(); i++,rowNum++) {
            row = sheet.createRow(rowNum);
            if (null == dataSet.get(i)) {
                continue;
            }
            //开始写入数据
            for (int j = 0; j < dataSet.get(i).length; j++) {
                cell = row.createCell(j);
                cell.setCellValue(dataSet.get(i)[j].toString());
                //设置样式
                cell.setCellStyle(styles.get("cell"));
            }
        }
    }

}


