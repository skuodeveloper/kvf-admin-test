package com.kalvin.kvf.modules.func.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diboot.core.controller.BaseCrudRestController;
import com.kalvin.kvf.modules.func.entity.QuestionBank;
import com.kalvin.kvf.modules.func.mapper.InfoTestMapper;
import com.kalvin.kvf.modules.func.mapper.QuestionBankMapper;
import com.kalvin.kvf.modules.func.vo.AnswerDto;
import com.kalvin.kvf.modules.func.vo.QuestionBankVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.InfoTest;
import com.kalvin.kvf.modules.func.service.InfoTestService;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @since 2022-06-15 09:43:42
 */
@RestController
@RequestMapping("func/infoTest")
public class InfoTestController extends BaseCrudRestController {
    @Resource
    private QuestionBankMapper questionBankMapper;

    @Resource
    private InfoTestMapper infoTestMapper;

    @Autowired
    private InfoTestService infoTestService;

    @RequiresPermissions("func:infoTest:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/infoTest");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/infoTest_edit");
        InfoTest infoTest;
        if (id == null) {
            infoTest = new InfoTest();
        } else {
            infoTest = infoTestService.getById(id);
        }
        mv.addObject("editInfo", infoTest);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(InfoTest infoTest) {
        Page<InfoTest> page = infoTestService.listInfoTestPage(infoTest);
        return R.ok(page);
    }

    @RequiresPermissions("func:infoTest:add")
    @PostMapping(value = "add")
    public R add(InfoTest infoTest) {
        infoTestService.save(infoTest);
        return R.ok();
    }

    @RequiresPermissions("func:infoTest:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        infoTestService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("func:infoTest:edit")
    @PostMapping(value = "edit")
    public R edit(InfoTest infoTest) {
        infoTestService.updateById(infoTest);
        return R.ok();
    }

    @RequiresPermissions("func:infoTest:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        infoTestService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(infoTestService.getById(id));
    }

    @GetMapping(value = "generate")
    public R generate() {
        List<Integer> ids = questionBankMapper.getRandom50();
        String idsStr = StringUtils.join(ids, ",");
        return R.ok(idsStr);
    }

    /**
     * @param userId
     * @return
     */
    @GetMapping(value = "getInfotests")
    public R getInfotests(@RequestParam String userId) {
        List<InfoTest> infoTests = infoTestMapper.getInfotests(userId);
        return R.ok(infoTests);
    }

    /**
     * @param id
     * @return
     */
    @GetMapping(value = "getInfotest")
    public R getInfotest(@RequestParam Integer id) {
        InfoTest infoTest = infoTestService.getById(id);
        List<QuestionBank> questionBanks = infoTestMapper.getInfotest(infoTest.getIds());

        for (QuestionBank o : questionBanks) {
            List<AnswerDto> answerDtos = JSON.parseArray(o.getAnswer(), AnswerDto.class);
            o.setAnswerDtos(answerDtos);

            StringBuilder correct = new StringBuilder();
            for (int i = 0; i < answerDtos.size(); i++) {
                if (answerDtos.get(i).isValue()) {
                    correct.append(i);
                }
            }

            String c = correct.toString().replaceAll("0", "A")
                    .replaceAll("1", "B")
                    .replaceAll("2", "C")
                    .replaceAll("3", "D");

            o.setCorrect(c);
        }

        List<QuestionBankVo> questionBankVos = super.convertToVoAndBindRelations(questionBanks, QuestionBankVo.class);

        return R.ok(questionBankVos);
    }

    /**
     * @return
     */
    @GetMapping(value = "getNewtest")
    public R getNewtest() {
        List<QuestionBank> questionBanks = questionBankMapper.getNewtests();

        for (QuestionBank o : questionBanks) {
            List<AnswerDto> answerDtos = JSON.parseArray(o.getAnswer(), AnswerDto.class);
            o.setAnswerDtos(answerDtos);

            StringBuilder correct = new StringBuilder();
            for (int i = 0; i < answerDtos.size(); i++) {
                if (answerDtos.get(i).isValue()) {
                    correct.append(i);
                }
            }

            String c = correct.toString()
                    .replaceAll("0", "A")
                    .replaceAll("1", "B")
                    .replaceAll("2", "C")
                    .replaceAll("3", "D");

            o.setCorrect(c);
        }

        List<QuestionBankVo> questionBankVos = super.convertToVoAndBindRelations(questionBanks, QuestionBankVo.class);

        return R.ok(questionBankVos);
    }
}

