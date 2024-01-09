package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.QuestionCollect;
import com.kalvin.kvf.modules.func.mapper.QuestionCollectMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @since 2022-01-17 08:50:34
 */
@Service
public class QuestionCollectServiceImpl extends ServiceImpl<QuestionCollectMapper, QuestionCollect> implements QuestionCollectService {

    @Override
    public Page<QuestionCollect> listQuestionCollectPage(QuestionCollect questionCollect) {
        Page<QuestionCollect> page = new Page<>(questionCollect.getCurrent(), questionCollect.getSize());
        List<QuestionCollect> questionCollects = baseMapper.selectQuestionCollectList(questionCollect, page);
        return page.setRecords(questionCollects);
    }

}
