package com.kalvin.kvf.modules.zhouqi.Vo;

import com.diboot.core.binding.annotation.BindEntity;
import com.diboot.core.binding.annotation.BindEntityList;
import com.kalvin.kvf.modules.zhouqi.entity.Question;
import com.kalvin.kvf.modules.zhouqi.entity.QuestionOptions;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class QuestionVo extends Question {
    @BindEntityList(entity = QuestionOptions.class, condition = "this.guid=parentid")
    private List<QuestionOptions> questionOptions;
}