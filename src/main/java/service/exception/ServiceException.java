// src/service/exception/ServiceException.java
package service.exception;

/**
 * 业务层异常
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}