package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.QuestionError;
import com.kalvin.kvf.modules.func.mapper.QuestionErrorMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @since 2022-01-14 09:46:07
 */
@Service
public class QuestionErrorServiceImpl extends ServiceImpl<QuestionErrorMapper, QuestionError> implements QuestionErrorService {

    @Override
    public Page<QuestionError> listQuestionErrorPage(QuestionError questionError) {
        Page<QuestionError> page = new Page<>(questionError.getCurrent(), questionError.getSize());
        List<QuestionError> questionErrors = baseMapper.selectQuestionErrorList(questionError, page);
        return page.setRecords(questionErrors);
    }

}
