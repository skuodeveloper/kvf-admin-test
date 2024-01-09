package com.kalvin.kvf.modules.zhouqi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.zhouqi.entity.QuestionOptions;
import com.kalvin.kvf.modules.zhouqi.mapper.QuestionOptionsMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @since 2022-01-20 11:05:50
 */
@Service
public class QuestionOptionsServiceImpl extends ServiceImpl<QuestionOptionsMapper, QuestionOptions> implements QuestionOptionsService {

    @Override
    public Page<QuestionOptions> listQuestionOptionsPage(QuestionOptions questionOptions) {
        Page<QuestionOptions> page = new Page<>(questionOptions.getCurrent(), questionOptions.getSize());
        List<QuestionOptions> questionOptionss = baseMapper.selectQuestionOptionsList(questionOptions, page);
        return page.setRecords(questionOptionss);
    }

}
