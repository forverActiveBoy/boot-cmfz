package com.baizhi.cmfz.dao;

import com.baizhi.cmfz.entity.Article;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Article)表数据库访问层
 *
 * @author myself
 * @since 2019-01-05 22:39:25
 */
public interface ArticleDao {

    /**
     * 通过ID查询单条数据
     */
    Article getById(Integer articleId);

    /**
     * 通过实体作为筛选条件分页查询数据
     */
    List<Article> getAllByLimit(@Param("start") int start, @Param("rows") int rows, @Param("article") Article article);

    /**
     * 新增数据
     */
    void insert(Article article);

    /**
     * 修改数据
     */
    void update(Article article);

    /**
     * 通过主键删除数据
     */
    void deleteById(Integer articleId);
    
    /**
     * 通过主键批量删除数据
     */
     void deleteByIds(Integer[] articleIds);
     
    /**
     * 查询数据条数
     */
    int getCount(Article article);
}