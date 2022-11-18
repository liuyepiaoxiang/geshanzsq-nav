package com.geshanzsq.web.controller.user;


import com.geshanzsq.common.core.controller.BaseController;
import com.geshanzsq.common.core.domain.AjaxResult;
import com.geshanzsq.nav.domain.User;
import com.geshanzsq.nav.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author andy
 * @since 2022-11-11
 */
@RestController
@Api(tags = "客户模块", description = "客户信息")
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 新增或更新用户
     */
    @PostMapping(value = "/addUser")
    @ApiOperation(value = "新增客户", notes = "新增客户")
    public AjaxResult addUser(@RequestBody User user){
        int result = userService.insertOrUpdateUser(user);
        return toAjax(result);
    }

    /**
     * 更新用户质押数量
     */
    @PostMapping(value = "/updatePledge")
    public AjaxResult updatePledge(@RequestBody User user){
        if (user.getPledgeCount() < 100) {
            return toAjax(-1);
        }
        int result = userService.updatePledgeCount(user);
        return toAjax(result);
    }

    /**
     * 获取用户发布链接浏览总数
     */
    @GetMapping(value = "/sumClickCount")
    @ApiOperation(value = "统计用户浏览量", notes = "新增客户")
    public AjaxResult updatePledge(String userWalletAddress){
        Long result = userService.sumClickCountByUserWalletAddress(userWalletAddress);
        return AjaxResult.success(result);
    }

}
