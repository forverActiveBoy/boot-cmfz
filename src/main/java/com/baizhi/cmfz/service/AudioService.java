package com.baizhi.cmfz.service;

import com.baizhi.cmfz.entity.Audio;
import java.util.List;
import java.util.Map;

/**
 * (Audio)表服务接口
 *
 * @author myself
 * @since 2019-01-05 17:07:37
 */
public interface AudioService {

    /**
     * 通过ID查询单条数据
     */
    Audio getById(Integer audioId);

    /**
     * 通过实体作为筛选条件分页查询数据
     */
    Map getAllByLimit(int page, int rows, Audio audio);

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

}