package test;

import com.baizhi.cmfz.dao.GuruDao;
import com.baizhi.cmfz.dao.UserDao;
import com.baizhi.cmfz.entity.Guru;
import com.baizhi.cmfz.util.ExcelOutput;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class POITest extends AppTest{
    @Autowired
    private GuruDao guruDao;
    @Autowired
    private UserDao userDao;

    @Test
    public void CreateTableTest() throws Exception {
//        1. 创建一个文件对象（工作簿）
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
//        2. 创建工作表对象
        HSSFSheet sheet = hssfWorkbook.createSheet("guru");
        //        3. 创建一个行对象
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;

        // 将数据库查询的内容做成数组
        String[] item = new String[5];
        item[0] = new String("上师id");
        item[1] = new String("上师名字");
        item[2] = new String("上师图片路径");
        item[3] = new String("上师法号");
        item[4] = new String("上师状态");

        //        5. 在单元格中写入数据
        for (int i = 0; i < item.length; i++) {
            //        4. 创建一个单元格对象
            cell = row.createCell(i);
            cell.setCellValue(item[i]);
        }

        // 数据库里查询出来的结果，并且将所有字段转成String保存到二维数组中
        List<Guru> gurus = guruDao.getAllByLimit(0, 99, new Guru());
        String[][] gurus1 = new String[gurus.size()][5];

        // 循环将值赋值进去
        for (int i = 0; i < gurus.size(); i++) {
            gurus1[i][0] = gurus.get(i).getGuruId().toString();
            gurus1[i][1] = gurus.get(i).getGuruName();
            gurus1[i][2] = gurus.get(i).getGuruImage();
            gurus1[i][3] = gurus.get(i).getGuruNickname();
            gurus1[i][4] = gurus.get(i).getGuruStatus().toString();
        }

        for (int i = 1; i < gurus.size()+1; i++) {
//        3. 创建一个行对象
            row = sheet.createRow(i);
            for (int j = 0; j < 5; j++) {
//        4. 创建一个单元格对象
                cell = row.createCell(j);
//        5. 在单元格中写入数据

                cell.setCellValue(gurus1[i-1][j]);
            }
        }

//        6. 保存到本地 IO流
        hssfWorkbook.write(new FileOutputStream(new File("f://服务器/guru.xls")));
    }

    @Test
    public void tryTest() throws Exception{
        List<Guru> list = guruDao.getAllByLimit(0, 99, new Guru());
        POITry.ChangeToExcelTest(list, "123");
    }

    @Test
    public void excelUtilTest() throws Exception {
        ExcelOutput.ChangeToExcelTest(userDao.selectList(null), "user");
    }
}
