package com.baizhi.cmfz.dao;

import com.baizhi.cmfz.entity.Album;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Album)表数据库访问层
 *
 * @author myself
 * @since 2019-01-05 17:07:37
 */
public interface AlbumDao extends BaseMapper<Album> {

    /**
     * 通过ID查询单条数据
     */
    Album getById(Integer albumId);

    /**
     * 通过实体作为筛选条件分页查询数据
     */
    List<Album> getAllByLimit(@Param("start") int start, @Param("rows") int rows, @Param("album") Album album);

    /**
     * 新增数据
     */
    void insertNew(Album album);

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
     * 查询数据条数
     */
    int getCount(Album album);
}