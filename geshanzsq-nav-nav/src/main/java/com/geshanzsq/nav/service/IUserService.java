package com.geshanzsq.nav.service;

import com.geshanzsq.nav.domain.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author andy
 * @since 2022-11-11
 */
public interface IUserService {
	int insertOrUpdateUser(User user);

	int updatePledgeCount(User user);

	Long sumClickCountByUserWalletAddress(String userWalletAddress);
}
