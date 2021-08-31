package com.sdf.framework.controller.role;

import com.sdf.QueryUserReq;
import com.sdf.common.Resp;
import com.sdf.framework.domain.Role;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author barry
 * @date 2021/8/30 10:40 上午
 * @description
 */
@RestController
public interface IRoleControllerService {

    @PostMapping("/user/role/add")
    Resp<Void> add(AddRoleReq req);

    @PostMapping("/user/role/update")
    Resp<Void> update();

    @PostMapping("/user/role/find")
    Resp<Role> find(QueryUserReq req);
}
