package com.geshanzsq.nav.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.geshanzsq.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author andy
 * @since 2022-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity implements Serializable  {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户钱包地址
     */
    @NotBlank(message = "钱包地址不能为空")
    private String walletAddress;

    /**
     * 质押数量
     */
    private Integer pledgeCount;

    /**
     * 头像地址
     */
    private String avatar;

}
