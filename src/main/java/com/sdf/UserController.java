package com.sdf;

import com.sdf.common.Resp;
import com.sdf.framework.controller.AbstractBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author barry
 * @date 2021/8/27 11:44 上午
 * @description
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户管理")
public class UserController extends AbstractBaseController {

    @Autowired
    UserServiceImpl userService;

    @ApiOperation(value = "增加用户", notes = "增加用户")
    @PostMapping("add")
    public Resp<Void> addUser(@RequestBody AddUserReq req) {
        return handle( fun -> userService.add(req),req);
    }

    @ApiOperation(value = "更新用户", notes = "更新用户")
    @PostMapping("/update")
    public Resp<Void> updateUser(@RequestBody UpdateUserReq req) {
        return handle( fun -> userService.update(req),req);
    }

    @ApiOperation(value = "查询用户", notes = "查询用户")
    @PostMapping("/query")
    public Resp<String> findUser(@RequestBody QueryUserReq req) {
        return handle( fun -> userService.find(req),req);
    }


}
