package com.baizhi.cmfz.dao;

import com.baizhi.cmfz.entity.Audio;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Audio)表数据库访问层
 *
 * @author myself
 * @since 2019-01-05 17:07:37
 */
public interface AudioDao {

    /**
     * 通过ID查询单条数据
     */
    Audio getById(Integer audioId);

    /**
     * 通过实体作为筛选条件分页查询数据
     */
    List<Audio> getAllByLimit(@Param("start") int start, @Param("rows") int rows, @Param("audio") Audio audio);

    /**
     * 新增数据
     */
    void insert(Audio audio);

    /**
     * 修改数据
     */
    void update(Audio audio);

    /**
     * 通过主键删除数据
     */
    void deleteById(Integer audioId);
    
    /**
     * 通过主键批量删除数据
     */
     void deleteByIds(Integer[] audioIds);
     
    /**
     * 查询数据条数
     */
    int getCount(Audio audio);
}