package com.baizhi.cmfz.dao;

import com.baizhi.cmfz.entity.Lesson;
import com.baizhi.cmfz.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LessonDao extends BaseMapper<Lesson> {
    /**
     * 根据条件，多表查询课程
     */
    List<Lesson> getAllLesson(@Param("lessonName") String lessonName, @Param("counterName") String counterName);
}
