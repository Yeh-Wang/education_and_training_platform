package com.yeh.pro.entity.vo;

import lombok.Data;

@Data
public class UsersInfoQuery {
    private static final long serialVersionUID = 1L;

    private Integer userGrade;

    private String realName;

    private String userPhone;
}
