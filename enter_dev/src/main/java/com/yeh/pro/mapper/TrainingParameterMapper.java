package com.yeh.pro.mapper;

import com.yeh.pro.entity.TrainingParameterEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
public interface TrainingParameterMapper extends BaseMapper<TrainingParameterEntity> {

    /**
     * get all training plans information
     */
    @Select("select * from training_parameter where mechanism_id = #{id}")
    List<TrainingParameterEntity> getAllByMechanismId(@Param("id") Integer id);

    /**
     * 保证一个机构只有一个计划是进行中的
     */
    @Update("update training_parameter set train_status = '已结束' where mechanism_id = #{org_id}")
    Integer updateStatusOnlyOne(@Param("org_id") Integer org_id);

    /**
     * 获得指定机构中正在进行中的计划编号
     */
    @Select("select id from training_parameter where train_status = '进行中' and mechanism_id = #{org_id}")
    Integer getIdOn(@Param("org_id") Integer org_id);
}
