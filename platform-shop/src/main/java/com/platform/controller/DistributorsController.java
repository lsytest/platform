/*
 * 类名称:DistributorsController.java
 * 包名称:com.platform.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-01-20 20:28:54        lipengjun     初版做成
 *
 * Copyright (c) 2019-2019 微同科技
 */
package com.platform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.annotation.SysLog;
import com.platform.utils.R;
import com.platform.controller.AbstractController;
import com.platform.entity.DistributorsEntity;
import com.platform.service.DistributorsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author lipengjun
 * @date 2020-01-20 20:28:54
 */
@RestController
@RequestMapping("specific/distributors")
public class DistributorsController extends AbstractController {
    @Autowired
    private DistributorsService distributorsService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return R
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("specific:distributors:list")
    public R queryAll(@RequestParam Map<String, Object> params) {
        List<DistributorsEntity> list = distributorsService.queryAll(params);

        return R.ok().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return R
     */
    @GetMapping("/list")
    @RequiresPermissions("specific:distributors:list")
    public R list(@RequestParam Map<String, Object> params) {
        Page page = distributorsService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return R
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("specific:distributors:info")
    public R info(@PathVariable("id") Integer id) {
        DistributorsEntity distributors = distributorsService.getById(id);

        return R.ok().put("distributors", distributors);
    }

    /**
     * 新增
     *
     * @param distributors distributors
     * @return R
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("specific:distributors:save")
    public R save(@RequestBody DistributorsEntity distributors) {

        distributorsService.add(distributors);

        return R.ok();
    }

    /**
     * 修改
     *
     * @param distributors distributors
     * @return R
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("specific:distributors:update")
    public R update(@RequestBody DistributorsEntity distributors) {

        distributorsService.update(distributors);

        return R.ok();
    }

    /**
     * 根据主键删除
     *
     * @param ids ids
     * @return R
     */
    @SysLog("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("specific:distributors:delete")
    public R delete(@RequestBody Integer[] ids) {
        distributorsService.deleteBatch(ids);

        return R.ok();
    }
}
