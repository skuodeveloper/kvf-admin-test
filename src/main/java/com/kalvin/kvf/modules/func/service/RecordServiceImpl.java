package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.Record;
import com.kalvin.kvf.modules.func.mapper.RecordMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @since 2021-03-30 16:24:21
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Override
    public Page<Record> listRecordPage(Record record) {
        Page<Record> page = new Page<>(record.getCurrent(), record.getSize());
        List<Record> records = baseMapper.selectRecordList(record, page);
        return page.setRecords(records);
    }

}
