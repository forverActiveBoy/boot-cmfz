package com.baizhi.cmfz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

/**
 * (Menu)实体类
 *
 * @author makejava
 * @since 2019-01-03 17:39:17
 */
@Data @AllArgsConstructor @NoArgsConstructor
@TableName("cmfz_menu")
public class Menu implements Serializable {
    // private static final long serialVersionUID = 708596802227463024L;

    @TableId("menu_id")
    private Integer menuId;
    
    private String menuName;
    
    private String menuUrl;
    
    private Integer menuParentId;

    private List<Menu> menus;

}