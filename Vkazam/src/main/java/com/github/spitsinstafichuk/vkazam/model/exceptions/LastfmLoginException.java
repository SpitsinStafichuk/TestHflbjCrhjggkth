package com.github.spitsinstafichuk.vkazam.model.exceptions;

public class LastfmLoginException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public LastfmLoginException() {
        super();
    }

    public LastfmLoginException(String message) {
        super(message);
    }
}
