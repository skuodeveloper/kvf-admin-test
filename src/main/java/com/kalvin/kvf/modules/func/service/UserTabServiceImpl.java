package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.UserTab;
import com.kalvin.kvf.modules.func.mapper.UserTabMapper;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 * @since 2021-03-30 14:33:54
 */
@Service
public class UserTabServiceImpl extends ServiceImpl<UserTabMapper, UserTab> implements UserTabService {

    @Override
    public Page<UserTab> listUserTabPage(UserTab userTab) {
        Page<UserTab> page = new Page<>(userTab.getCurrent(), userTab.getSize());
        List<UserTab> userTabs = baseMapper.selectUserTabList(userTab, page);
        return page.setRecords(userTabs);
    }

}
