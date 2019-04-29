package com.baizhi.cmfz.dao;

import com.baizhi.cmfz.entity.Admin;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Admin)表数据库访问层
 *
 * @author myself
 * @since 2019-01-06 14:19:14
 */
public interface AdminDao {

    /**
     * 通过ID查询单条数据
     */
    Admin getById(Integer id);

    /**
     * 通过实体作为筛选条件分页查询数据
     */
    List<Admin> getAllByLimit(@Param("start") int start, @Param("rows") int rows, @Param("admin") Admin admin);

    /**
     * 新增数据
     */
    void insert(Admin admin);

    /**
     * 修改数据
     */
    void update(Admin admin);

    /**
     * 通过主键删除数据
     */
    void deleteById(Integer id);
    
    /**
     * 通过主键批量删除数据
     */
     void deleteByIds(Integer[] ids);
     
    /**
     * 查询数据条数
     */
    int getCount(Admin admin);

    Admin getOne(Admin admin);
}