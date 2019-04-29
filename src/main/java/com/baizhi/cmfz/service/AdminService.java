package com.baizhi.cmfz.service;

import com.baizhi.cmfz.entity.Admin;
import java.util.List;
import java.util.Map;

/**
 * (Admin)表服务接口
 *
 * @author myself
 * @since 2019-01-06 14:19:14
 */
public interface AdminService {

    /**
     * 通过ID查询单条数据
     */
    Admin getById(Integer id);

    /**
     * 通过实体作为筛选条件分页查询数据
     */
    Map getAllByLimit(int page, int rows, Admin admin);

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

    boolean login(Admin admin);

    void logout();

    String loginByShiro(String username, String password);
}