package com.yeh.pro.controller_1;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeh.pro.commonutils.R;
import com.yeh.pro.entity.UsersInfoEntity;
import com.yeh.pro.entity.vo.UsersInfoQuery;
import com.yeh.pro.service.UsersInfoService;
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
 * @since 2023-02-05
 */
@RestController
@RequestMapping("/myservice/users-info")
@CrossOrigin //解决跨域
public class UsersInfoController_1 {

    @Autowired
    private UsersInfoService usersInfoService;

    @GetMapping("findUsersInfo")
    public R findAddr(){
        List<UsersInfoEntity> list = usersInfoService.list(null);
        return R.ok().data("item",list);
    }


    //条件查询（分页）
    @PostMapping("pageUsersInfoCondition/{current}/{limit}")
    public R pageUsersCondition(
                               @PathVariable Long current,

                               @PathVariable Long limit,

                               @RequestBody UsersInfoQuery UsersInfoQuery){
        //创建page对象
        Page<UsersInfoEntity> pageUsersInfo = new Page<>(current,limit);
        //构建条件
        QueryWrapper<UsersInfoEntity> wrapper = new QueryWrapper<>();
        //多条件组合查询
        //判断条件是否为空，如果不为空拼接条件
        String name = UsersInfoQuery.getRealName();
        String phone = UsersInfoQuery.getUserPhone();
        Integer grade = UsersInfoQuery.getUserGrade();
        if (!StringUtils.isEmpty(name)){
            wrapper.like("real_name",name);
        }
        if (!StringUtils.isEmpty(name)){
            wrapper.eq("user_phone",phone);
        }
        if (!StringUtils.isEmpty(name)){
            wrapper.eq("user_grade",grade);
        }
        //排序
        wrapper.orderByAsc("user_grade");

        //调用方法
        usersInfoService.page(pageUsersInfo,wrapper);
        long total = pageUsersInfo.getTotal();
        List<UsersInfoEntity> records = pageUsersInfo.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    //添加用户信息接口的方法
    @PostMapping("addUsersInfo")
    public R addUsers(@RequestBody UsersInfoEntity usersInfo){
        boolean save = usersInfoService.save(usersInfo);
        if (save){
            return R.ok();
        }else return R.error();
    }

    //用户删除
    @DeleteMapping("{id}")
    public R removeById(
            @PathVariable String id){
        boolean flag = usersInfoService.removeById(id);
        if (flag){
            return R.ok();
        }else return R.error();
    }

    //用户修改
    @PostMapping("updateUsersInfo")
    public R updateUsers(@RequestBody UsersInfoEntity usersInfo){
        boolean flag = usersInfoService.updateById(usersInfo);
        if (flag){
            return R.ok();
        }else return R.error();
    }

    //根据用户id进行查询
    @GetMapping("getUsersInfo/{id}")
    public R getUsers(@PathVariable String id){
        UsersInfoEntity usersInfo= usersInfoService.getById(id);
        return R.ok().data("usersInfo",usersInfo);
    }
}

