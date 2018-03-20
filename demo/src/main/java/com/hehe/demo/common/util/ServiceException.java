package com.hehe.demo.common.util;

/**
 * @author hehe
 * @date  2017/8/12
 * @email qinghe101@qq.com
 */
public class ServiceException extends RuntimeException{
    private static final long serialVersionUID = 657378777056762471L;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
