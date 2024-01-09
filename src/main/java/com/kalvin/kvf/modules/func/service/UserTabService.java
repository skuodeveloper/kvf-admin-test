package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.UserTab;

/**
 * <p>
 * 用户表 服务类
 * </p>
 * @since 2021-03-30 14:33:54
 */
public interface UserTabService extends IService<UserTab> {

    /**
     * 获取列表。分页
     * @param userTab 查询参数
     * @return page
     */
    Page<UserTab> listUserTabPage(UserTab userTab);

}
