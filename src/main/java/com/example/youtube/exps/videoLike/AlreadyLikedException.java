package com.example.youtube.exps.videoLike;
public class AlreadyLikedException extends RuntimeException {
    public AlreadyLikedException(String massage) {
        super(massage);
    }
}
