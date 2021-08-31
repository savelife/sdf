package com.sdf.normal;

import com.sdf.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * @author barry
 * @date 2021/8/27 11:31 上午
 * @description
 */
public abstract class AbstractBaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final <T> Resp<T> handle(Function<IReq,Resp<T>> function,
        IReq req) {
        logger.info("请求参数：{}", req);
        Resp<T> resp = null;
        try {
            resp = function.apply(req);
        } catch (BusinessException businessException) {
            logger.warn("出现业务异常！" + getBusinessExceptionLine(businessException));
            // 业务类型异常，根据BusinessException拼装为对应的Resp返回给前端
            BusinessEnumIfc businessEnumIfc = businessException.getBusinessEnumIfc();
            if (businessEnumIfc == null) {
                // 如果出现的业务异常没有设置 com.demo.BusinessEnumIfc 信息，那么属于代码漏洞
                logger.error("没有获取到异常详细信息！", businessException);
                resp = Resp.createFailerResp(GlobalSystemMsg.SYSTEM_ERROR);
            } else {
                logger.warn("业务异常详细信息: {}",
                    businessEnumIfc.getClass().getSimpleName() + "." + businessEnumIfc.toString());
                resp = Resp.createFailerResp(businessException);
            }
        } catch (Exception e) {
            // 未知异常，返回一个通用的返回体信息，如 系统繁忙 等
            logger.error("出现未知异常！", e);
            resp = Resp.createFailerResp(GlobalSystemMsg.SYSTEM_ERROR);
        }
        return resp;
    }


    /**
     * 获取业务异常报错位置
     *
     * @param businessException
     * @return
     */
    private String getBusinessExceptionLine(BusinessException businessException) {
        StackTraceElement[] stackTraces = businessException.getStackTrace();
        int index = stackTraces[0].getMethodName().equals("ex") ? 1 : 0;
        return new StringBuffer(" 业务异常位置: ").append(stackTraces[index].getClassName()).append("(")
            .append(stackTraces[index].getLineNumber()).append(")").toString();
    }
}
