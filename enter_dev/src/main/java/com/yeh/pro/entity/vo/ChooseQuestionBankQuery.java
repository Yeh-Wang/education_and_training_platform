package com.yeh.pro.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class ChooseQuestionBankQuery {
    @TableField("founder")
    private Integer founder;
}
