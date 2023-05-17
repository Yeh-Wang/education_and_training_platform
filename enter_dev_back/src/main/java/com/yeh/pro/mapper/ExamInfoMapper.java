package com.yeh.pro.mapper;

import com.yeh.pro.entity.ExamInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YehWang
 * @since 2023-01-30 22:32:15
 */
@Mapper
public interface ExamInfoMapper extends BaseMapper<ExamInfoEntity> {

    @Select("select COUNT(*) from exam_info where user_id = #{user_id} and exam_paper_id = #{paper_id}")
    int judgeUserExamInfo(@Param("user_id") Integer user_id,@Param("paper_id") Integer paper_id);

    @Select("select * from exam_info where user_id = #{user_id}")
    ExamInfoEntity getExamInfoByUser(@Param("user_id") Integer user_id);
}
