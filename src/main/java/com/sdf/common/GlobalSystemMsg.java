package com.sdf.common;

import com.sdf.common.BusinessEnumIfc;

/**
 * 全局系统异常
 * 该类不对外开放，因为业务中通过返回成功返回体即可
 */
public enum GlobalSystemMsg implements BusinessEnumIfc {

    /**
     *
     */
    OK("MSCS0000", "成功"),
    SYSTEM_ERROR("MSCE9999", "失败"),
    URI_NOT_EXIST("MSCE9998", "接口不存在！"),
    ;

    private String errorCode;

    private String errorMsg;

    GlobalSystemMsg(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public String getBusiRespCode() {
        return errorCode;
    }

    @Override
    public String getRespMsg() {
        return errorMsg;
    }
}
