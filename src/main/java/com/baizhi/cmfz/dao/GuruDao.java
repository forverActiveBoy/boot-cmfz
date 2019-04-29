package com.baizhi.cmfz.dao;

import com.baizhi.cmfz.entity.Guru;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Guru)表数据库访问层
 *
 * @author myself
 * @since 2019-01-05 19:52:27
 */
public interface GuruDao {

    /**
     * 通过ID查询单条数据
     */
    Guru getById(Integer guruId);

    /**
     * 通过实体作为筛选条件分页查询数据
     */
    List<Guru> getAllByLimit(@Param("start") int start, @Param("rows") int rows, @Param("guru") Guru guru);

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
     * 查询数据条数
     */
    int getCount(Guru guru);

    /**
     * 批量假删除
     * @param ids
     */
    void multiDelete(Integer[] ids);
    void multiDelete2(Integer[] ids);
}