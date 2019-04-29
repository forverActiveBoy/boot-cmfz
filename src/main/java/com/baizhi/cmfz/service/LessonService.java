package com.baizhi.cmfz.service;

import com.baizhi.cmfz.entity.Lesson;

import java.util.List;

public interface LessonService {
    List<Lesson> getAllLesson(String lessonName, String counterName);

    /**
     * 获取一个Lesson信息
     */
    Lesson getOneLesson(Integer id);

    boolean updateLesson(Lesson lesson);

    boolean insertLesson(Lesson lesson);

    boolean deleteLesson(Integer id);
}
