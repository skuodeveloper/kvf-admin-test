package com.kalvin.kvf.modules.func.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kalvin.kvf.modules.func.entity.TalkShow;

import java.util.List;

/**
 * <p>
 * 脱口秀 Mapper 接口
 * </p>
 * @since 2022-08-22 08:41:19
 */
public interface TalkShowMapper extends BaseMapper<TalkShow> {

    /**
     * 查询列表(分页)
     * @param talkShow 查询参数
     * @param page 分页参数
     * @return list
     */
    List<TalkShow> selectTalkShowList(TalkShow talkShow, IPage page);

}
