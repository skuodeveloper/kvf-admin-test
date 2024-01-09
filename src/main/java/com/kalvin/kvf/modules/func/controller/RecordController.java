package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.Record;
import com.kalvin.kvf.modules.func.service.RecordService;

import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @since 2021-03-30 16:24:21
 */
@RestController
@RequestMapping("func/record")
public class RecordController extends BaseController {

    @Autowired
    private RecordService recordService;

    @RequiresPermissions("func:record:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/record");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/record_edit");
        Record record;
        if (id == null) {
            record = new Record();
        } else {
            record = recordService.getById(id);
        }
        mv.addObject("editInfo", record);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(Record record) {
        Page<Record> page = recordService.listRecordPage(record);
        return R.ok(page);
    }

    @RequiresPermissions("func:record:add")
    @PostMapping(value = "add")
    public R add(Record record) {
        recordService.save(record);
        return R.ok();
    }

    @RequiresPermissions("func:record:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        recordService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("func:record:edit")
    @PostMapping(value = "edit")
    public R edit(Record record) {
        recordService.updateById(record);
        return R.ok();
    }

    @RequiresPermissions("func:record:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        recordService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(recordService.getById(id));
    }

    @GetMapping(value = "getForUser")
    public R getForUser(@RequestParam String userid) {
        List<Record> records = recordService.getBaseMapper()
                .selectList(new LambdaQueryWrapper<Record>()
                        .eq(Record::getUserid, userid)
                        .orderByDesc(Record::getCreateTime));
        return R.ok(records);
    }
}

