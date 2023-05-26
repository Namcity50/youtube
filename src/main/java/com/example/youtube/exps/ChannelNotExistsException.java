package com.example.youtube.exps;

import com.example.youtube.enums.Language;

public class ChannelNotExistsException extends RuntimeException {
    public ChannelNotExistsException(String message) {
        super(message);
    }
}
