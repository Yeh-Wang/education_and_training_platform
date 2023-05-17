package com.yeh.pro.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yeh.pro.entity.organizationInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2023-02-06
 */

public interface OrganizationInfoService extends IService<organizationInfo> {

    /**
     * get all information
     */
    List<organizationInfo> getAllOrgInfo();
}
