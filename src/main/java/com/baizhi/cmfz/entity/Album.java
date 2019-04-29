package com.baizhi.cmfz.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * (Album)实体类
 *
 * @author myself
 * @since 2019-01-05 17:07:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cmfz_album")
public class Album implements Serializable {

    @TableId("album_id")
    private Integer albumId;
    
    private String albumName;
    
    private String albumAuthor;
    
    private String albumTeller;
    
    private Integer albumEpisodes;
    
    private Date albumDate;
    
    private String albumContent;
    
    private String albumImage;
    
    private Integer albumStar;

    private List<Audio> children;

}