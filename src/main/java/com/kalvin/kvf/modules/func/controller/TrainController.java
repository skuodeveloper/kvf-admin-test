package com.kalvin.kvf.modules.func.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kalvin.kvf.common.controller.BaseController;
import com.kalvin.kvf.common.dto.R;
import com.kalvin.kvf.modules.func.entity.Train;
import com.kalvin.kvf.modules.func.entity.TrainRecord;
import com.kalvin.kvf.modules.func.mapper.TrainMapper;
import com.kalvin.kvf.modules.func.service.TrainRecordService;
import com.kalvin.kvf.modules.func.service.TrainService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * 在线培训表 前端控制器
 * </p>
 *
 * @since 2022-06-08 09:36:53
 */
@RestController
@RequestMapping("func/train")
public class TrainController extends BaseController {

    @Autowired
    private TrainService trainService;

    @Resource
    private TrainMapper trainMapper;

    @Resource
    private TrainRecordService trainRecordService;

    @RequiresPermissions("func:train:index")
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("func/train");
    }

    @GetMapping(value = "edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = new ModelAndView("func/train_edit");
        Train train;
        if (id == null) {
            train = new Train();
        } else {
            train = trainService.getById(id);
        }
        mv.addObject("editInfo", train);
        return mv;
    }

    @GetMapping(value = "list/data")
    public R listData(Train train) {
        Page<Train> page = trainService.listTrainPage(train);
        return R.ok(page);
    }

    @RequiresPermissions("func:train:add")
    @PostMapping(value = "add")
    public R add(Train train) {
        train.setCreateTime(new Date());
        trainService.save(train);
        return R.ok();
    }

    @RequiresPermissions("func:train:del")
    @PostMapping(value = "batchdel")
    public R batchdel(@RequestParam("ids") List<Long> ids) {
        trainService.removeByIds(ids);
        return R.ok();
    }

    @RequiresPermissions("func:train:edit")
    @PostMapping(value = "edit")
    public R edit(Train train) {
        trainService.updateById(train);
        return R.ok();
    }

    @RequiresPermissions("func:train:del")
    @PostMapping(value = "del/{id}")
    public R del(@PathVariable Long id) {
        trainService.removeById(id);
        return R.ok();
    }

    @GetMapping(value = "get/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(trainService.getById(id));
    }

    @GetMapping(value = "getVideos")
    public R getVideos(Train train) {
        Page<Train> page = trainService.getTrainPage(train);
        return R.ok(page);
    }

    @GetMapping(value = "getDepts")
    public R getDepts() {
        List<String> depts = trainMapper.getDepts();
        return R.ok(depts);
    }

    @PostMapping(value = "finish")
    public R finish(@RequestParam String userId, @RequestParam Integer trainId) {
        try {
            TrainRecord trainRecord = new TrainRecord();
            trainRecord.setUserId(userId);
            trainRecord.setTrainId(trainId);
            trainRecordService.save(trainRecord);
            return R.ok();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "/videoPlayer", method = RequestMethod.GET)
    public void player2(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getServletContext().getRealPath("/static/my/video/interview.mp4");
        path = System.getProperty("user.dir") + "\\userfile\\"
                + request.getParameter("path").replace("/", "\\");

        BufferedInputStream bis = null;
        try {
            File file = new File(path);
            if (file.exists()) {
                long p = 0L;
                long toLength = 0L;
                long contentLength = 0L;
                int rangeSwitch = 0;
                long fileLength;
                String rangBytes = "";
                fileLength = file.length();

                // get file content
                InputStream ins = new FileInputStream(file);
                bis = new BufferedInputStream(ins);

                // tell the client to allow accept-ranges
                response.reset();
                response.setHeader("Accept-Ranges", "bytes");

                // client requests a file block download start byte
                String range = request.getHeader("Range");
                if (range != null && range.trim().length() > 0 && !"null".equals(range)) {
                    response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
                    rangBytes = range.replaceAll("bytes=", "");
                    if (rangBytes.endsWith("-")) { // bytes=270000-
                        rangeSwitch = 1;
                        p = Long.parseLong(rangBytes.substring(0, rangBytes.indexOf("-")));
                        contentLength = fileLength - p; // 客户端请求的是270000之后的字节（包括bytes下标索引为270000的字节）
                    } else { // bytes=270000-320000
                        rangeSwitch = 2;
                        String temp1 = rangBytes.substring(0, rangBytes.indexOf("-"));
                        String temp2 = rangBytes.substring(rangBytes.indexOf("-") + 1, rangBytes.length());
                        p = Long.parseLong(temp1);
                        toLength = Long.parseLong(temp2);
                        contentLength = toLength - p + 1; // 客户端请求的是 270000-320000 之间的字节
                    }
                } else {
                    contentLength = fileLength;
                }

                // 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。
                // Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
                response.setHeader("Content-Length", new Long(contentLength).toString());

                // 断点开始
                // 响应的格式是:
                // Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
                if (rangeSwitch == 1) {
                    String contentRange = new StringBuffer("bytes ").append(new Long(p).toString()).append("-")
                            .append(new Long(fileLength - 1).toString()).append("/")
                            .append(new Long(fileLength).toString()).toString();
                    response.setHeader("Content-Range", contentRange);
                    bis.skip(p);
                } else if (rangeSwitch == 2) {
                    String contentRange = range.replace("=", " ") + "/" + new Long(fileLength).toString();
                    response.setHeader("Content-Range", contentRange);
                    bis.skip(p);
                } else {
                    String contentRange = new StringBuffer("bytes ").append("0-").append(fileLength - 1).append("/")
                            .append(fileLength).toString();
                    response.setHeader("Content-Range", contentRange);
                }

                String fileName = file.getName();
                response.setContentType("application/octet-stream");
                response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

                OutputStream out = response.getOutputStream();
                int n = 0;
                long readLength = 0;
                int bsize = 1024;
                byte[] bytes = new byte[bsize];
                if (rangeSwitch == 2) {
                    // 针对 bytes=27000-39000 的请求，从27000开始写数据
                    while (readLength <= contentLength - bsize) {
                        n = bis.read(bytes);
                        readLength += n;
                        out.write(bytes, 0, n);
                    }
                    if (readLength <= contentLength) {
                        n = bis.read(bytes, 0, (int) (contentLength - readLength));
                        out.write(bytes, 0, n);
                    }
                } else {
                    while ((n = bis.read(bytes)) != -1) {
                        out.write(bytes, 0, n);
                    }
                }
                out.flush();
                out.close();
                bis.close();
            }
        } catch (IOException ie) {
            // 忽略 ClientAbortException 之类的异常
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

