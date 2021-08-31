package com.sdf.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回体信息
 */
@ApiModel(value = "返回体")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public final class Resp<T> implements Serializable {

    private static final long serialVersionUID = 4768835308338670054L;

    @ApiModelProperty(value = "状态码", name = "状态码", required = true)
    private String code;

    @ApiModelProperty(value = "状态码描述", name = "状态码描述", required = true)
    private String msg;

    @ApiModelProperty(value = "数据对象", name = "数据对象")
    private T data;

    private Resp() {
    }

    public final static Resp createSuccessCodeResp(Object data) {
        Resp resp = new Resp();
        resp.data = data;
        resp.code = GlobalSystemMsg.OK.getBusiRespCode();
        resp.msg = GlobalSystemMsg.OK.getRespMsg();
        return resp;
    }

    public final static Resp createSuccessCodeResp() {
        Resp resp = new Resp();
        resp.code = GlobalSystemMsg.OK.getBusiRespCode();
        resp.msg = GlobalSystemMsg.OK.getRespMsg();
        return resp;
    }

    /**
     * @param businessException
     * @return
     */
    public final static Resp createFailerResp(BusinessException businessException) {
        BusinessEnumIfc businessEnum = businessException.getBusinessEnumIfc();
        return createFailerResp(businessEnum);
    }

    public final static Resp createFailerResp(BusinessEnumIfc businessEnum) {
        Resp resp = new Resp();
        resp.code = businessEnum.getBusiRespCode();
        resp.msg = businessEnum.getRespMsg();
        return resp;
    }

    public final static Resp createFailerResp(BusinessEnumIfc businessEnum, Object data) {
        Resp resp = createFailerResp(businessEnum);
        resp.data = data;
        return resp;
    }

}
