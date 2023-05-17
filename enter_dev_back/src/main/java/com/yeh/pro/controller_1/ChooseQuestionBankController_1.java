package com.yeh.pro.controller_1;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeh.pro.commonutils.R;
import com.yeh.pro.entity.ChooseQuestionBankEntity;
import com.yeh.pro.entity.vo.ChooseQuestionBankQuery;
import com.yeh.pro.service.ChooseQuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-02-06
 */
@RestController
@RequestMapping("/myservice/choose-question-bank")
@CrossOrigin
public class ChooseQuestionBankController_1 {

    @Autowired
    private ChooseQuestionBankService ChooseQuestionBankService;

    @GetMapping("findChooseQuestionBankEntity")
    public R findChooseQuestionBankEntity(){
        List<ChooseQuestionBankEntity> list = ChooseQuestionBankService.list(null);
        return R.ok().data("item",list);
    }


    //条件查询（分页）
    @PostMapping("pageChooseQuestionBankEntityCondition/{current}/{limit}")
    public R pageChooseQuestionBankEntityCondition(
                                @PathVariable Long current,

                                @PathVariable Long limit,
                                
                                @RequestBody ChooseQuestionBankQuery ChooseQuestionBankQuery){
        //创建page对象
        Page<ChooseQuestionBankEntity> pageChooseQuestionBankEntity = new Page<>(current,limit);
        //构建条件
        QueryWrapper<ChooseQuestionBankEntity> wrapper = new QueryWrapper<>();
        //多条件组合查询
        //判断条件是否为空，如果不为空拼接条件
        Integer founder = ChooseQuestionBankQuery.getFounder();
        if (!StringUtils.isEmpty(founder)) {
            wrapper.like("founder", founder);
        }
        //排序
        wrapper.orderByAsc("founder");

        //调用方法
        ChooseQuestionBankService.page(pageChooseQuestionBankEntity,wrapper);
        long total = pageChooseQuestionBankEntity.getTotal();
        List<ChooseQuestionBankEntity> records = pageChooseQuestionBankEntity.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    //添加选择题题库接口的方法
    @PostMapping("addChooseQuestionBankEntity")
    public R addChooseQuestionBankEntity(@RequestBody ChooseQuestionBankEntity ChooseQuestionBankEntity){
        boolean save = ChooseQuestionBankService.save(ChooseQuestionBankEntity);
        if (save){
            return R.ok();
        }else return R.error();
    }

    //选择题题库删除
    @DeleteMapping("{id}")
    public R removeById(
            @PathVariable String id){
        boolean flag = ChooseQuestionBankService.removeById(id);
        if (flag){
            return R.ok();
        }else return R.error();
    }

    //选择题修改
    @PostMapping("updateChooseQuestionBankEntity")
    public R updateChooseQuestionBankEntity(@RequestBody ChooseQuestionBankEntity ChooseQuestionBankEntity){
        boolean flag = ChooseQuestionBankService.updateById(ChooseQuestionBankEntity);
        if (flag){
            return R.ok();
        }else return R.error();
    }

    //根据用户id进行查询
    @GetMapping("getChooseQuestionBankEntity/{id}")
    public R getChooseQuestionBankEntity(@PathVariable String id){
        ChooseQuestionBankEntity ChooseQuestionBankEntity= ChooseQuestionBankService.getById(id);
        return R.ok().data("ChooseQuestionBankEntity",ChooseQuestionBankEntity);
    }
}

