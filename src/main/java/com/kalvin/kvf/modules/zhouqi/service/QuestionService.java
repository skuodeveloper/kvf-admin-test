package com.kalvin.kvf.modules.zhouqi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kalvin.kvf.modules.zhouqi.entity.Question;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2022-01-20 11:05:36
 */
public interface QuestionService extends IService<Question> {

    /**
     * 获取列表。分页
     * @param question 查询参数
     * @return page
     */
    Page<Question> listQuestionPage(Question question);

}
