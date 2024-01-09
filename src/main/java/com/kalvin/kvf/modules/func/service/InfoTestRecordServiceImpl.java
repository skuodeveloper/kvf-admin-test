package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.InfoTestRecord;
import com.kalvin.kvf.modules.func.mapper.InfoTestRecordMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @since 2022-06-16 16:59:58
 */
@Service
public class InfoTestRecordServiceImpl extends ServiceImpl<InfoTestRecordMapper, InfoTestRecord> implements InfoTestRecordService {

    @Override
    public Page<InfoTestRecord> listInfoTestRecordPage(InfoTestRecord infoTestRecord) {
        Page<InfoTestRecord> page = new Page<>(infoTestRecord.getCurrent(), infoTestRecord.getSize());
        List<InfoTestRecord> infoTestRecords = baseMapper.selectInfoTestRecordList(infoTestRecord, page);
        return page.setRecords(infoTestRecords);
    }

}
