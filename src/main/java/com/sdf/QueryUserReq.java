package com.sdf;

import com.sdf.common.IReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author barry
 * @date 2021/8/27 2:15 下午
 * @description
 */
@Data
public class QueryUserReq implements IReq {
    @ApiModelProperty(value = "name", name = "name", example = "34346412", required = true)
    String name;

    @ApiModelProperty(value = "userId", name = "userId", example = "userId", required = true)
    Integer userId;
}
