package com.kalvin.kvf.modules.func.vo;

import com.kalvin.kvf.modules.func.entity.QuestionBank;
import com.kalvin.kvf.modules.func.entity.QuestionCollect;
import com.kalvin.kvf.modules.sys.entity.Dict;
import com.kalvin.kvf.modules.sys.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import com.diboot.core.binding.annotation.BindEntity;

@Getter
@Setter
@Accessors(chain = true)
public class QuestionBankVo extends QuestionBank {
    @BindEntity(entity = QuestionCollect.class, condition="this.id=qs_id")
    private QuestionCollect questionCollect;
}
