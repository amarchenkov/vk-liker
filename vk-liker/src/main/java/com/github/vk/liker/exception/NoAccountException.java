package com.github.vk.liker.exception;

/**
 * Created at 08.09.2016 0:44
 *
 * @author Andrey
 */
public class NoAccountException extends RuntimeException {
    /**
     * Inherited constructor
     *
     * @param message exception message
     */
    public NoAccountException(String message) {
        super(message);
    }
}
