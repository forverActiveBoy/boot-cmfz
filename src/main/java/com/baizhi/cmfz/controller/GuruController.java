package com.baizhi.cmfz.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baizhi.cmfz.entity.Guru;
import com.baizhi.cmfz.service.GuruService;
import com.baizhi.cmfz.util.ExcelOutput;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * (Guru)表控制层
 *
 * @author myself
 * @since 2019-01-05 19:52:27
 */
@Controller
@RequestMapping("/guru")
public class GuruController {
    /**
     * 服务对象
     */
    @Resource
    private GuruService guruService;

    /**
     * 通过主键查询单条数据
     */
    @RequestMapping("/getOne")
    @ResponseBody
    public Guru getOne(Integer id) {
        return this.guruService.getById(id);
    }
    
    /**
     * 通过实体作为筛选条件分页查询数据
     */
    @RequestMapping("/getAllGuruByLimit")
    @ResponseBody
    public Map getAllGuruByLimit(int page, int rows, Guru guru) {
        return this.guruService.getAllByLimit(page,rows,guru);
    }
    
    /**
     * 添加数据
     */
    @RequestMapping("/insertGuru")
    @ResponseBody
    public boolean insertGuru(MultipartFile name, Guru guru){
        try {
            //老名字
            String filename = name.getOriginalFilename();

            //2.通过时间戳 创建新名字
            filename=new Date().getTime()+"_"+filename;
            guru.setGuruImage(filename);

            //把文件写入服务器磁盘
            File file = new File("F:\\Program Files\\cmfzdemo\\src\\main\\webapp/image/",filename);
            name.transferTo(file);

            guruService.insert(guru);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 修改数据
     */
    @RequestMapping("/updateGuru")
    @ResponseBody
    public boolean updateGuru(MultipartFile name, Guru guru){
        try {
            //老名字
            String filename = name.getOriginalFilename();


            //2.通过时间戳 创建新名字
            filename=new Date().getTime()+"_"+filename;
            guru.setGuruImage(filename);

            //把文件写入服务器磁盘
            File file = new File("F:\\Program Files\\cmfzdemo\\src\\main\\webapp/image/",filename);
            name.transferTo(file);

            guruService.update(guru);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 通过主键删除数据
     */
    @RequestMapping("/deleteGuruById")
    @ResponseBody
    public boolean deleteGuruById(Integer guruId){
        try {
            guruService.deleteById(guruId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 通过主键批量删除数据
     */
    @RequestMapping("/deleteGuruByIds")
    @ResponseBody
    public boolean deleteGuruByIds(Integer[] guruIds){
        try {
            guruService.deleteByIds(guruIds);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 批量假删除
     */
    @RequestMapping("/multiDel")
    @ResponseBody
    public boolean multiDel(Integer[] ids){
        return guruService.multiDelete(ids);
    }

    /**
     * 获取所有的上师，以list集合的形式返回给页面
     */
    @RequestMapping("/all")
    @ResponseBody
    public List<Guru> getAllGuru(Integer[] ids){
        return (List<Guru>) guruService.getAllByLimit(1,99,new Guru()).get("rows");
    }

    /**
     * 数据库内容导出成excel
     */
    @RequestMapping("/output")
    @ResponseBody
    public boolean output(String fileName) {
        try {
            List<Guru> list = (List<Guru>) guruService.getAllByLimit(1, 99, new Guru()).get("rows");
           /* ExcelOutput.ChangeToExcelTest(list, fileName);*/

            //        1.定义导出的相关参数
            ExportParams exportParams = new ExportParams("上师信息","guru", ExcelType.HSSF);
//        2.创建WorkBook对象
            Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Guru.class, list);
//        3.保存到本地
            workbook.write(new FileOutputStream(new File("F://服务器/"+ fileName +".xls")));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * excel内容上传数据库
     */
    @RequestMapping("/input")
    @ResponseBody
    public boolean input(MultipartFile file) {
        System.out.println(file.getName());

        try {
            //        1.读取文件
            FileInputStream fileInputStream = new FileInputStream("F://服务器/" + file.getOriginalFilename());

//        2.处理流中的数据
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);

//        3.从工作簿对象中取出工作表对象
            HSSFSheet sheet = workbook.getSheet("guru");
//        sheet.getLastRowNum()获取最后一行的下标
            int lastRowNum = sheet.getLastRowNum();
//        不取标题栏 从1开始
            for (int i = 2; i <= lastRowNum; i++) {
//            4.从工作表对象中取出行对象
                HSSFRow row = sheet.getRow(i);
//           5.从行对象中取出单元格对象 取出单元格中的值
                //        6.封装为上师对象
                Guru cmfzGuru = new Guru();
                String stringCellValue = row.getCell(0).getStringCellValue();
                int id = Integer.parseInt(stringCellValue);
                cmfzGuru.setGuruId(id);
                cmfzGuru.setGuruName(row.getCell(1).getStringCellValue());
                cmfzGuru.setGuruNickname(row.getCell(3).getStringCellValue());
                cmfzGuru.setGuruImage(row.getCell(2).getStringCellValue());

                if ("正常".equals(row.getCell(4).getStringCellValue())) {
                    cmfzGuru.setGuruStatus(1);
                } else {
                    cmfzGuru.setGuruStatus(0);
                }

//        7.插入数据库
                if (guruService.getById(cmfzGuru.getGuruId()) == null) {
                    guruService.insert(cmfzGuru);
                } else {
                    guruService.update(cmfzGuru);
                }


                //        1.定义导入参数
//                ImportParams importParams = new ImportParams();
//                importParams.setTitleRows(1);
//                importParams.setHeadRows(1);
//        2.读取文件  使用工具类
//                List<CmfzGuru> objects = ExcelImportUtil.importExcel(new File("easypoitest.xls"), CmfzGuru.class, importParams);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}