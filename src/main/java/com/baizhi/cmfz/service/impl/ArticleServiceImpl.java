package com.baizhi.cmfz.service.impl;

import com.baizhi.cmfz.lucene.LunceneDao;
import com.baizhi.cmfz.entity.Article;
import com.baizhi.cmfz.dao.ArticleDao;
import com.baizhi.cmfz.service.ArticleService;
import com.baizhi.cmfz.util.LuceneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * (Article)表服务实现类
 *
 * @author myself
 * @since 2019-01-05 22:39:25
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleDao articleDao;
    @Autowired
    private LunceneDao lunceneDao;

    /**
     * 通过ID查询单条数据
     */
    @Override
    public Article getById(Integer articleId) {
        return this.articleDao.getById(articleId);
    }

    /**
     * 通过实体作为筛选条件分页查询数据
     */
    @Override
    public Map getAllByLimit(int page, int rows, Article article, String keyWord) {
        int start = (page - 1) * rows;
        Map map = new HashMap();
        List<Article> articles;

        // 如果包含查询关键字，就使用lucene查询
        if (keyWord != null && !"".equals(keyWord)) {
            // 判断索引库库存不存在
            if (LuceneUtil.judgeIndexDB()) {
                // 存在  直接查
                articles = lunceneDao.luceneSeleteByKeyword(keyWord);
            } else {
                // 不存在，先创建再查
                lunceneDao.createIndex(articleDao.getAllByLimit(0, Integer.MAX_VALUE, new Article()));

                articles = lunceneDao.luceneSeleteByKeyword(keyWord);
            }

            map.put("rows", articles);
            map.put("total", articles.size());
        } else {
            // 否则使用正常的查询
            List<Article> articleList = articleDao.getAllByLimit(start,rows,article);
            map.put("rows", articleList);
            int count = articleDao.getCount(article);
            map.put("total", count);
        }

        return map;
    }

    /**
     * 新增数据
     */
    @Override
    public void insert(Article article) {
        this.articleDao.insert(article);

        this.updateLucene();
    }

    /**
     * 修改数据
     */
    @Override
    public void update(Article article) {
        this.articleDao.update(article);

        this.updateLucene();
    }

    /**
     * 通过主键删除数据
     */
    @Override
    public void deleteById(Integer articleId) {
        this.articleDao.deleteById(articleId);

        this.updateLucene();
    }
    
    /**
     * 通过主键批量删除数据
     */
    @Override
    public void deleteByIds(Integer[] articleIds) {
        this.articleDao.deleteByIds(articleIds);

        this.updateLucene();
    }

    /**
     * 重置索引库
     */
    @Override
    public void updateLucene() {
        // 更新个索引库
        List<Article> list = this.articleDao.getAllByLimit(0, 9999, new Article());
        lunceneDao.resettingIndex(list);
    }
}