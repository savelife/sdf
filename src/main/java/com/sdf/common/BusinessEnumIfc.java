package com.sdf.common;

/**
 * 所有对业务返回体的定义都需要实现该接口
 * 注意: 实现类建议是 Enum 类型
 *
 */
public interface BusinessEnumIfc {

    /**
     * 获取返回体 业务-code
     *
     * @return
     */
    String getBusiRespCode();

    /**
     * 获取返回体 msg信息
     *
     * @return
     */
    String getRespMsg();

}
