package com.baizhi.cmfz.lucene;

import com.baizhi.cmfz.dao.ArticleDao;
import com.baizhi.cmfz.lucene.LunceneDao;
import com.baizhi.cmfz.entity.Article;
import com.baizhi.cmfz.util.LuceneUtil;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LunceneDaoImpl implements LunceneDao {
    private static final Logger logger = LoggerFactory.getLogger(LunceneDaoImpl.class);
    @Autowired
    private ArticleDao articleDao;

    @Override
    public boolean createIndex(List<Article> articles) {
        logger.info("创建了新的索引库");

        try {
            // 建立索引库
            for (Article article : articles) {
                IndexWriter indexWriter = LuceneUtil.getIndexWriter();

                Document document = new Document();
                document.add(new StringField(("articleId"), article.getArticleId().toString(), Field.Store.YES));
                document.add(new TextField(("articleName"), article.getArticleName(), Field.Store.YES));
                document.add(new TextField(("articleContent"), article.getArticleContent(), Field.Store.YES));

                indexWriter.addDocument(document);

                LuceneUtil.indexCommit(indexWriter);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean resettingIndex(List<Article> articles) {
        logger.info("重置了索引库");

        try {
            // 先删除所有的索引
            IndexWriter indexWriter = LuceneUtil.getIndexWriter();
            indexWriter.deleteAll();
            LuceneUtil.indexCommit(indexWriter);

            // 建立索引库
            for (Article article : articles) {
                IndexWriter indexWriter1 = LuceneUtil.getIndexWriter();

                Document document = new Document();
                document.add(new StringField(("articleId"), article.getArticleId().toString(), Field.Store.YES));
                document.add(new TextField(("articleName"), article.getArticleName(), Field.Store.YES));
                document.add(new TextField(("articleContent"), article.getArticleContent(), Field.Store.YES));

                indexWriter1.addDocument(document);

                LuceneUtil.indexCommit(indexWriter1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteAllIndex() {
        logger.info("删除了所有的索引");

        try {
            // 删除所有的索引
            IndexWriter indexWriter = LuceneUtil.getIndexWriter();
            indexWriter.deleteAll();
            LuceneUtil.indexCommit(indexWriter);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteIndexById(int articleId) {
        logger.info("根据id删除了索引");

        try {
            // 获取索引写出对象
            IndexWriter indexWriter = LuceneUtil.getIndexWriter();

            indexWriter.deleteDocuments(new Term("articleId", "2"));

            LuceneUtil.indexCommit(indexWriter);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean updateIndexById(int articleId, Article article) {
        logger.info("根据id更新了索引");

        try {
            // 获取索引写出对象
            IndexWriter indexWriter = LuceneUtil.getIndexWriter();
            Document document = new Document();

            document.add(new StringField("articleId", article.getArticleId().toString(), Field.Store.YES));
            document.add(new TextField("articleName",article.getArticleName(), Field.Store.YES));
            document.add(new TextField("articleContent", article.getArticleContent(), Field.Store.YES));

            // 更新数据
            indexWriter.updateDocument(new Term("articleId", new Integer(articleId).toString()), document);

            LuceneUtil.indexCommit(indexWriter);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<Article> luceneSeleteByKeyword(String keyword) {
        logger.info("查询了索引");

        List<Article> list = new ArrayList<Article>();

        try {
            String[] files = {"articleId", "articleName", "articleContent"};

            MultiFieldQueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_44, files, LuceneUtil.analyzer);
            Query query = queryParser.parse(keyword);
            IndexSearcher indexSearcher = LuceneUtil.getIndexSearcher();
            TopDocs topDocs = indexSearcher.search(query, 100);

            ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            for (int i = 0; i < scoreDocs.length; i++) {
                ScoreDoc scoreDoc = scoreDocs[i];
                int doc = scoreDoc.doc;
                float score = scoreDoc.score;
                Document document = indexSearcher.doc(doc);
                if (document != null) {
                    String articleId = document.get("articleId");
                    String articleName = document.get("articleName");
                    String articleContent = document.get("articleContent");

                    Article article = new Article();
                    article.setArticleId(Integer.parseInt(articleId));
                    article.setArticleName(articleName);
                    article.setArticleContent(articleContent);

                    list.add(article);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            return list;

        }

    }
}
