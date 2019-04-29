package com.baizhi.cmfz.service;

import com.baizhi.cmfz.entity.Article;
import java.util.List;
import java.util.Map;

/**
 * (Article)表服务接口
 *
 * @author myself
 * @since 2019-01-05 22:39:25
 */
public interface ArticleService {

    /**
     * 通过ID查询单条数据
     */
    Article getById(Integer articleId);

    /**
     * 通过实体作为筛选条件分页查询数据
     */
    Map getAllByLimit(int page, int rows, Article article, String keyWord);

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
     * 重置索引库
     */
    void updateLucene();
}