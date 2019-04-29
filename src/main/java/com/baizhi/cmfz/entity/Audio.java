package com.baizhi.cmfz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Audio)实体类
 *
 * @author myself
 * @since 2019-01-05 17:07:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Audio implements Serializable {

    private Integer audioId;
    
    private String audioName;
    
    private Integer albumId;
    
    private String audioUrl;
    
    private String audioSize;
    
    private Integer audioOrder;

    private String albumName;

}