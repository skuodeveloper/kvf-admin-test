package com.kalvin.kvf.modules.zhouqi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.zhouqi.entity.Question;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @since 2022-01-20 11:05:36
 */
public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 查询列表(分页)
     * @param question 查询参数
     * @param page 分页参数
     * @return list
     */
    List<Question> selectQuestionList(Question question, IPage page);

}
