package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.UserInfo;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 * @since 2021-03-11 14:26:11
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 获取列表。分页
     * @param userInfo 查询参数
     * @return page
     */
    Page<UserInfo> listUserInfoPage(UserInfo userInfo);

    /**
     * 获取列表。分页 获取排行榜
     * @return page
     */
    Page<UserInfo> getRank(UserInfo userInfo);

    /**
     * 获取列表。分页 获取本月排行榜
     * @return page
     */
    Page<UserInfo> getMonthRank(UserInfo userInfo);
}
