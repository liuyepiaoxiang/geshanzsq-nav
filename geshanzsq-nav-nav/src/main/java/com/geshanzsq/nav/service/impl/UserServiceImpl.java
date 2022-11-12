package com.geshanzsq.nav.service.impl;

import com.geshanzsq.common.utils.DateUtils;
import com.geshanzsq.nav.domain.User;
import com.geshanzsq.nav.mapper.NavSiteMapper;
import com.geshanzsq.nav.mapper.UserMapper;
import com.geshanzsq.nav.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author andy
 * @since 2022-11-11
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NavSiteMapper navSiteMapper;

    @Override
    public int insertOrUpdateUser(User user) {
        user.setUpdateTime(DateUtils.getNowDate());
        user.setCreateTime(DateUtils.getNowDate());
        int result = userMapper.insertOrUpdateUser(user);
        return result;
    }

    @Override
    public int updatePledgeCount(User user) {
        return userMapper.updatePledgeCount(user);
    }

    @Override
    public long sumClickCountByUserWalletAddress(String userWalletAddress) {
        return navSiteMapper.sumClickCountByUserWalletAddress(userWalletAddress);
    }

}
