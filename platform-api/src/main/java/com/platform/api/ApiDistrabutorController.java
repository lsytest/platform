package com.platform.api;

import com.platform.entity.DistributorVo;
import com.platform.service.ApiDistrabutorService;
import com.platform.util.ApiBaseAction;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "分销商")
@RestController
    @RequestMapping("/api/distrabutor")
public class ApiDistrabutorController extends ApiBaseAction {

    @Autowired
    ApiDistrabutorService apiDistrabutorService;

    /**
     * 保存或更新分销商
     * @param distributorVo
     * @return
     */
    @PostMapping("saveOrUpdateDistrabutorInfo")
    public Object saveDistrabutorInfo(DistributorVo distributorVo){
        String message = distributorVo.getId()!=null?"保存分销商":"更新分销商";
        try{
            apiDistrabutorService.saveOrUpdateDistrabutorInfo(distributorVo);
            return toResponsMsgSuccess(message + "成功");
        }catch (Exception e){
            return toResponsFail(message + "失败");
        }
    }

    /**
     * 获取分销商列表
     * @return
     */
    @GetMapping("list")
    public Object distrabutorList(){
        try{
            List<DistributorVo> distributorVoList = apiDistrabutorService.getDistrabutorList();
            return toResponsSuccessForSelect(distributorVoList);
        }catch (Exception e){
            return toResponsFail("获取分销商列表失败");
        }
    }

    /**
     * 根据分销商ID获取分销商信息
     * @param distrabutorId
     * @return
     */
    @GetMapping("getDistrabutorInfo")
    public Object getDistrabutorInfo(@RequestParam("distrabutorId") Integer distrabutorId){
        DistributorVo distributorVo = null;
        try{
            distributorVo = apiDistrabutorService.getDistrabutorInfo(distrabutorId);
            return toResponsSuccessForSelect(distributorVo);
        }catch (Exception e){
            return toResponsFail("获取分销商失败");
        }
    }

    /**
     * 获取分销订单
     * @param distrabutorId 分销商Id
     * @return
     */
    @GetMapping("getDistrabutorOrder")
    public Object getDistrabutorOrder(@RequestParam("distrabutorId") Integer distrabutorId){


        return null;
    }

    /**
     * 获取首页分销商信息
     * @param distrabutorId
     * @return
     */
    @GetMapping("getDistrabutorInfo")
    public Object getDistrabutorBanner(@RequestParam("distrabutorId") Integer distrabutorId){

        try{
            Map resultMap = apiDistrabutorService.getBannerInfo(distrabutorId);
            return toResponsSuccessForSelect(resultMap);
        }catch (Exception e){
            return toResponsFail("获取首页banner信息失败");
        }
    }
}
