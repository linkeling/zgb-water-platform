package com.zgb.water.common.base;

/**
 * 功能：响应码枚举类
 *
 * @author wsx
 * @version 2022-06-30
 */
public enum ResponseCode {
    //***** 常用响应码 *****/
    /**
     * 操作成功
     */
    SUCCESS("0"),
    /**
     * 参数异常
     */
    PARAMETER_EXCEPTION("PARAM.EXCEPTION"),
    /**
     * 业务异常
     */
    BUSSINESS_EXCEPTION("BIZ.BUSSINESS_EXCEPTION"),
    /**
     * 未知异常
     */
    UNKNOWN_EXCEPTION("SYS.UNKNOWN_EXCEPTION"),

    //***** 系统级细分 *****/
    /**
     * 服务器内部错误
     */
    SERVER_ERROR("SYS.SERVER_ERROR"),
    /**
     * 系统级方法异常
     */
    SYSTEM_EXCEPTION("SYS.SYSTEM_EXCEPTION"),
    /**
     * 数据库异常
     */
    DB_EXCEPTION("SYS.DB_EXCEPTION"),
    /**
     * 缓存读写异常
     */
    CACHE_EXCEPTION("SYS.CACHE_EXCEPTION"),
    /**
     * 接口调用次数超上限
     */
    CALLS_OVER_LIMIT("SYS.CALL_OVER_LIMIT"),
    /**
     * RPC远程调用异常
     */
    RPC_EXCEPTION("SYS.RPC_EXCEPTION"),

    //***** 业务级细分 *****/
    /**
     * TOKEN失效
     */
    TOKEN_EXPIRATION("BIZ.TOKEN_EXPIRATION"),
    /**
     * SESSION为空或已过期
     */
    SESSION_NULL("BIZ.SESSION_NULL"),
    /**
     * 数据不存在
     */
    DATA_NOT_EXIST("BIZ.DATA_NOT_EXIST"),
    /**
     * 数据冲突
     */
    DATA_CONFLICT("BIZ.DATA_CONFLICT"),
    /**
     * 开始时间大于结束时间
     */
    BEGDATE_GT_ENDDATE("BIZ.BEGDATE_GT_ENDDATE"),
    /**
     * 密码错误
     */
    PASSPORT_ERROR("BIZ.PASSPORT_ERROR"),

    /**
     * 调用妇幼成功标志
     */
    PWC_SUCCESS("200");

    private final String code;

    ResponseCode(String code) {
        this.code = code;
    }

    /**
     * 获取响应码
     */
    public String code() {
        return code;
    }
}

