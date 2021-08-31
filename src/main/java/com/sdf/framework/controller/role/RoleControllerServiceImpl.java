package com.sdf.framework.controller.role;

import com.sdf.normal.QueryUserReq;
import com.sdf.common.Resp;
import com.sdf.framework.domain.Role;
import org.springframework.stereotype.Service;

/**
 * @author barry
 * @date 2021/8/31 2:30 下午
 * @description
 */
@Service
public class RoleControllerServiceImpl implements IRoleControllerService{
    @Override
    public Resp<Void> add(AddRoleReq req) {
        return Resp.createSuccessCodeResp();
    }

    @Override
    public Resp<Void> update() {
        return Resp.createSuccessCodeResp();
    }

    @Override
    public Resp<Role> find(QueryUserReq req) {
        return Resp.createSuccessCodeResp(new Role(1,"role"));
    }
}
