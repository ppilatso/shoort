package com.bafana.shorturl.exception;

public class LinkNotFoundException extends RuntimeException {

    public LinkNotFoundException(String code) {
        super("Link not found for code: " + code);
    }
}