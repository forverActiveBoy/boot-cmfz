package com.baizhi.cmfz.service;

import com.baizhi.cmfz.entity.Album;
import java.util.List;
import java.util.Map;

/**
 * (Album)表服务接口
 *
 * @author myself
 * @since 2019-01-05 17:07:37
 */
public interface AlbumService {

    /**
     * 通过ID查询单条数据
     */
    Album getById(Integer albumId);

    /**
     * 通过实体作为筛选条件分页查询数据
     */
    Map getAllByLimit(int page, int rows, Album album);

    /**
     * 新增数据
     */
    void insert(Album album);

    /**
     * 修改数据
     */
    void update(Album album);

    /**
     * 通过主键删除数据
     */
    void deleteById(Integer albumId);
    
    /**
     * 通过主键批量删除数据
     */
    void deleteByIds(Integer[] albumIds);

    /**
     * 树形表格展示所有的专辑
     */
    List<Album> getAllAlbum();

}