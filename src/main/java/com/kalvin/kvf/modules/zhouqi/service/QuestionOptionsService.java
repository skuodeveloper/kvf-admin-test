package com.kalvin.kvf.modules.zhouqi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.zhouqi.entity.QuestionOptions;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2022-01-20 11:05:50
 */
public interface QuestionOptionsService extends IService<QuestionOptions> {

    /**
     * 获取列表。分页
     * @param questionOptions 查询参数
     * @return page
     */
    Page<QuestionOptions> listQuestionOptionsPage(QuestionOptions questionOptions);

}
