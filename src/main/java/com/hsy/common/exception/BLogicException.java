/**
 * 
 */
package com.hsy.common.exception;

/**
 * @author 张梓枫
 * @date:  2019年3月14日 下午2:00:04  
 * @Description:  自定义业务异常类
 */
public class BLogicException extends RuntimeException{

    private static final long serialVersionUID = -7434726551028162354L;
    
    public BLogicException() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public BLogicException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public BLogicException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public BLogicException(Throwable cause) {
        super(cause);
    }
}
