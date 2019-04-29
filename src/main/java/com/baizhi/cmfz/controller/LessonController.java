package com.baizhi.cmfz.controller;

import com.baizhi.cmfz.entity.Lesson;
import com.baizhi.cmfz.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/lesson")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @RequestMapping("/show")
    @ResponseBody
    public List<Lesson> getAll(String lessonName, String counterName) {
       return lessonService.getAllLesson(lessonName, counterName);
    }

    /**
     * 获取一个lesson的详细信息
     */
    @RequestMapping("/getOne")
    @ResponseBody
    public Lesson getOne(Integer lessonId) {
        return lessonService.getOneLesson(lessonId);
    }

    @RequestMapping("/update")
    @ResponseBody
    public boolean update(Lesson lesson) {
        return lessonService.updateLesson(lesson);
    }

    @RequestMapping("/insert")
    @ResponseBody
    public boolean insertLesson(Lesson lesson) {
        lesson.setLessonId(0);
        return lessonService.insertLesson(lesson);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public boolean deleteLesson(Integer id) {
        return lessonService.deleteLesson(id);
    }
}
