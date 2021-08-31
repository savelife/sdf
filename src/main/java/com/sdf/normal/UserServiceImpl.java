package com.sdf.normal;

import com.sdf.common.Resp;
import org.springframework.stereotype.Service;

/**
 * @author barry
 * @date 2021/8/27 11:23 上午
 * @description
 */
@Service
public class UserServiceImpl implements IUserService{

    @Override
    public Resp<Void> add(AddUserReq req) {
        return Resp.createSuccessCodeResp();
    }

    @Override
    public Resp<Void> update(UpdateUserReq req) {
        return null;
    }

    @Override
    public Resp<String> find(QueryUserReq req) {
        return Resp.createSuccessCodeResp("suc");
    }

}
