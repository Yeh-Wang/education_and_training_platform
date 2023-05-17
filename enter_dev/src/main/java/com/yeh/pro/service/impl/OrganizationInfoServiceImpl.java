package com.yeh.pro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeh.pro.entity.organizationInfo;
import com.yeh.pro.mapper.organizationInfoMapper;
import com.yeh.pro.service.OrganizationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-02-06
 */
@Service
public class OrganizationInfoServiceImpl extends ServiceImpl<organizationInfoMapper, organizationInfo> implements OrganizationInfoService {

    @Autowired
    private organizationInfoMapper infoMapper;

    @Override
    public List<organizationInfo> getAllOrgInfo() {
        return infoMapper.selectList(null);
    }
}
