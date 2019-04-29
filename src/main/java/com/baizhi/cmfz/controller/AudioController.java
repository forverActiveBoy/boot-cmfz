package com.baizhi.cmfz.controller;

import com.baizhi.cmfz.entity.Audio;
import com.baizhi.cmfz.service.AudioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * (Audio)表控制层
 *
 * @author myself
 * @since 2019-01-05 17:07:37
 */
@Controller
@RequestMapping("/audio")
public class AudioController {
    /**
     * 服务对象
     */
    @Resource
    private AudioService audioService;

    /**
     * 通过主键查询单条数据
     */
    @RequestMapping("/getOne")
    @ResponseBody
    public Audio getOne(Integer id) {
        return this.audioService.getById(id);
    }
    
    /**
     * 通过实体作为筛选条件分页查询数据
     */
    @RequestMapping("/getAllAudioByLimit")
    @ResponseBody
    public Map getAllAudioByLimit(int page, int rows, Audio audio) {
        return this.audioService.getAllByLimit(page,rows,audio);
    }
    
    /**
     * 添加数据
     */
    @RequestMapping("/insertAudio")
    @ResponseBody
    public boolean insertAudio(Audio audio){
        try {
            audioService.insert(audio);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 修改数据
     */
    @RequestMapping("/updateAudio")
    @ResponseBody
    public boolean updateAudio(Audio audio){
        try {
            audioService.update(audio);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 通过主键删除数据
     */
    @RequestMapping("/deleteAudioById")
    @ResponseBody
    public boolean deleteAudioById(Integer audioId){
        try {
            audioService.deleteById(audioId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 通过主键批量删除数据
     */
    @RequestMapping("/deleteAudioByIds")
    @ResponseBody
    public boolean deleteAudioByIds(Integer[] audioIds){
        try {
            audioService.deleteByIds(audioIds);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}