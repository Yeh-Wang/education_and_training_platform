package com.yeh.pro.controller_1;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeh.pro.commonutils.R;
import com.yeh.pro.entity.JudgeQuestionBankEntity;
import com.yeh.pro.service.JudgeQuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-02-07
 */
@RestController
@RequestMapping("/myservice/judge-question-bank")
public class JudgeQuestionBankController_1 {

    @Autowired
    private JudgeQuestionBankService judgeQuestionBankService;

    @GetMapping("findJudgeQuestionBank")
    public R findJudgeQuestionBank(){
        List<JudgeQuestionBankEntity> list = judgeQuestionBankService.list(null);
        return R.ok().data("item",list);
    }


    //条件查询（分页）
    @PostMapping("pageJudgeQuestionBankCondition/{current}/{limit}")
    public R pageJudgeQuestionBankCondition(
                                             @PathVariable Long current,

                                             @PathVariable Long limit
                                             ){
        //创建page对象
        Page<JudgeQuestionBankEntity> pageJudgeQuestionBank = new Page<>(current,limit);
        //构建条件
        QueryWrapper<JudgeQuestionBankEntity> wrapper = new QueryWrapper<>();
        //多条件组合查询

        //排序
        wrapper.orderByAsc("founder");

        //调用方法
        judgeQuestionBankService.page(pageJudgeQuestionBank,wrapper);
        long total = pageJudgeQuestionBank.getTotal();
        List<JudgeQuestionBankEntity> records = pageJudgeQuestionBank.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    //添加选择题题库接口的方法
    @PostMapping("addJudgeQuestionBank")
    public R addJudgeQuestionBank(@RequestBody JudgeQuestionBankEntity judgeQuestionBank){
        boolean save = judgeQuestionBankService.save(judgeQuestionBank);
        if (save){
            return R.ok();
        }else return R.error();
    }

    //选择题题库删除
    @DeleteMapping("{id}")
    public R removeById(
            @PathVariable String id){
        boolean flag = judgeQuestionBankService.removeById(id);
        if (flag){
            return R.ok();
        }else return R.error();
    }

    //选择题修改
    @PostMapping("updateJudgeQuestionBank")
    public R updateJudgeQuestionBank(@RequestBody JudgeQuestionBankEntity judgeQuestionBank){
        boolean flag = judgeQuestionBankService.updateById(judgeQuestionBank);
        if (flag){
            return R.ok();
        }else return R.error();
    }

    //根据用户id进行查询
    @GetMapping("getJudgeQuestionBank/{id}")
    public R getJudgeQuestionBank(@PathVariable String id){
        JudgeQuestionBankEntity judgeQuestionBank= judgeQuestionBankService.getById(id);
        return R.ok().data("judgeQuestionBank",judgeQuestionBank);
    }
}

