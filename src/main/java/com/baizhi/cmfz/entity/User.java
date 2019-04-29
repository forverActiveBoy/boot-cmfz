package com.baizhi.cmfz.entity;

import com.baizhi.cmfz.util.ExcelName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2019-01-03 21:31:56
 */
@Data @AllArgsConstructor @NoArgsConstructor
@TableName("cmfz_user")
public class User implements Serializable {
    // private static final long serialVersionUID = 692662301215719706L;
    @TableId(value = "user_id")
    @ExcelName(name = "用户的编号")
    private Integer userId;
    @ExcelName(name = "用户的tel")
    private String telphone;
    @ExcelName(name = "用户的密码")
    private String password;
    @ExcelName(name = "用户的图片路径")
    private String userImage;
    @ExcelName(name = "用户的昵称")
    private String nickname;
    @ExcelName(name = "用户的真名")
    private String name;
    @ExcelName(name = "用户的性别")
    private String sex;
    @ExcelName(name = "用户的签名")
    private String autograph;
    @ExcelName(name = "用户的所在省份")
    private String userProvince;
    @ExcelName(name = "用户的所在城市")
    private String userCity;
    @ExcelName(name = "用户的关注上师id")
    private Integer guruId;
    @ExcelName(name = "用户的状态")
    private Integer userStatus = 1;
    @ExcelName(name = "用户的状态")
    private Date createDate;

}