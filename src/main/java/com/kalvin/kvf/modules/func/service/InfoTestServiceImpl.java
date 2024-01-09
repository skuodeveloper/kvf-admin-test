package com.kalvin.kvf.modules.func.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.kalvin.kvf.modules.func.entity.InfoTest;
import com.kalvin.kvf.modules.func.mapper.InfoTestMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @since 2022-06-15 09:43:42
 */
@Service
public class InfoTestServiceImpl extends ServiceImpl<InfoTestMapper, InfoTest> implements InfoTestService {

    @Override
    public Page<InfoTest> listInfoTestPage(InfoTest infoTest) {
        Page<InfoTest> page = new Page<>(infoTest.getCurrent(), infoTest.getSize());
        List<InfoTest> infoTests = baseMapper.selectInfoTestList(infoTest, page);
        return page.setRecords(infoTests);
    }

}
