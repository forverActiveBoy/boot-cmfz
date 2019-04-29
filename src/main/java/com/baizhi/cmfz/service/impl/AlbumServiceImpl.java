package com.baizhi.cmfz.service.impl;

import com.baizhi.cmfz.dao.AudioDao;
import com.baizhi.cmfz.entity.Album;
import com.baizhi.cmfz.dao.AlbumDao;
import com.baizhi.cmfz.entity.Audio;
import com.baizhi.cmfz.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Album)表服务实现类
 *
 * @author myself
 * @since 2019-01-05 17:07:37
 */
@Service("albumService")
public class AlbumServiceImpl implements AlbumService {
    @Resource
    private AlbumDao albumDao;
    @Autowired
    private AudioDao audioDao;

    /**
     * 通过ID查询单条数据
     */
    @Override
    public Album getById(Integer albumId) {
        return this.albumDao.getById(albumId);
    }

    /**
     * 通过实体作为筛选条件分页查询数据
     */
    @Override
    public Map getAllByLimit(int page, int rows, Album album) {
        int start = (page - 1) * rows;
        Map map = new HashMap();
        List<Album> albumList = albumDao.getAllByLimit(start,rows,album);
        int count = albumDao.getCount(album);
        map.put("rows", albumList);
        map.put("total", count);
        return map;
    }

    /**
     * 新增数据
     */
    @Override
    public void insert(Album album) {
        this.albumDao.insert(album);
    }

    /**
     * 修改数据
     */
    @Override
    public void update(Album album) {
        this.albumDao.update(album);
    }

    /**
     * 通过主键删除数据
     */
    @Override
    public void deleteById(Integer albumId) {
        this.albumDao.deleteById(albumId);
    }
    
    /**
     * 通过主键批量删除数据
     */
    @Override
    public void deleteByIds(Integer[] albumIds) {
        this.albumDao.deleteByIds(albumIds);
    }

    @Override
    public List<Album> getAllAlbum() {
        List<Album> albums = albumDao.getAllByLimit(0, 999, new Album());

        // 通过循环遍历，调用audio的查询方法查出相应专辑内的内容，赋值给对应专辑
        for (Album album : albums) {
            Audio audio = new Audio();

            audio.setAlbumId(album.getAlbumId());


            List<Audio> audios = audioDao.getAllByLimit(0, 999, audio);
            for (Audio audio1 : audios) {
                audio1.setAlbumName(audio1.getAudioName());
            }

            album.setChildren(audios);
        }

        return albums;
    }
}