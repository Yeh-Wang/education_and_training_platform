package com.yeh.pro.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeh.pro.entity.UsersInfoEntity;
import com.yeh.pro.entity.organizationInfo;
import com.yeh.pro.mapper.organizationInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Yaxin-Wang
 * @date : 2023/2/6
 */
@RestController
@RequestMapping("/organization-info-entity")
public class organizationInfoController {

    private organizationInfoMapper infoMapper;

    @Autowired
    public void setInfoMapper(organizationInfoMapper infoMapper) {
        this.infoMapper = infoMapper;
    }

    /**
     * get all information
     */
    @ResponseBody
    @GetMapping("/getAllOrgInfo")
    List<organizationInfo> getAllOrgInfo(){
        return infoMapper.selectList(null);
    }

    /**
     * add
     */
    @ResponseBody
    @PostMapping("/addOrgInfo")
    Integer addOrgInfo(@RequestBody organizationInfo info){
        Integer org_id = infoMapper.selectOne(new QueryWrapper<organizationInfo>().select("max(id) as id")).getId()+1;
        info.setId(org_id);
        infoMapper.insert(info);
        return org_id;
    }
    /**
     * delete
     */
    @ResponseBody
    @GetMapping("/deleteOrgInfo/{id}")
    Integer deleteOrgInfo(@PathVariable String id){
        return infoMapper.deleteById(id);
    }

    /**
     * get organization information
     */
    @ResponseBody
    @GetMapping("/getOrgInfoById/{id}")
    organizationInfo getOrgInfoById(@PathVariable Integer id){
        return infoMapper.selectById(id);
    }

    /**
     * update organization information
     */
    @ResponseBody
    @PostMapping("/updateOrgInfo")
    Integer updateOrgInfo(@RequestBody organizationInfo info){
        return infoMapper.updateById(info);
    }
}
