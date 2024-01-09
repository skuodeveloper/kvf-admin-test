package com.kalvin.kvf.modules.zhouqi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.zhouqi.entity.Question;
import com.kalvin.kvf.modules.zhouqi.mapper.QuestionMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @since 2022-01-20 11:05:36
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Override
    public Page<Question> listQuestionPage(Question question) {
        Page<Question> page = new Page<>(question.getCurrent(), question.getSize());
        List<Question> questions = baseMapper.selectQuestionList(question, page);
        return page.setRecords(questions);
    }

}
