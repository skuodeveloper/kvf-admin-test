package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.TalkShow;
import com.kalvin.kvf.modules.func.mapper.TalkShowMapper;

import java.util.List;

/**
 * <p>
 * 脱口秀 服务实现类
 * </p>
 * @since 2022-08-22 08:41:19
 */
@Service
public class TalkShowServiceImpl extends ServiceImpl<TalkShowMapper, TalkShow> implements TalkShowService {

    @Override
    public Page<TalkShow> listTalkShowPage(TalkShow talkShow) {
        Page<TalkShow> page = new Page<>(talkShow.getCurrent(), talkShow.getSize());
        List<TalkShow> talkShows = baseMapper.selectTalkShowList(talkShow, page);
        return page.setRecords(talkShows);
    }

}
