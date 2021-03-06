package test;

import com.baizhi.cmfz.dao.GuruDao;
import com.baizhi.cmfz.entity.Guru;
import com.baizhi.cmfz.util.ExcelName;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public class POIFullTest extends AppTest{

    @Autowired
    private GuruDao guruDao;

    @Test
    public void testExport3() throws Exception {
//        准备要填充的数据
        List<Guru> cmfzGurus = guruDao.getAllByLimit(0, 99, new Guru());

//        1. 创建一个文件对象（工作簿）
        HSSFWorkbook workbook = new HSSFWorkbook();
//        2. 创建工作表对象
        HSSFSheet sheet = workbook.createSheet("guru");

//        3. 创建并写入标题栏数据
        HSSFRow titleRow = sheet.createRow(0);

//        优化写入标题栏数据开始
//        1.拿到类对象
        Class<? extends Guru> titleClass = cmfzGurus.get(0).getClass();
//        2.拿到属性对象数组
        Field[] titleFields = titleClass.getDeclaredFields();
//        3.遍历数组拿到对应的注解
        for (int i = 0; i < titleFields.length; i++) {
            //4.拿到注解对象
            ExcelName annotation = titleFields[i].getAnnotation(ExcelName.class);

//            5.拿到注解中的值
            String title = annotation.name();
//            6.创建单元格
            HSSFCell cell = titleRow.createCell(i);
//            7.写入数据
            cell.setCellValue(title);
        }

//        优化写入标题栏数据结束

//        4.填充guru数据
        for (int i = 0; i < cmfzGurus.size(); i++) {
//            0.创建行
            HSSFRow row = sheet.createRow(i + 1);

//                反射代码开始
//            1.获取对象
            Guru cmfzGuru = cmfzGurus.get(i);
//            2.获取类对象
            Class<? extends Guru> guruClass = cmfzGuru.getClass();
//            3.获取属性数组
            Field[] fields = guruClass.getDeclaredFields();
            for (int j = 0; j < fields.length; j++) {
//                4.获取属性名
                String fieldName = fields[j].getName();
//                5.拼接出来get方法名
                String getName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
//                6.通过反射调用get方法
                Object invoke = guruClass.getDeclaredMethod(getName, null).invoke(cmfzGuru, null);
//                反射代码结束

//                7.写入单元格
                HSSFCell cell = row.createCell(j);
//                8.类型判断
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
        workbook.write(new FileOutputStream(new File("F://服务器/guruall.xls")));

    }

}
