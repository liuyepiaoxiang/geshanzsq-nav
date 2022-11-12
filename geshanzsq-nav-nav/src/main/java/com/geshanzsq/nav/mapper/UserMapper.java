package com.geshanzsq.nav.mapper;

import com.geshanzsq.nav.domain.User;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author andy
 * @since 2022-11-11
 */
public interface UserMapper {

    /**
     * 新增或更新用户
     *
     * @param user 用户
     * @return 结果
     */
    public int insertOrUpdateUser(User user);

    /**
     * 更新用户的质押数量
     *
     * @param user 用户
     * @return 结果
     */
    public int updatePledgeCount(User user);
}
