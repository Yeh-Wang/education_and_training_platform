package com.yeh.pro.controller_1;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeh.pro.commonutils.R;
import com.yeh.pro.entity.organizationInfo;
import com.yeh.pro.entity.vo.OrganizationInfoQuery;
import com.yeh.pro.service.OrganizationInfoService;
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
@RequestMapping("/myservice/organization-info")
@CrossOrigin
public class OrganizationInfoController_1 {

    @Autowired
    private OrganizationInfoService organizationInfoService;

    @GetMapping("findOrganizationInfo")
    public R findOrganizationInfo(){
        List<organizationInfo> list = organizationInfoService.list(null);
        return R.ok().data("item",list);
    }


    @PostMapping("pageOrganizationInfoCondition/{current}/{limit}")
    public R pageOrganizationInfoCondition(
                                            @PathVariable Long current,

                                            @PathVariable Long limit,
                                            @RequestBody OrganizationInfoQuery organizationInfoQuery){
        //创建page对象
        Page<organizationInfo> pageOrganizationInfo = new Page<>(current,limit);
        //构建条件
        QueryWrapper<organizationInfo> wrapper = new QueryWrapper<>();

        String name = organizationInfoQuery.getOrgName();
        if (!StringUtils.isEmpty(name)){
            wrapper.like("org_name",name);
        }

        organizationInfo organizationInfo = new organizationInfo();
        //排序
        wrapper.orderByAsc("org_name");

        //调用方法
        organizationInfoService.page(pageOrganizationInfo,wrapper);
        long total = pageOrganizationInfo.getTotal();
        List<organizationInfo> records = pageOrganizationInfo.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    //添加机构接口的方法
    @PostMapping("addOrganizationInfo")
    public R addOrganizationInfo(@RequestBody organizationInfo organizationInfo){
        boolean save = organizationInfoService.save(organizationInfo);
        if (save){
            return R.ok();
        }else return R.error();
    }

    //机构删除
    @DeleteMapping("{id}")
    public R removeById(
            @PathVariable String id){
        boolean flag = organizationInfoService.removeById(id);
        if (flag){
            return R.ok();
        }else return R.error();
    }

    //机构修改
    @PostMapping("updateOrganizationInfo")
    public R updateOrganizationInfo(@RequestBody organizationInfo organizationInfo){
        boolean flag = organizationInfoService.updateById(organizationInfo);
        if (flag){
            return R.ok();
        }else return R.error();
    }

    //根据机构id进行查询
    @GetMapping("getOrganizationInfo/{id}")
    public R getOrganizationInfo(@PathVariable String id){
        organizationInfo organizationInfo= organizationInfoService.getById(id);
        return R.ok().data("organizationInfo",organizationInfo);
    }

}

