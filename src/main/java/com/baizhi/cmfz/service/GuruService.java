package com.baizhi.cmfz.service;

import com.baizhi.cmfz.entity.Guru;
import java.util.List;
import java.util.Map;

/**
 * (Guru)表服务接口
 *
 * @author myself
 * @since 2019-01-05 19:52:27
 */
public interface GuruService {

    /**
     * 通过ID查询单条数据
     */
    Guru getById(Integer guruId);

    /**
     * 通过实体作为筛选条件分页查询数据
     */
    Map getAllByLimit(int page, int rows, Guru guru);

    /**
     * 新增数据
     */
    void insert(Guru guru);

    /**
     * 修改数据
     */
    void update(Guru guru);

    /**
     * 通过主键删除数据
     */
    void deleteById(Integer guruId);
    
    /**
     * 通过主键批量删除数据
     */
    void deleteByIds(Integer[] guruIds);

    /**
     * 批量假删除
     */
    boolean multiDelete(Integer[] ids);

}