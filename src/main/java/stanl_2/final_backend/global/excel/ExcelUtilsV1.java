package stanl_2.final_backend.global.excel;

import com.sun.nio.sctp.IllegalReceiveException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ExcelUtilsV1 implements ExcelSupportV1{

    private static final Integer MAX_ROW = 5000;

    // 메타데이터 타입, 메타 데이터, 파일이름, 응답
    @Override
    public void download(Class<?> clazz, List<?> data, String fileName, HttpServletResponse response){

        try(SXSSFWorkbook workbook = new SXSSFWorkbook()){

            Integer loop = 1;
            Integer listSize = data.size();

            // 하나의 sheet당 10000개의 데이터를 그려줌
            for (Integer start = 0; start < listSize; start+= MAX_ROW) {

                Integer nextPage = MAX_ROW * loop;
                if(nextPage > listSize){
                    nextPage = listSize - 1;
                }

                List<?> datas = new ArrayList<>(data.subList(start, nextPage));
                getWorkBook(clazz, workbook, start, findHeaderNames(clazz), datas, listSize);
                datas.clear();
                loop++;
            }

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");

            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch(IOException | IllegalAccessException e){
            log.error("Excel 다운로드 Error 메세지: {}", e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private SXSSFWorkbook getWorkBook(Class<?> clazz, SXSSFWorkbook workbook, Integer start,
                                      List<String> headerNames, List<?> datas, Integer listSize)
                                      throws IllegalAccessException, IOException{

        // 각 sheet당 MAX_ROW개씩 생성
        String sheetName = "Sheet" + (start / MAX_ROW + 1);

        Sheet sheet = ObjectUtils.isEmpty(workbook.getSheet(sheetName))
                ? workbook.createSheet(sheetName) : workbook.getSheet(sheetName);
        sheet.setDefaultColumnWidth(10);    // 디폴트 너비 설정
//        sheet.setDefaultRowHeight((short) 500);      // 디폴트 높이 설정

        Row row = null;
        Cell cell = null;
        Integer rowNo = start % listSize; // 0, 10000, 20000, 30000 -> 10000(maxSize)씩 증가

        row = sheet.createRow(0);
        createHeaders(workbook, row, cell, headerNames);        // sheet의 헤더 생성
        createBody(clazz, datas, sheet, row, cell, start);       // sheet의 body 생성

        // 주기적으로 flush 진행
        if (rowNo % MAX_ROW == 0){
            ((SXSSFSheet) sheet).flushRows(MAX_ROW);
        }

        return workbook;
    }

    private void createBody(Class<?> clazz, List<?> datas, Sheet sheet, Row row, Cell cell, Integer start)
                            throws IllegalAccessException, IOException{

        Integer startRow = 0;

        for (Object o : datas){
            List<Object> fields = findFieldValue(clazz, o);
            row = sheet.createRow(++startRow);
            for (Integer i = 0, fieldSize = fields.size() ; i < fieldSize; i++) {
                cell = row.createCell(i);
                cell.setCellValue(String.valueOf(fields.get(i)));
            }

            // 주기적인 flush 진행
            if (start % MAX_ROW == 0){
                ((SXSSFSheet) sheet).flushRows(MAX_ROW);
            }
        }
    }

    // 데이터의 값을 추출하는 메소드
    private List<Object> findFieldValue(Class<?> clazz, Object o) throws IllegalAccessException {
        List<Object> result = new ArrayList<>();
        for (Field field: clazz.getDeclaredFields()){
            field.setAccessible(true);
            result.add(field.get(o));
        }

        return result;
    }

    private void createHeaders(SXSSFWorkbook workbook, Row row, Cell cell, List<String> headerNames) {

        // header의 폰트 스타일
        Font font = workbook.createFont();
        font.setBold(true);  // 글자 굵게

        // header의 cell 스타일
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);           // 가로 가운데 정렬
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);     // 세로 가운데 정렬

        // cell 테두리 설정
        headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
        headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
        headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerCellStyle.setFont(font);

        for (Integer i = 0, size = headerNames.size() ; i < size; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(headerCellStyle);
            cell.setCellValue(headerNames.get(i));
        }
    }

    // 엑셀의 헤더 이름을 찾는 로직
    private List<String> findHeaderNames(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ExcelColumnName.class))
                .map(field -> field.getAnnotation(ExcelColumnName.class).name())
                .collect(Collectors.toList());
    }
}
