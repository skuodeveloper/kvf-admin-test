package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.Depart;
import com.kalvin.kvf.modules.func.mapper.DepartMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @since 2021-04-01 14:33:52
 */
@Service
public class DepartServiceImpl extends ServiceImpl<DepartMapper, Depart> implements DepartService {

    @Override
    public Page<Depart> listDepartPage(Depart depart) {
        Page<Depart> page = new Page<>(depart.getCurrent(), depart.getSize());
        List<Depart> departs = baseMapper.selectDepartList(depart, page);
        return page.setRecords(departs);
    }

}
