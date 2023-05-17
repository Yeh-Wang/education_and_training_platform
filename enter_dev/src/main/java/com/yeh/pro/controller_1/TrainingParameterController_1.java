package com.yeh.pro.controller_1;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeh.pro.commonutils.R;
import com.yeh.pro.entity.TrainingParameterEntity;
import com.yeh.pro.entity.vo.TrainingParameterQuery;
import com.yeh.pro.service.TrainingParameterService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/myservice/training-parameter")
@CrossOrigin
public class TrainingParameterController_1 {

    @Autowired
    private TrainingParameterService trainingParameterService;

    @GetMapping("findTrainingParameter")
    public R findTrainingParameter(){
        List<TrainingParameterEntity> list = trainingParameterService.list(null);
        return R.ok().data("item",list);
    }


    @PostMapping("pageTrainingParameterCondition/{current}/{limit}")
    public R pageTrainingParameterCondition(
                                @PathVariable Long current,

                                @PathVariable Long limit,

                                @RequestBody TrainingParameterQuery trainingParameterQuery){
        //创建page对象
        Page<TrainingParameterEntity> pageTrainingParameter = new Page<>(current,limit);
        //构建条件
        QueryWrapper<TrainingParameterEntity> wrapper = new QueryWrapper<>();

        TrainingParameterEntity trainingParameter = new TrainingParameterEntity();
        //排序
        wrapper.orderByAsc("start_time");

        //调用方法
        trainingParameterService.page(pageTrainingParameter,wrapper);
        long total = pageTrainingParameter.getTotal();
        List<TrainingParameterEntity> records = pageTrainingParameter.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    //添加计划接口的方法
    @PostMapping("addTrainingParameter")
    public R addTrainingParameter(@RequestBody TrainingParameterEntity trainingParameter){
        boolean save = trainingParameterService.save(trainingParameter);
        if (save){
            return R.ok();
        }else return R.error();
    }

    //计划删除
    @DeleteMapping("{id}")
    public R removeById(
            @PathVariable String id){
        boolean flag = trainingParameterService.removeById(id);
        if (flag){
            return R.ok();
        }else return R.error();
    }

    //用户修改
    @PostMapping("updateTrainingParameter")
    public R updateTrainingParameter(@RequestBody TrainingParameterEntity trainingParameter){
        boolean flag = trainingParameterService.updateById(trainingParameter);
        if (flag){
            return R.ok();
        }else return R.error();
    }

    //根据用户id进行查询
    @GetMapping("getTrainingParameter/{id}")
    public R getTrainingParameter(@PathVariable String id){
        TrainingParameterEntity trainingParameter= trainingParameterService.getById(id);
        return R.ok().data("trainingParameter",trainingParameter);
    }
}

