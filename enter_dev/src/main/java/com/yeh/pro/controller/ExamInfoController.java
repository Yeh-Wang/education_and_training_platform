package com.yeh.pro.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeh.pro.entity.ExamInfoEntity;
import com.yeh.pro.entity.UsersInfoEntity;
import com.yeh.pro.mapper.ExamInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YehWang
 * @since 2023-01-30 22:32:15
 */
@RestController
@RequestMapping("/exam-info-entity")
public class ExamInfoController {

    @Autowired
    private ExamInfoMapper examInfoMapper;

    /**
     * add a exam info
     */
    @ResponseBody
    @PostMapping("/addAInfo")
    Integer addAInfo(@RequestBody ExamInfoEntity examInfoEntity){
        if(examInfoMapper.judgeUserExamInfo(examInfoEntity.getUserId(),examInfoEntity.getExamPaperId())==0){
            Integer id = examInfoMapper.selectOne(new QueryWrapper<ExamInfoEntity>().select("max(id) as id")).getId()+1;
            examInfoEntity.setId(id);
            return examInfoMapper.insert(examInfoEntity);
        }else{
            return 0;
        }
    }

    /**
     * getExamInfoByUser
     */
    @ResponseBody
    @GetMapping("/getExamInfoByUser/{user_id}")
    ExamInfoEntity getExamInfoByUser(@PathVariable Integer user_id){
        return examInfoMapper.getExamInfoByUser(user_id);
    }
}
