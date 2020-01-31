/*
 * 类名称:DistributorsServiceImpl.java
 * 包名称:com.platform.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-01-20 20:28:54        lipengjun     初版做成
 *
 * Copyright (c) 2019-2019 微同科技
 */
package com.platform.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.utils.QueryPlus;
import com.platform.dao.DistributorsDao;
import com.platform.entity.DistributorsEntity;
import com.platform.service.DistributorsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author lipengjun
 * @date 2020-01-20 20:28:54
 */
@Service("distributorsService")
public class DistributorsServiceImpl extends ServiceImpl<DistributorsDao, DistributorsEntity> implements DistributorsService {

    @Override
    public List<DistributorsEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<DistributorsEntity> page = new QueryPlus<DistributorsEntity>(params).getPage();
        return page.setRecords(baseMapper.selectDistributorsPage(page, params));
    }

    @Override
    public boolean add(DistributorsEntity distributors) {
        return this.save(distributors);
    }

    @Override
    public boolean update(DistributorsEntity distributors) {
        return this.updateById(distributors);
    }

    @Override
    public boolean delete(Integer id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(Integer[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }

    @Override
    public boolean updateState(DistributorsEntity distributors) {
        Integer flag = baseMapper.updateState(distributors);
        return flag>0?true:false;
    }
}
