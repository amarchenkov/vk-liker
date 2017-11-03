package com.github.vk.bot.groupservice.exception;

public class NoActiveAccountException extends RuntimeException {
    public NoActiveAccountException(Throwable e) {
        super(e);
    }

    public NoActiveAccountException() {
        super();
    }
}
