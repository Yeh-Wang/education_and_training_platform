package com.yeh.pro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeh.pro.entity.StuInfoEntity;
import com.yeh.pro.entity.UsersInfoEntity;
import com.yeh.pro.mapper.StuInfoMapper;
import com.yeh.pro.mapper.UsersInfoMapper;
import com.yeh.pro.service.UsersInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YehWang
 * @since 2023-01-30 22:32:15
 */
@Service
public class UsersInfoServiceImpl extends ServiceImpl<UsersInfoMapper, UsersInfoEntity> implements UsersInfoService {

    //注入mapper
    private UsersInfoMapper usersInfoMapper;
    private StuInfoMapper stuInfoMapper;

    @Autowired
    public void setUsersInfoMapper(UsersInfoMapper usersInfoMapper){
        this.usersInfoMapper=usersInfoMapper;
    }

    @Override
    public String judgeUsername(String name) {
        if(Objects.equals(usersInfoMapper.judgeUsername(name), "1"))
            return "1";
        else
            return "用户名错误！";
    }

    @Override
    public String judgeUserPwd(String password) {
        if(Objects.equals(usersInfoMapper.judgeUserPwd(password), "1"))
            return "1";
        else
            return "密码错误！";
    }

    @Override
    public String judgeUserGrade(int grade,String username,String password) {
        if(usersInfoMapper.judgeUserGrade(grade,username,password)==1){
            return "1";
        }
        else
            return "用户类型错误！请重新选择用户类型";
    }

    @Override
    public UsersInfoEntity getUserInfoById(int id) {
        return usersInfoMapper.getUserInfoById(id);
    }

    @Override
    public int getId(int grade, String username, String password) {
        return usersInfoMapper.getId(grade,username,password);
    }

    @Override
    public List<UsersInfoEntity> getUsersInfoByGrade(int user_grade) {
        return usersInfoMapper.getUsersInfoByGrade(user_grade);
    }

    @Override
    public List<UsersInfoEntity> getUsersInfoByOrgId(int mechanism_id, int user_grade) {
        return usersInfoMapper.getUsersInfoByOrgId(mechanism_id,user_grade);
    }

    @Override
    public Integer addUserInfo(UsersInfoEntity usersInfoEntity) {
        Integer user_id = usersInfoMapper.selectOne(new QueryWrapper<UsersInfoEntity>().select("max(user_id) as userId")).getUserId()+1;
        usersInfoEntity.setUserId(user_id);
        usersInfoMapper.insert(usersInfoEntity);
        return user_id;
    }

    @Override
    public int updateUserInfo(UsersInfoEntity usersInfoEntity) {
        return usersInfoMapper.updateById(usersInfoEntity);
    }

    @Override
    public int deleteUserInfo(Integer id) {
        return usersInfoMapper.deleteById(id);
    }
}
