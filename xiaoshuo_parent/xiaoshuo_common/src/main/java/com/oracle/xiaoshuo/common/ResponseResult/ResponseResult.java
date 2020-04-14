package com.oracle.xiaoshuo.common.ResponseResult;


public class ResponseResult {
    private String message;
    public ResponseResult() {

    }
    public ResponseResult(String massage) {
        this.message=massage;

    }
    public static  ResponseResult success(String message)
    {
        return new ResponseResult(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
