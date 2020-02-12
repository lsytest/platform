package com.platform.service;

import com.platform.dao.ApiDistrabutorMapper;
import com.platform.entity.DistributorVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiDistrabutorService {

    @Autowired
    ApiDistrabutorMapper apiDistrabutorMapper;

    public void saveOrUpdateDistrabutorInfo(DistributorVo distributorVo) throws Exception{
        if(distributorVo.getId()!=null){
            apiDistrabutorMapper.update(distributorVo);
        }else{
            apiDistrabutorMapper.save(distributorVo);
        }
    }


    public DistributorVo getDistrabutorInfo(Integer distrabutorId) throws Exception{
        return apiDistrabutorMapper.queryObject(distrabutorId);
    }

    public List<DistributorVo> getDistrabutorList(){
        return apiDistrabutorMapper.queryList(null);
    }

    public Map getBannerInfo(Integer distrabutorId) throws Exception{
        Map<String, Object> resultMap = new HashMap<String, Object>();
        DistributorVo distributorVo = getDistrabutorInfo(distrabutorId);
        resultMap.put("image", distributorVo.getImage());
        resultMap.put("name", distributorVo.getName());
        resultMap.put("description", distributorVo.getDescription());
        // 获取分销商的粉丝数和购买指数
        Map<String, Object> numMap = apiDistrabutorMapper.queryBannerTotal(distrabutorId);
        resultMap.put("fansNum", numMap.get("fansNum"));
        resultMap.put("buyNum", numMap.get("buyNum"));
        return resultMap;
    }

}
