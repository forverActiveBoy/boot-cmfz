package com.baizhi.cmfz.controller;

import com.baizhi.cmfz.entity.Album;
import com.baizhi.cmfz.service.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (Album)表控制层
 *
 * @author myself
 * @since 2019-01-05 17:07:37
 */
@Controller
@RequestMapping("/album")
public class AlbumController {
    /**
     * 服务对象
     */
    @Resource
    private AlbumService albumService;

    /**
     * 通过主键查询单条数据
     */
    @RequestMapping("/getOne")
    @ResponseBody
    public Album getOne(Integer id) {
        return this.albumService.getById(id);
    }
    
    /**
     * 通过实体作为筛选条件分页查询数据
     */
    @RequestMapping("/getAllAlbumByLimit")
    @ResponseBody
    public Map getAllAlbumByLimit(int page, int rows, Album album) {
        return this.albumService.getAllByLimit(page,rows,album);
    }
    
    /**
     * 添加数据
     */
    @RequestMapping("/insertAlbum")
    @ResponseBody
    public boolean insertAlbum(Album album){
        System.out.println("album = " + album);
        album.setAlbumId(0);

        try {
            albumService.insert(album);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 修改数据
     */
    @RequestMapping("/updateAlbum")
    @ResponseBody
    public boolean updateAlbum(Album album){
        try {
            albumService.update(album);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 通过主键删除数据
     */
    @RequestMapping("/deleteAlbumById")
    @ResponseBody
    public boolean deleteAlbumById(Integer albumId){
        try {
            albumService.deleteById(albumId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 通过主键批量删除数据
     */
    @RequestMapping("/deleteAlbumByIds")
    @ResponseBody
    public boolean deleteAlbumByIds(Integer[] albumIds){
        try {
            albumService.deleteByIds(albumIds);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * treeGrid展示
     */
    @RequestMapping("/show")
    @ResponseBody
    public List<Album> showAllAlbum(){
        return albumService.getAllAlbum();
    }
}