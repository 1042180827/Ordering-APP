package com.stone.conf.constant.error;

public enum ErrorEnum {

    /*
     * 错误信息
     * */
    E_400("400", "请求处理异常，请稍后再试"),

    E_10008("10008", "账户已存在"),

    E_10009("10009", "账户不存在或密码错误"),

    E_10006("10006", "模板文件不存在，请上传后下载"),

    E_10005("10005", "上传文件格式有误，请核对后导入"),

    E_20011("20011", "登陆已过期,请重新登陆"),

    E_90003("90003", "缺少必填参数");

    private String errorCode;

    private String errorMsg;

    ErrorEnum() {
    }

    ErrorEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
