package com.tuliu.translate.common;

public enum ResultCode {
    SUCCESS_HAS_MESSAGE(200), SUCCESS_NO_MESSAGE(200), FAIL(2), FAIL_NO_LOGIN(3);
    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
