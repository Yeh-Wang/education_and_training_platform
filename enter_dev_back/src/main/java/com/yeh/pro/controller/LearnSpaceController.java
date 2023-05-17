package com.yeh.pro.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeh.pro.entity.ExamInfoEntity;
import com.yeh.pro.entity.LearnSpaceEntity;
import com.yeh.pro.mapper.LearnSpaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YehWang
 * @since 2023-01-30 22:32:15
 */
@RestController
@RequestMapping("/learn-space-entity")
public class LearnSpaceController {

    @Autowired
    private LearnSpaceMapper learnSpaceMapper;

    /**
     * add learn info
     */
    @ResponseBody
    @PostMapping("/addOneLearnInfo")
    Integer addOneLearnInfo(@RequestBody LearnSpaceEntity learnSpaceEntity){
        if(learnSpaceMapper.judgeUserLearnInfo(learnSpaceEntity.getUserId(),learnSpaceEntity.getVideoId())==0){
            Integer id = learnSpaceMapper.selectOne(new QueryWrapper<LearnSpaceEntity>().select("max(id) as id")).getId()+1;
            learnSpaceEntity.setId(id);
            return learnSpaceMapper.insert(learnSpaceEntity);
        }else{
            return learnSpaceMapper.updateById(learnSpaceEntity);
        }
    }

    /**
     * get learn info
     */
    @ResponseBody
    @GetMapping("/getAllLearnInfo")
    List<LearnSpaceEntity> getAllLearnInfo(){
        return learnSpaceMapper.selectList(null);
    }

    /**
     * getLearnByUserVideo
     */
    @ResponseBody
    @GetMapping("/getLearnByUserVideo/{user_id},{plan_id}")
    List<LearnSpaceEntity> getLearnByUserVideo(@PathVariable Integer user_id,@PathVariable Integer plan_id){
        return learnSpaceMapper.getLearnByUserVideo(user_id, plan_id);
    }
}
