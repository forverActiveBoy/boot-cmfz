package com.baizhi.cmfz.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (AdminLog)实体类
 *
 * @author myself
 * @since 2019-01-11 16:30:46
 */
@Data @AllArgsConstructor @NoArgsConstructor
@TableName("cmfz_admin_log")
public class AdminLog implements Serializable {
    // private static final long serialVersionUID = -74951436597737422L;
    @TableId(value = "log_id",type = IdType.AUTO)
    private Integer logId;
    
    private String logAction;
    
    private String adminUsername;
    
    private Integer adminId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date logDate;
    
    private String logIp;
    
    private String logResult;



}