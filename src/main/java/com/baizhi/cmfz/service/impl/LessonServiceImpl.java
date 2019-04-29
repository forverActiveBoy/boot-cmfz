package com.baizhi.cmfz.service.impl;

import com.baizhi.cmfz.dao.LessonDao;
import com.baizhi.cmfz.entity.Lesson;
import com.baizhi.cmfz.service.LessonService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {
    @Resource
    private LessonDao lessonDao;

    @Override
    public List<Lesson> getAllLesson(String lessonName, String counterName) {
        return lessonDao.getAllLesson(lessonName, counterName);
    }

    @Override
    public Lesson getOneLesson(Integer id) {
        return lessonDao.selectById(id);
    }

    @Override
    public boolean updateLesson(Lesson lesson) {
        return lessonDao.updateById(lesson) == 1 ? true : false;
    }

    @Override
    public boolean insertLesson(Lesson lesson) {
        return lessonDao.insert(lesson) == 1 ? true : false;
    }

    @Override
    public boolean deleteLesson(Integer id) {
        return lessonDao.deleteById(id) == 1 ? true : false;
    }
}
