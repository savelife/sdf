package com.sdf;

import com.sdf.common.Resp;

/**
 * @author barry
 * @date 2021/8/27 11:21 上午
 * @description
 */
public interface IUserService{

    Resp<Void> add(AddUserReq req);

    Resp<Void> update(UpdateUserReq req);

    Resp<String> find(QueryUserReq req);
}
