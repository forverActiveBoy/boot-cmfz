package com.baizhi.cmfz.entity;

import java.io.Serializable;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Guru)实体类
 *
 * @author myself
 * @since 2019-01-05 19:52:27
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class Guru implements Serializable {
    @Excel(name = "上师的编号")
    private Integer guruId;
    @Excel(name = "上师的名字")
    private String guruName;
    @Excel(name = "上师的图片")
    private String guruImage;
    @Excel(name = "上师的法号")
    private String guruNickname;
    @Excel(name = "上师的状态", replace = {"正常_1","冻结_0"})
    //1冻结 冻结的同时需要下架相关的专辑和文章
    private Integer guruStatus = 1;

}