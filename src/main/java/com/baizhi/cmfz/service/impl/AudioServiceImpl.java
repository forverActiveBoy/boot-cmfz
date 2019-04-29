package com.baizhi.cmfz.service.impl;

import com.baizhi.cmfz.entity.Audio;
import com.baizhi.cmfz.dao.AudioDao;
import com.baizhi.cmfz.service.AudioService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * (Audio)表服务实现类
 *
 * @author myself
 * @since 2019-01-05 17:07:37
 */
@Service("audioService")
public class AudioServiceImpl implements AudioService {
    @Resource
    private AudioDao audioDao;

    /**
     * 通过ID查询单条数据
     */
    @Override
    public Audio getById(Integer audioId) {
        return this.audioDao.getById(audioId);
    }

    /**
     * 通过实体作为筛选条件分页查询数据
     */
    @Override
    public Map getAllByLimit(int page, int rows, Audio audio) {
        int start = (page - 1) * rows;
        Map map = new HashMap();
        List<Audio> audioList = audioDao.getAllByLimit(start,rows,audio);
        int count = audioDao.getCount(audio);
        map.put("rows", audioList);
        map.put("total", count);
        return map;
    }

    /**
     * 新增数据
     */
    @Override
    public void insert(Audio audio) {
        this.audioDao.insert(audio);
    }

    /**
     * 修改数据
     */
    @Override
    public void update(Audio audio) {
        this.audioDao.update(audio);
    }

    /**
     * 通过主键删除数据
     */
    @Override
    public void deleteById(Integer audioId) {
        this.audioDao.deleteById(audioId);
    }
    
    /**
     * 通过主键批量删除数据
     */
    @Override
    public void deleteByIds(Integer[] audioIds) {
        this.audioDao.deleteByIds(audioIds);
    }
}