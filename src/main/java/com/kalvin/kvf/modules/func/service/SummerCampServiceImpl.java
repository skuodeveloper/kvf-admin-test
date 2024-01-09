package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.SummerCamp;
import com.kalvin.kvf.modules.func.mapper.SummerCampMapper;

import java.util.List;

/**
 * <p>
 * 夏令营 服务实现类
 * </p>
 * @since 2022-08-16 14:54:46
 */
@Service
public class SummerCampServiceImpl extends ServiceImpl<SummerCampMapper, SummerCamp> implements SummerCampService {

    @Override
    public Page<SummerCamp> listSummerCampPage(SummerCamp summerCamp) {
        Page<SummerCamp> page = new Page<>(summerCamp.getCurrent(), summerCamp.getSize());
        List<SummerCamp> summerCamps = baseMapper.selectSummerCampList(summerCamp, page);
        return page.setRecords(summerCamps);
    }

}
