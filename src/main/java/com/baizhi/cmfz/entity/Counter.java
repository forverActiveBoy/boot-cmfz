package com.baizhi.cmfz.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Counter)实体类
 *
 * @author myself
 * @since 2019-01-06 09:31:08
 */
@Data @AllArgsConstructor @NoArgsConstructor
@TableName("cmfz_counter")
public class Counter implements Serializable {

    @TableId("counter_id")
    private Integer counterId;

    private String counterName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date counterDate;
    
    private String lessonId;
    
    private User user;

    private Integer userId;
    
    private Integer counterCount;
    
    private Integer counterStatus = 1;

    private String lessonName;
}