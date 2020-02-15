/*
 * 类名称:DistributorsEntity.java
 * 包名称:com.platform.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-01-20 20:28:54        lipengjun     初版做成
 *
 * Copyright (c) 2019-2019 微同科技
 */
package com.platform.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实体
 *
 * @author lsy
 * @date 2020-01-20 20:28:54
 */
@TableName("specific_distributors")
@ApiModel(value="分销商对象",description="分销商对象信息")
public class DistributorVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="分销商ID")
    private Integer id;
    /**
     * 分销商名称
     */
    @ApiModelProperty(value="分销商名称")
    private String name;
    /**
     * 分销商描述
     */
    @ApiModelProperty(value="分销商描述")
    private String description;
    /**
     * 分销商图标
     */
    @ApiModelProperty(value="分销商图标")
    private String image;
    /**
     * 分销商地址
     */
    @ApiModelProperty(value="分销商地址")
    private String address;
    /**
     * 父级ID
     */
    @ApiModelProperty(value="父级ID")
    private Integer parentId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    @ApiModelProperty(value="'1=未审核；2=审核成功；3=审核失败")
    private Integer state;

    @ApiModelProperty(value = "利润")
    private Double money;

    private List ids;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List getIds() {
        return ids;
    }

    public void setIds(List ids) {
        this.ids = ids;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
