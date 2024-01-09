package com.kalvin.kvf.modules.func.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diboot.core.controller.BaseCrudRestController;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.common.utils.ShiroKit;
import com.kalvin.kvf.modules.func.entity.QuestionBank;
import com.kalvin.kvf.modules.func.mapper.QuestionBankMapper;
import com.kalvin.kvf.modules.func.service.QuestionBankService;
import com.kalvin.kvf.modules.func.vo.AnswerDto;
import com.kalvin.kvf.modules.func.vo.QuestionBankVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 题库 前端控制器
 * </p>
 *
 * @since 2020-07-20 10:24:50
 */
@RestController
@RequestMapping("func/questionBank")
public class QuestionBankController extends BaseCrudRestController {
    @Resource
    private QuestionBankService questionBankService;

    @Resource
    QuestionBankMapper questionBankMapper;

    @RequiresPermissions("func:questionBank:index")
    @GetMapping("index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("func/questionBank");
        return mv;
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/questionBank_edit");
        QuestionBank questionBank;
        if (id == null) {
            questionBank = new QuestionBank();
        } else {
            questionBank = questionBankService.getById(id);

            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                List<AnswerDto> answerDtos = mapper.readValue(questionBank.getAnswer(),
                        new TypeReference<List<AnswerDto>>() {
                        });
                questionBank.setAnswerDtos(answerDtos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mv.addObject("editInfo", questionBank);

        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(QuestionBank questionBank) {
        Long userId = ShiroKit.getUser().getId();
        questionBank.setCreateBy(userId);

        Page<QuestionBank> page = questionBankService.listQuestionBankPage(questionBank);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        for (QuestionBank o : page.getRecords()) {
            try {
                List<AnswerDto> answerDtos = mapper.readValue(o.getAnswer(),
                        new TypeReference<List<AnswerDto>>() {
                        });
                o.setAnswerDtos(answerDtos);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return R.ok(page);
    }

    @RequiresPermissions("func:questionBank:add")
    @PostMapping(value = "add")
    public R add(QuestionBank questionBank) {
        questionBank.setAnswer(questionBank.getAnswer().replaceAll("PASSWWORD", "\""));
        questionBank.setCreateBy(ShiroKit.getUser().getId());
        questionBank.setCreateTime(new Date());
        questionBankService.save(questionBank);
        return R.ok();
    }

    @RequiresPermissions("func:questionBank:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        questionBankService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("func:questionBank:edit")
    @PostMapping(value = "edit")
    public R edit(QuestionBank questionBank) {
        questionBank.setAnswer(questionBank.getAnswer().replaceAll("PASSWWORD", "\""));
        questionBank.setUpdateBy(ShiroKit.getUser().getId());
        questionBankService.updateById(questionBank);
        return R.ok();
    }

    @RequiresPermissions("func:questionBank:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        questionBankService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(questionBankService.getById(id));
    }

    @GetMapping(value = "getClassify")
    public R getClassify() {
        try {
            return R.ok(questionBankMapper.getClassify());
        } catch (Exception ex) {
            return R.fail(ex.getMessage());
        }
    }

    @GetMapping(value = "getRandQuestion")
    public R getRandQuestion() {
        try {
            List<QuestionBank> single = questionBankService.list(
                    new QueryWrapper<QuestionBank>()
                            .eq("status", 0)
                            .eq("subject_type", 1)
                            .last("limit 5")
                            .orderByDesc("RAND()"));

            List<QuestionBank> multi = questionBankService.list(
                    new QueryWrapper<QuestionBank>()
                            .eq("status", 0)
                            .eq("subject_type", 2)
                            .last("limit 3")
                            .orderByDesc("RAND()"));

            List<QuestionBank> judge = questionBankService.list(
                    new QueryWrapper<QuestionBank>()
                            .eq("status", 0)
                            .eq("subject_type", 3)
                            .last("limit 2")
                            .orderByDesc("RAND()"));

            List<QuestionBank> questionBanks = new ArrayList<>(single);
            questionBanks.addAll(multi);
            questionBanks.addAll(judge);

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
        } catch (Exception ex) {
            return R.fail(ex.getMessage());
        }
    }

    @GetMapping(value = "getRandClassifyQuestion")
    public R getRandClassifyQuestion(@RequestParam String classify) {
        try {
            List<QuestionBank> single = questionBankService.list(
                    new QueryWrapper<QuestionBank>()
                            .eq("status", 0)
                            .eq("subject_type", 1)
                            .eq("classify", classify)
                            .last("limit 5")
                            .orderByDesc("RAND()"));

            List<QuestionBank> multi = questionBankService.list(
                    new QueryWrapper<QuestionBank>()
                            .eq("status", 0)
                            .eq("subject_type", 2)
                            .eq("classify", classify)
                            .last("limit 3")
                            .orderByDesc("RAND()"));

            List<QuestionBank> judge = questionBankService.list(
                    new QueryWrapper<QuestionBank>()
                            .eq("status", 0)
                            .eq("subject_type", 3)
                            .eq("classify", classify)
                            .last("limit 2")
                            .orderByDesc("RAND()"));

            List<QuestionBank> questionBanks = new ArrayList<>(single);
            questionBanks.addAll(multi);
            questionBanks.addAll(judge);

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
        } catch (Exception ex) {
            return R.fail(ex.getMessage());
        }
    }
}

