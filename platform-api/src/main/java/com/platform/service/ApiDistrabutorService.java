package com.platform.service;

import com.platform.dao.ApiDistrabutorMapper;
import com.platform.entity.DistributorVo;
import com.platform.utils.ResourceUtil;
import freemarker.template.SimpleDate;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiDistrabutorService {

    private static String basePath = ResourceUtil.getConfigByName("images.path");

    @Autowired
    ApiDistrabutorMapper apiDistrabutorMapper;

    public void saveOrUpdateDistrabutorInfo(CommonsMultipartFile file, DistributorVo distributorVo) throws Exception{
        // 上传文件并返回地址

        String path = null;
        if(distributorVo.getId()!=null){
            //删除以前的图片
            File oldFile = new File("basePath" + distributorVo.getImage());
            oldFile.delete();
            path = upload(file);
            distributorVo.setImage(path);
            apiDistrabutorMapper.update(distributorVo);
        }else{
            path = upload(file);
            distributorVo.setImage(path);
            distributorVo.setState(1);
            distributorVo.setCreateTime(new Date());
            apiDistrabutorMapper.save(distributorVo);
        }
    }

    /**
     * 文件上传
     */
    private String upload(CommonsMultipartFile file) throws IOException {
        String originFileName = file.getOriginalFilename();
        // 获取后缀
        String ext = originFileName.substring(originFileName.lastIndexOf("."), originFileName.length());
        // 获取当前日期
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        try{
            File newFile = new File(basePath + newFileName + ext);
            if(!newFile.exists()){
                newFile.createNewFile();
            }
            file.transferTo(newFile);
        }catch (IOException e){
            e.printStackTrace();
        }
        return newFileName + ext;
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
