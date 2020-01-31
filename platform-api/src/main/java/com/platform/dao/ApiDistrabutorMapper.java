package com.platform.dao;

import com.platform.entity.DistributorVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface ApiDistrabutorMapper extends BaseDao<DistributorVo>{
    Map queryBannerTotal(@Param("distrabutorId") Integer distrabutorId);
}
