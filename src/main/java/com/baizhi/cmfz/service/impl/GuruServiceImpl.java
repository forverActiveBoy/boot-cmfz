package com.baizhi.cmfz.service.impl;

import com.baizhi.cmfz.entity.Guru;
import com.baizhi.cmfz.dao.GuruDao;
import com.baizhi.cmfz.service.GuruService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * (Guru)表服务实现类
 *
 * @author myself
 * @since 2019-01-05 19:52:27
 */
@Service("guruService")
public class GuruServiceImpl implements GuruService {
    @Resource
    private GuruDao guruDao;

    private final static Logger logger = LoggerFactory.getLogger(GuruServiceImpl.class);

    /**
     * 通过ID查询单条数据
     */
    @Override
    public Guru getById(Integer guruId) {
        return this.guruDao.getById(guruId);
    }

    /**
     * 通过实体作为筛选条件分页查询数据
     */
    @Override
    public Map getAllByLimit(int page, int rows, Guru guru) {
        int start = (page - 1) * rows;
        Map map = new HashMap();
        List<Guru> guruList = guruDao.getAllByLimit(start,rows,guru);
        int count = guruDao.getCount(guru);
        map.put("rows", guruList);
        map.put("total", count);

//        logger.info("guruServiceImpl中的map:" + map.toString());

        return map;
    }

    /**
     * 新增数据
     */
    @Override
    public void insert(Guru guru) {
        this.guruDao.insert(guru);
    }

    /**
     * 修改数据
     */
    @Override
    public void update(Guru guru) {
        this.guruDao.update(guru);
    }

    /**
     * 通过主键删除数据
     */
    @Override
    public void deleteById(Integer guruId) {
        this.guruDao.deleteById(guruId);
    }
    
    /**
     * 通过主键批量删除数据
     */
    @Override
    public void deleteByIds(Integer[] guruIds) {
        this.guruDao.deleteByIds(guruIds);
    }

    @Override
    public boolean multiDelete(Integer[] ids) {
        try {

            Integer[] ids1 = new Integer[ids.length];
            int i1 = 0;
            Integer[] ids2 = new Integer[ids.length];
            int i2 = 0;

            // 对里面的数据进行筛选
            for (Integer id : ids) {
                if (guruDao.getById(id).getGuruStatus() == 1) {
                    ids1[i1] = id;
                    i1++;
                } else {
                    ids2[i2] = id;
                    i2++;
                }
            }

            guruDao.multiDelete(ids1);
            guruDao.multiDelete2(ids2);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}