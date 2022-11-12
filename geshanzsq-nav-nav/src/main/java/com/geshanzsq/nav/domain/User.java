package com.geshanzsq.nav.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.geshanzsq.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author andy
 * @since 2022-11-11
 */
@Data
@ApiModel(description= "用户信息")
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity implements Serializable  {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户id", required = false)
    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名称", required = false)
    private String userName;

    /**
     * 用户钱包地址
     */
    @NotBlank(message = "钱包地址不能为空")
    @ApiModelProperty(value = "用户钱包地址", required = true)
    private String walletAddress;

    /**
     * 质押数量
     */
    @ApiModelProperty(value = "用户质押数量", required = false)
    private Integer pledgeCount;

    /**
     * 头像地址
     */
    @ApiModelProperty(value = "用户头像", required = false)
    private String avatar;

}
