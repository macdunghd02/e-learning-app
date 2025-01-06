package com.mdd.ela.util;



import com.mdd.ela.exception.ExcelCellValidationException;
import com.mdd.ela.exception.ExcelRowValidationException;
import com.mdd.ela.exception.ExcelValidationException;
import com.mdd.ela.exception.ElaValidateException;
import com.mdd.ela.util.constants.ConstantMessages;
import com.mdd.ela.util.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.util.ReflectionUtils.handleReflectionException;

@Slf4j
public class ExcelUtil {
    private ExcelUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void writeHeader(XSSFWorkbook workbook, XSSFSheet sheet, List<String> list) {
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(Constants.EXCEL_DEFAULT_FONT_SIZE);
        style.setFont(font);
        row.setHeight((short) 0);
        for (int i = 0; i < list.size(); i++) {
            createCell(sheet, row, i, list.get(i), style);
        }
    }

    public static void createCell(XSSFSheet sheet, Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell != null) {
            if (valueOfCell instanceof Integer) {
                cell.setCellValue((Integer) valueOfCell);
            } else if (valueOfCell instanceof Long) {
                cell.setCellValue((Long) valueOfCell);
            } else if (valueOfCell instanceof String) {
                cell.setCellValue((String) valueOfCell);
            } else if (valueOfCell instanceof Enum) {
                cell.setCellValue(((Enum) valueOfCell).name());
            } else if (valueOfCell instanceof Float) {
                cell.setCellValue(((Float) valueOfCell));
            } else if (valueOfCell instanceof BigDecimal) {
                cell.setCellValue(((BigDecimal) valueOfCell).doubleValue());
            } else if (valueOfCell instanceof Boolean) {
                cell.setCellValue((Boolean) valueOfCell ? "true" : "false");
            } else if (valueOfCell instanceof Date) {
                cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format((Date) valueOfCell));
            } else {
                cell.setCellValue(valueOfCell.toString());
            }
        } else {
            cell.setCellValue("");
        }
        cell.setCellStyle(style);
    }

    public static XSSFWorkbook createNewSheetTemplate(XSSFWorkbook workbook, Map<String, List<String>> sheetAndColumns){
        for (Map.Entry<String, List<String>> entry : sheetAndColumns.entrySet()) {
            String sheetName = entry.getKey();
            List<String> columns = entry.getValue();
            XSSFSheet sheet = workbook.createSheet(sheetName);
            writeHeader(workbook, sheet, columns);
            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setFontHeight(Constants.EXCEL_DEFAULT_FONT_SIZE);
            style.setFont(font);
            style.setFont(font);
            sheet.autoSizeColumn(0);
        }
        return workbook;
    }

    public static XSSFWorkbook createTemplateExcel(String sheetName, List<String> columnName){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
        writeHeader(workbook, sheet, columnName);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(Constants.EXCEL_DEFAULT_FONT_SIZE);
        style.setFont(font);
        style.setFont(font);
        sheet.autoSizeColumn(0);
        return workbook;
    }

    public static XSSFWorkbook createExcelFromObject(String sheetName,
                                                     String[][] mapObjectFieldAndColumnName,
                                                     List<Object> objectDataList,
                                                     Class<?> objectClass
    ) {
        LinkedHashMap<String, String> linkedHashMap = twoDimensionalArrayToLinkedHashMap(mapObjectFieldAndColumnName);
        // Get field names
        Set<String> fieldNames = linkedHashMap.keySet();
        List<Field> fieldList;

        List<Field> objectClassFields = new ArrayList<>();
        for (Class<?> c = objectClass; c != null; c = c.getSuperclass()) {
            objectClassFields.addAll(Arrays.asList(c.getDeclaredFields()));
        }

        Map<String, Field> totalFields = new HashMap<>();
        objectClassFields.forEach(f -> totalFields.put(f.getName(), f));


        //Get actual column names that sent to response
        fieldList = fieldNames.stream().map(totalFields::get).collect(Collectors.toList());
        fieldList = fieldList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        List<String> columnNames = fieldList.stream().map(field -> linkedHashMap.get(field.getName())).collect(Collectors.toList());

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);

        //Create header row
        writeHeader(workbook, sheet, columnNames);

        //Create row data
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(Constants.EXCEL_DEFAULT_FONT_SIZE);
        style.setFont(font);
        style.setFont(font);
        Row row;
        int rowIndex = 1;
        for (Object rowData : objectDataList
        ) {
            row = sheet.createRow(rowIndex++);
            for (int i = 0; i < fieldList.size(); i++) {
                Object cellValue;
                try {
                    cellValue = FieldUtils.readField(fieldList.get(i), rowData, true);
                } catch (IllegalAccessException e) {
                    cellValue = null;
                }
                createCell(sheet, row, i, cellValue, style);
            }
        }
        return workbook;
    }

    public static XSSFWorkbook createExcelFromColumnData(XSSFSheet sheet, String columnName, int columnIndex, List<Object> columnDataList) {
        Row row;
        XSSFWorkbook workbook = sheet.getWorkbook();

        //Create header
        writeHeader(workbook, sheet, List.of(columnName));

        //Create column data
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(Constants.EXCEL_DEFAULT_FONT_SIZE);
        style.setFont(font);

        int rowIndex = 1;
        for (Object rowData : columnDataList
        ) {
            row = sheet.createRow(rowIndex++);
            createCell(sheet, row, columnIndex, rowData, style);
        }

        return workbook;
    }

    private static LinkedHashMap<String, String> twoDimensionalArrayToLinkedHashMap(String[][] arrays) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (String[] i : arrays) {
            map.put(i[0], i[1]);
        }
        return map;
    }

    public static <T> List<T> convertExcelFileToObjectList(MultipartFile file,
                                                           String[][] mappingColumnArray,
                                                           Class<T> clazz,
                                                           int firstRowOfData
    ) throws IOException, ExcelValidationException {
        InputStream fileInputStream = new BufferedInputStream(file.getInputStream());
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null || sheet.getRow(0) == null) {
            throw new ElaValidateException(ConstantMessages.EMPTY_FILE_UPLOADED);
        }
        List<Row> rowList = IteratorUtils.toList(sheet.rowIterator());
        if (rowList.size() < firstRowOfData) {
            throw new ExcelValidationException(0, 0, new ArrayList<>());
        }

        //Get column names
        Row headerRow = rowList.get(firstRowOfData - 1);
        LinkedHashMap<String, String> columnNameMapping = twoDimensionalArrayToLinkedHashMap(mappingColumnArray);
        List<String> correctColumnKoreanNames = List.copyOf(columnNameMapping.keySet());
        correctColumnKoreanNames = correctColumnKoreanNames.stream().filter(s -> !StringUtils.isBlank(s)).collect(Collectors.toList());

        Map<String, Integer> fieldNameAndColumnIndexesMap = new HashMap<>();
        // Get columns' index
        for (int i = headerRow.getFirstCellNum(); i < headerRow.getLastCellNum(); i++) {
            String columnName = headerRow.getCell(i).getStringCellValue();
            if (correctColumnKoreanNames.contains(columnName)) {
                fieldNameAndColumnIndexesMap.put(columnNameMapping.get(columnName), i);
            }
        }

        List<T> object = new ArrayList<>();
        // Convert each row to appropriate object
        List<ExcelRowValidationException> validationErrorList = new ArrayList<>();
        for (int i = firstRowOfData; i < rowList.size(); i++) {
            if(isEmptyRow(rowList.get(i))) continue;
            T insertedObject = null;
            try {
                insertedObject
                        = rowToObject(rowList.get(i), fieldNameAndColumnIndexesMap, clazz, i + 1);
                object.add(insertedObject);
            } catch (ExcelRowValidationException e) {
                validationErrorList.add(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (!validationErrorList.isEmpty()) {
            throw new ExcelValidationException(validationErrorList.size(), object.size(), validationErrorList);
        }

        return object;
    }

    public static <T> T rowToObject(Row row,
                                    Map<String, Integer> fieldNameAndColumnIndexesMap,
                                    Class<T> clazz,
                                    int rowIndex
    ) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ExcelRowValidationException {
        //Create new object of defined type
        Constructor<T> constructor = clazz.getConstructor();
        T object = constructor.newInstance();

        List<Field> fields = new ArrayList<>();
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        List<ExcelCellValidationException> validationErrorList = new ArrayList<>();
        for (Field field : fields
        ) {
            if (fieldNameAndColumnIndexesMap.containsKey(field.getName())) {
                try {
                    int columnIndex = fieldNameAndColumnIndexesMap.get(field.getName());
                    Object actualCellValue = getCelValueByFieldType(row.getCell(columnIndex), field.getType(), rowIndex, columnIndex);
//                    Map<String, String> errMap = ValidationUtil.validateProperty(clazz, field.getName(), actualCellValue);
//                    errMap.values().forEach(s -> {
////                        String message = MessageFormat.format(ConstantMessages.CELL_EXCEL_ERROR_MESSAGE, rowIndex-1, columnIndex + 1, s);
//                        validationErrorList.add(new ExcelCellValidationException(rowIndex, field.getName(), s));
//                    });
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, object, actualCellValue);
                } catch (ExcelCellValidationException e) {

                }
            }
        }
        if (!validationErrorList.isEmpty()) {
            throw new ExcelRowValidationException(rowIndex, validationErrorList, row.iterator());
        }
        return object;
    }

    public static void setField(Field field, @Nullable Object target, @Nullable Object value) {
        try {
            if (field.getType() == int.class && value instanceof String) {
                int intValue = Integer.parseInt((String) value);
                field.set(target, intValue);
            } else {
                field.set(target, value);
            }
        } catch (IllegalAccessException ex) {
            handleReflectionException(ex);
        }
    }

    public static <T> T rowToObjectWithAllFields(Row row,
                                                 Map<String, Integer> fieldNameAndColumnIndexesMap,
                                                 Class<T> clazz,
                                                 int rowIndex
    ) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ExcelRowValidationException {
        // Create new object of defined type
        Constructor<T> constructor = clazz.getConstructor();
        T object = constructor.newInstance();

        List<Field> fields = new ArrayList<>();
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        List<ExcelCellValidationException> validationErrorList = new ArrayList<>();
        for (Field field : fields
        ) {
            if (fieldNameAndColumnIndexesMap.containsKey(field.getName())) {
                try {
                    int columnIndex = fieldNameAndColumnIndexesMap.get(field.getName());
                    Object actualCellValue = getCelValueByFieldType(row.getCell(columnIndex), field.getType(), rowIndex, columnIndex);
                    Map<String, String> errMap = ValidationUtil.validateProperty(clazz, field.getName(), actualCellValue);
                    errMap.values().forEach(s -> {
                        String message = MessageFormat.format(ConstantMessages.CELL_EXCEL_ERROR_MESSAGE, rowIndex, columnIndex + 1, s);
                        validationErrorList.add(new ExcelCellValidationException(rowIndex, field.getName(), message));
                    });
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, object, actualCellValue);
                } catch (ExcelCellValidationException e) {
                    // ignore
                }
            }
        }
        if (!validationErrorList.isEmpty()) {
            throw new ExcelRowValidationException(rowIndex, validationErrorList, row.iterator());
        }
        return object;
    }

    private static Object getCelValueByFieldType(Cell cell,
                                                 Class clazz,
                                                 int rowIndex,
                                                 int columnIndex
    ) throws ExcelValidationException {
        try {
            DataFormatter formatter = new DataFormatter();
            String cellValue = formatter.formatCellValue(cell);
            if (clazz.equals(Date.class)) {
                if (cellValue == null || cellValue.equals("")) {
                    return null;
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                return new Date(format.parse(cellValue).getTime());
            } else if (clazz.equals(Boolean.class)) {
                return Boolean.valueOf(cellValue);
            } else if (clazz.equals(Long.class)) {
                return Double.valueOf(cellValue).longValue();
            } else if (clazz.equals(long.class)) {
                return Double.valueOf(cellValue).longValue();
            } else if (clazz.equals(int.class)) {
                return Double.valueOf(cellValue).intValue();
            } else if (clazz.equals(Integer.class)) {
                return Double.valueOf(cellValue).intValue();
            } else if (clazz.equals(BigDecimal.class)) {
                return BigDecimal.valueOf(Double.valueOf(cellValue));
            } else if (clazz.equals(String.class)) {
                return cellValue.isBlank() ? null : cellValue;
            } else {
                return cellValue;
            }
        } catch (ParseException e) {
//            String errorDetail = MessageFormat.format(ConstantMessages.CELL_EXCEL_ERROR_MESSAGE,
//                    String.valueOf(rowIndex), String.valueOf(columnIndex + 1),
//                    ConstantMessages.DATETIME_CAST_EXCEPTION_MESSAGE);
//            throw new ExcelCellValidationException(rowIndex, columnIndex + 1, "ERR0001", errorDetail);
        } catch (ClassCastException | NumberFormatException e) {
//            String errorDetail = MessageFormat.format(ConstantMessages.CELL_EXCEL_ERROR_MESSAGE,
//                    String.valueOf(rowIndex), String.valueOf(columnIndex + 1),
//                    ConstantMessages.INVALID_CELL_VALUE_TYPE_MESSAGE);
//            throw new ExcelCellValidationException(rowIndex, columnIndex + 1, "ERR0001", errorDetail);
        }
        return null;
    }

    public static boolean isEmptyRow(Row row) {
        if (row == null) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
                return false;
            }
        }
        return true;
    }

}
