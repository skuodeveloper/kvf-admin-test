package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.Train;
import com.kalvin.kvf.modules.func.mapper.TrainMapper;

import java.util.List;

/**
 * <p>
 * 在线培训表 服务实现类
 * </p>
 * @since 2022-06-08 09:36:53
 */
@Service
public class TrainServiceImpl extends ServiceImpl<TrainMapper, Train> implements TrainService {

    @Override
    public Page<Train> listTrainPage(Train train) {
        Page<Train> page = new Page<>(train.getCurrent(), train.getSize());
        List<Train> trains = baseMapper.selectTrainList(train, page);
        return page.setRecords(trains);
    }

    @Override
    public Page<Train> getTrainPage(Train train) {
        Page<Train> page = new Page<>(train.getCurrent(), train.getSize());
        List<Train> trains = baseMapper.getTrainList(train, page);
        return page.setRecords(trains);
    }
}
