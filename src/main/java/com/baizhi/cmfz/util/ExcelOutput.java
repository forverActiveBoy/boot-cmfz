package com.baizhi.cmfz.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public class ExcelOutput {
    /**
     * 将数据库查询的所有数据以excel的形式导出，路径写死在88行，自行修改
     * @param list 查询数据库的结果
     * @param s 想保存的文件名字
     * @param <T>
     * @throws Exception
     */
    public static <T> void ChangeToExcelTest(List<T> list, String s)  throws Exception{

//        创建一个文件对象（工作簿）
        HSSFWorkbook workbook = new HSSFWorkbook();
//        创建工作表对象
        HSSFSheet sheet = workbook.createSheet();

//        创建并写入标题栏数据
        HSSFRow titleRow = sheet.createRow(0);

//##########优化写入标题栏数据开始##########
//        拿到类对象
        Class<?> titleClass = list.get(0).getClass();
//        拿到属性对象数组
        Field[] titleFields = titleClass.getDeclaredFields();
//        遍历数组拿到对应的注解
        for (int i = 0; i < titleFields.length; i++) {
            // 拿到注解对象
            ExcelName annotation = titleFields[i].getAnnotation(ExcelName.class);

//            拿到注解中的值
            String title = annotation.name();
//            创建单元格
            HSSFCell cell = titleRow.createCell(i);
//            写入数据
            cell.setCellValue(title);
        }
//##########优化写入标题栏数据结束##########

//        填充数据
        for (int i = 0; i < list.size(); i++) {
//            0.创建行
            HSSFRow row = sheet.createRow(i + 1);

//##########反射代码开始##########
//            获取对象
            T t = list.get(i);
//            获取类对象
            Class<?> aClass = t.getClass();
//            获取属性数组
            Field[] fields = aClass.getDeclaredFields();
            for (int j = 0; j < fields.length; j++) {
//                获取属性名
                String fieldName = fields[j].getName();
//                拼接出来get方法名
                String getName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
//                通过反射调用get方法
                Object invoke = aClass.getDeclaredMethod(getName, null).invoke(t, null);
//##########反射代码结束##########

//                写入单元格
                HSSFCell cell = row.createCell(j);
//                类型判断
                if(invoke instanceof Integer){
                    cell.setCellValue((Integer) invoke);
                }else if(invoke instanceof Date){
                    cell.setCellValue((Date) invoke);
                }else {
                    cell.setCellValue((String) invoke);
                }

            }

        }

//        6. 保存到本地 IO流
        workbook.write(new FileOutputStream(new File("F://服务器/"+ s +".xls")));

    }
}
