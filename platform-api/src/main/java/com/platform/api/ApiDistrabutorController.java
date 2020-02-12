package com.platform.api;

import com.platform.annotation.IgnoreAuth;
import com.platform.entity.DistributorVo;
import com.platform.entity.OrderVo;
import com.platform.service.ApiDistrabutorService;
import com.platform.service.ApiOrderService;
import com.platform.util.ApiBaseAction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "分销商", value="分销商", description = "分销商相关的接口")
@RestController
@RequestMapping("/api/distrabutor")
public class ApiDistrabutorController extends ApiBaseAction {

    @Autowired
    private ApiDistrabutorService apiDistrabutorService;

    @Autowired
    private ApiOrderService apiOrderService;

    /**
     * 保存或更新分销商
     * @param distributorVo
     * @param file 接受文件上传
     * @return
     */
    @PostMapping("saveOrUpdateDistrabutorInfo")
    @IgnoreAuth
    @ApiOperation(value="保存或更新分销商信息", httpMethod = "POST")
    public Object saveDistrabutorInfo(@RequestParam("file") CommonsMultipartFile file, DistributorVo distributorVo){
        String message = (distributorVo.getId()!=null&&!"".equals(distributorVo))?"更新分销商":"保存分销商";
        try{
            apiDistrabutorService.saveOrUpdateDistrabutorInfo(file, distributorVo);
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
    @IgnoreAuth
    @ApiOperation(value="分销商列表", httpMethod = "GET")
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
    @GetMapping("getDistrabutorInfo/{distrabutorId}")
    @IgnoreAuth
    @ApiOperation(value = "获取分销商详情", httpMethod = "GET")
    public Object getDistrabutorInfo(@ApiParam(value = "分销商ID") @PathVariable("distrabutorId") Integer distrabutorId){
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
    @GetMapping("getDistrabutorOrder/{distrabutorId}")
    @IgnoreAuth
    @ApiOperation(value = "获取分销商订单", httpMethod = "GET")
    public Object getDistrabutorOrder(@ApiParam(value = "分销商ID") @PathVariable("distrabutorId") Integer distrabutorId){
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("distrabutorId", distrabutorId);
        try{
            List<OrderVo> orderList = apiOrderService.queryList(paraMap);
            // 支付订单（待支付、已完成、退款）
            Map<String, Object> resultMap = new HashMap<String, Object>();
            List<OrderVo> unpayList = new ArrayList<OrderVo>();
            List<OrderVo> havepayList = new ArrayList<OrderVo>();
            List<OrderVo> refundList = new ArrayList<OrderVo>();
            for (OrderVo orderVo : orderList) {
                if(orderVo.getOrder_status() == 0){
                    unpayList.add(orderVo);
                }else if(orderVo.getOrder_status()==401){
                    refundList.add(orderVo);
                }else{
                    havepayList.add(orderVo);
                }
            }
            resultMap.put("unpayList", unpayList);
            resultMap.put("havepayList", havepayList);
            resultMap.put("refundList", refundList);
            return toResponsSuccess(resultMap);
        }catch (Exception e){
            return toResponsFail("获取分销商订单列表失败");
        }
    }

    /**
     * 获取首页分销商信息
     * @param distrabutorId
     * @return
     */
    @GetMapping("getDistrabutorBanner/{distrabutorId}")
    @IgnoreAuth
    @ApiOperation(value = "分销商粉丝数、购买数", httpMethod = "GET")
    public Object getDistrabutorBanner(@ApiParam(value = "分销商ID") @PathVariable("distrabutorId") Integer distrabutorId){

        try{
            Map resultMap = apiDistrabutorService.getBannerInfo(distrabutorId);
            return toResponsSuccessForSelect(resultMap);
        }catch (Exception e){
            return toResponsFail("获取首页banner信息失败:"+e.getMessage());
        }
    }
}
