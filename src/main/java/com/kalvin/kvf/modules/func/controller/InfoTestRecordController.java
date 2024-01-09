package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.InfoTestRecord;
import com.kalvin.kvf.modules.func.service.InfoTestRecordService;

import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @since 2022-06-16 16:59:58
 */
@RestController
@RequestMapping("func/infoTestRecord")
public class InfoTestRecordController extends BaseController {

    @Autowired
    private InfoTestRecordService infoTestRecordService;

    @RequiresPermissions("func:infoTestRecord:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/infoTestRecord");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/infoTestRecord_edit");
        InfoTestRecord infoTestRecord;
        if (id == null) {
            infoTestRecord = new InfoTestRecord();
        } else {
            infoTestRecord = infoTestRecordService.getById(id);
        }
        mv.addObject("editInfo", infoTestRecord);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(InfoTestRecord infoTestRecord) {
        Page<InfoTestRecord> page = infoTestRecordService.listInfoTestRecordPage(infoTestRecord);
        return R.ok(page);
    }

    @RequiresPermissions("func:infoTestRecord:add")
    @PostMapping(value = "add")
    public R add(InfoTestRecord infoTestRecord) {
        infoTestRecordService.save(infoTestRecord);
        return R.ok();
    }

    @RequiresPermissions("func:infoTestRecord:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        infoTestRecordService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("func:infoTestRecord:edit")
    @PostMapping(value = "edit")
    public R edit(InfoTestRecord infoTestRecord) {
        infoTestRecordService.updateById(infoTestRecord);
        return R.ok();
    }

    @RequiresPermissions("func:infoTestRecord:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        infoTestRecordService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(infoTestRecordService.getById(id));
    }

    /**
     * 获取新警测试前40名
     * @return
     */
    @GetMapping(value = "getTop40")
    public R getTop40() {
        List<InfoTestRecord> infoTestRecords = infoTestRecordService.list(
                new LambdaQueryWrapper<InfoTestRecord>()
                        .eq(InfoTestRecord::getTestId, 4)
                        .orderByDesc(InfoTestRecord::getScore)
                        .last("limit 40")
        );
        return R.ok(infoTestRecords);
    }
}

