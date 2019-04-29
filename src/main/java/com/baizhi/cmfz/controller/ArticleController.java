package com.baizhi.cmfz.controller;

import com.baizhi.cmfz.entity.Article;
import com.baizhi.cmfz.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * (Article)表控制层
 *
 * @author myself
 * @since 2019-01-05 22:39:25
 */
@Controller
@RequestMapping("/article")
public class ArticleController {
    /**
     * 服务对象
     */
    @Resource
    private ArticleService articleService;

    /**
     * 通过主键查询单条数据
     */
    @RequestMapping("/getOne")
    @ResponseBody
    public Article getOne(Integer id) {
        return this.articleService.getById(id);
    }

    /**
     * 通过实体作为筛选条件分页查询数据
     */
    @RequestMapping("/getAllArticleByLimit")
    @ResponseBody
    public Map getAllArticleByLimit(int page, int rows, Article article, String keyWord) {

        return this.articleService.getAllByLimit(page, rows, article, keyWord);
    }
    
    /**
     * 添加数据
     */
    @RequestMapping("/insertArticle")
    @ResponseBody
    public boolean insertArticle(Article article, MultipartFile name){
        try {
            //老名字
            String filename = name.getOriginalFilename();


            //2.通过时间戳 创建新名字
            filename=new Date().getTime()+"_"+filename;
            article.setArticleImage(filename);

            //把文件写入服务器磁盘
            File file = new File("F:\\Program Files\\cmfzdemo\\src\\main\\webapp/image/",filename);
            name.transferTo(file);

            articleService.insert(article);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 修改数据
     */
    @RequestMapping("/updateArticle")
    @ResponseBody
    public boolean updateArticle(Article article, MultipartFile name){
        try {
            //老名字
            String filename = name.getOriginalFilename();


            //2.通过时间戳 创建新名字
            filename=new Date().getTime()+"_"+filename;
            article.setArticleImage(filename);

            //把文件写入服务器磁盘
            File file = new File("F:\\Program Files\\cmfzdemo\\src\\main\\webapp/image/",filename);
            name.transferTo(file);

            articleService.update(article);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 通过主键删除数据
     */
    @RequestMapping("/deleteArticleById")
    @ResponseBody
    public boolean deleteArticleById(Integer articleId){
        try {
            articleService.deleteById(articleId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 通过主键批量删除数据
     */
    @RequestMapping("/deleteArticleByIds")
    @ResponseBody
    public boolean deleteArticleByIds(Integer[] articleIds){
        try {
            articleService.deleteByIds(articleIds);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}