package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.UserInfo;
import com.kalvin.kvf.modules.func.mapper.UserInfoMapper;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 * @since 2021-03-11 14:26:11
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public Page<UserInfo> listUserInfoPage(UserInfo userInfo) {
        Page<UserInfo> page = new Page<>(userInfo.getCurrent(), userInfo.getSize());
        List<UserInfo> userInfos = baseMapper.selectUserInfoList(userInfo, page);
        return page.setRecords(userInfos);
    }

    @Override
    public Page<UserInfo> getRank(UserInfo userInfo) {
        Page<UserInfo> page = new Page<>(userInfo.getCurrent(), userInfo.getSize());
        List<UserInfo> userInfos = baseMapper.getRank(userInfo, page);
        return page.setRecords(userInfos);
    }

    @Override
    public Page<UserInfo> getMonthRank(UserInfo userInfo) {
        Page<UserInfo> page = new Page<>(userInfo.getCurrent(), userInfo.getSize());
        List<UserInfo> userInfos = baseMapper.getMonthRank(userInfo, page);
        return page.setRecords(userInfos);
    }
}
