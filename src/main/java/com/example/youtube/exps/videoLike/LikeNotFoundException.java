package com.example.youtube.exps.videoLike;

import com.example.youtube.enums.Language;

public class LikeNotFoundException extends RuntimeException {
    public LikeNotFoundException(String message) {
        super(message);
    }
}
