package com.baizhi.cmfz.util;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class LuceneUtil {
    private static final Logger logger = LoggerFactory.getLogger(LuceneUtil.class);
    private static FSDirectory directory;
    private static IndexWriterConfig indexWriterConfig;
    public static SmartChineseAnalyzer analyzer;
    private static DirectoryReader reader;

    static {
        try {
            analyzer = new SmartChineseAnalyzer(Version.LUCENE_44);
            directory = FSDirectory.open(new File("E://lucentest"));
            indexWriterConfig = new IndexWriterConfig(Version.LUCENE_44,analyzer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static IndexWriter getIndexWriter(){
        IndexWriter indexWriter = null;
        try {
            indexWriter = new IndexWriter(directory,indexWriterConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexWriter;
    }

    public static IndexSearcher getIndexSearcher() throws IOException {
        reader = DirectoryReader.open(directory);
        return new IndexSearcher(reader);
    }

    public static void indexCommit(IndexWriter indexWriter){
        try {
            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void indexRollback(IndexWriter indexWriter){
        try {
            indexWriter.rollback();
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断索引库中有没有索引文件 如果没有 返回false  如果有返回true
     * @return
     */
    public static boolean judgeIndexDB(){
        IndexSearcher indexSearcher = null;
        /**
         * MatchAllDocsQuery  查询全部document信息
         */
        Query query = new MatchAllDocsQuery();
        TopDocs topDocs = null;
        try {
            indexSearcher = getIndexSearcher();
            topDocs = indexSearcher.search(query, 10000);
        } catch (Exception e) {
            return false;
        }
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        logger.info("目前索引库中有："+scoreDocs.length+"条数据");
        if (scoreDocs.length==0){
            return false;
        }
        return true;
    }
}
