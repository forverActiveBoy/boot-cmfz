package com.baizhi.cmfz.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Admin)实体类
 *
 * @author myself
 * @since 2019-01-06 14:19:14
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class Admin implements Serializable {

    private Integer id;
    
    private String username;
    
    private String password;

}