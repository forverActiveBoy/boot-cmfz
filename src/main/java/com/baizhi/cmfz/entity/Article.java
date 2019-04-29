package com.baizhi.cmfz.entity;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Article)实体类
 *
 * @author myself
 * @since 2019-01-05 22:39:25
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class Article implements Serializable {

    private Integer articleId;
    
    private String articleName;
    
    private String articleImage;
    
    private String articleContent;
    
    private Integer guruId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date articleDate;

}