package com.baizhi.cmfz.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * (Banner)实体类
 *
 * @author makejava
 * @since 2019-01-03 20:34:18
 */
@Data @AllArgsConstructor @NoArgsConstructor
@TableName("cmfz_banner")
public class Banner implements Serializable {
    // private static final long serialVersionUID = 167985707528314980L;

    @TableId(value = "banner_id")
    private Integer bannerId;
    
    private String bannerImageUrl;
    //原有名称
    private String bannerOldName;
    
    private Integer bannerState = 1;
    //上传时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date bannerDate;
    
    private String bannerDescription;


}