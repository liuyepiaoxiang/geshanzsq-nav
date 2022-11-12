package com.geshanzsq.web.controller.user;


import com.geshanzsq.common.core.controller.BaseController;
import com.geshanzsq.common.core.domain.AjaxResult;
import com.geshanzsq.nav.domain.User;
import com.geshanzsq.nav.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 新增或更新用户
     */
    @PostMapping
    public AjaxResult adduser(@RequestBody User user){
        int result = userService.insertOrUpdateUser(user);
        return toAjax(result);
    }

    /**
     * 更新用户质押数量
     */
    @PostMapping(value = "/updatePledge")
    public AjaxResult updatePledge(@RequestBody User user){
        if (user.getPledgeCount() < 100) return toAjax(-1);
        int result = userService.updatePledgeCount(user);
        return toAjax(result);
    }

}
