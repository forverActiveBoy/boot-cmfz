package com.baizhi.cmfz.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Lesson)实体类
 *
 * @author myself
 * @since 2019-01-06 09:29:13
 */
@Data @AllArgsConstructor @NoArgsConstructor
@TableName("cmfz_lesson")
public class Lesson implements Serializable {
    @TableId("lesson_id")
    private Integer lessonId;
    
    private String lessonName;

    private Integer userId;

    private Integer lessonStatus = 1;

    @TableField(value = "user_id")
    private User user;
    @TableField(exist = false)
    private List<Counter> children;
}