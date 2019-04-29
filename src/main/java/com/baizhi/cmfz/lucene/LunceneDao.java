package com.baizhi.cmfz.lucene;

import com.baizhi.cmfz.entity.Article;

import java.util.List;

public interface LunceneDao {
    /**
     * 创建索引库
     * 参数为要创建索引的数据
     * @return 成功返回true  失败返回false
     */
    boolean createIndex(List<Article> articles);

    /**
     * 重置索引库  先删掉全部索引文件  再使用新的数据创建
     * 成功返回true  失败返回false
     * @return
     */
    boolean resettingIndex(List<Article> articles);

    /**
     * 删除索引库中全部文件
     *
     * @return 成功返回true  失败返回false
     */
    boolean deleteAllIndex();
    /**
     * 根据id删除索引库中对应的数据
     *
     * @return 成功返回true  失败返回false
     */
    boolean deleteIndexById(int articleId);

    /**
     * 修改 根据id
     * @param articleId
     * @param article
     * @return 成功返回true  失败返回false
     */
    boolean updateIndexById(int articleId,Article article);
    /**
     * 根据关键词检索  关键词可以是一个字儿  也可以是一句话
     * @param keyword
     * @return
     */
    List<Article> luceneSeleteByKeyword(String keyword);
}
