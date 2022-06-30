package com.zgb.water.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 请求响应的数据模型
 *
 * @author wsx
 * @version 2022-06-30
 */
@ApiModel(value="请求响应的数据模型")
@SuppressWarnings("unchecked")
public class ResponseDataModel<T> {
    public static ResponseDataModel ok(){
        return new ResponseDataModel(ResponseCode.SUCCESS,null);
    }

    public ResponseDataModel(){
        this(ResponseCode.SUCCESS,null);
    }

    public static <T> ResponseDataModel<T> ok(T data){
        ResponseDataModel<T> model = new ResponseDataModel(ResponseCode.SUCCESS,null);
        model.setData(data);
        return model;
    }

    public ResponseDataModel(ResponseCode responseCode, String msg) {
        this.code = responseCode.code();
        this.msg = msg;
    }

    public ResponseDataModel(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseDataModel(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /** 接口返回状态码 */
    @ApiModelProperty(value="接口返回状态码", required=true)
    @JsonView(BaseView.DataModel.class)
    private String code;

    /** 接口返回消息 */
    @ApiModelProperty(value="接口返回消息", required=true)
    @JsonView(BaseView.DataModel.class)
    private String msg;

    /** 返回数据 */
    @ApiModelProperty(value="返回数据", required=true)
    @JsonView(BaseView.DataModel.class)
    private T data;

    /** 时间戳 */
    @ApiModelProperty(value="时间戳", required=true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(BaseView.DataModel.class)
    private Date timestamp = new Date();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
