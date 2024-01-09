package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.func.entity.TalkShow;

/**
 * <p>
 * 脱口秀 服务类
 * </p>
 * @since 2022-08-22 08:41:19
 */
public interface TalkShowService extends IService<TalkShow> {

    /**
     * 获取列表。分页
     * @param talkShow 查询参数
     * @return page
     */
    Page<TalkShow> listTalkShowPage(TalkShow talkShow);

}
