package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.TrainRecord;
import com.kalvin.kvf.modules.func.mapper.TrainRecordMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @since 2022-06-10 09:34:01
 */
@Service
public class TrainRecordServiceImpl extends ServiceImpl<TrainRecordMapper, TrainRecord> implements TrainRecordService {

    @Override
    public Page<TrainRecord> listTrainRecordPage(TrainRecord trainRecord) {
        Page<TrainRecord> page = new Page<>(trainRecord.getCurrent(), trainRecord.getSize());
        List<TrainRecord> trainRecords = baseMapper.selectTrainRecordList(trainRecord, page);
        return page.setRecords(trainRecords);
    }

}
