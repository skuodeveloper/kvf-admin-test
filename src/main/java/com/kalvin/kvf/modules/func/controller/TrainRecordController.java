package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.TrainRecord;
import com.kalvin.kvf.modules.func.service.TrainRecordService;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 * @since 2022-06-10 09:34:01
 */
@RestController
@RequestMapping("func/trainRecord")
public class TrainRecordController extends BaseController {

    @Autowired
    private TrainRecordService trainRecordService;

    @RequiresPermissions("func:trainRecord:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/trainRecord");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/trainRecord_edit");
        TrainRecord trainRecord;
        if (id == null) {
            trainRecord = new TrainRecord();
        } else {
            trainRecord = trainRecordService.getById(id);
        }
        mv.addObject("editInfo", trainRecord);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(TrainRecord trainRecord) {
        Page<TrainRecord> page = trainRecordService.listTrainRecordPage(trainRecord);
        return R.ok(page);
    }

    @RequiresPermissions("func:trainRecord:add")
    @PostMapping(value = "add")
    public R add(TrainRecord trainRecord) {
        trainRecordService.save(trainRecord);
        return R.ok();
    }

    @RequiresPermissions("func:trainRecord:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        trainRecordService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("func:trainRecord:edit")
    @PostMapping(value = "edit")
    public R edit(TrainRecord trainRecord) {
        trainRecordService.updateById(trainRecord);
        return R.ok();
    }

    @RequiresPermissions("func:trainRecord:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        trainRecordService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(trainRecordService.getById(id));
    }

}

