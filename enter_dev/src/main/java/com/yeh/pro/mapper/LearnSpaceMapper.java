package com.yeh.pro.mapper;

import com.yeh.pro.entity.LearnSpaceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YehWang
 * @since 2023-01-30 22:32:15
 */
@Mapper
public interface LearnSpaceMapper extends BaseMapper<LearnSpaceEntity> {

    @Select("select COUNT(*) from learn_space where user_id = #{user_id} and video_id = #{video_id}")
    int judgeUserLearnInfo(@Param("user_id") Integer user_id, @Param("video_id") Integer video_id);

    @Select("select * from learn_space where user_id = #{user_id} and train_id = #{plan_id}")
    List<LearnSpaceEntity> getLearnByUserVideo(@Param("user_id") Integer user_id, @Param("plan_id") Integer plan_id);
}
