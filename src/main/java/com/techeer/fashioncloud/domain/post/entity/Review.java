package com.techeer.fashioncloud.domain.post.entity;

public enum Review {
    추웠다(0),
    서늘했다(1),
    괜찮았다(2),
    따뜻했다(3),
    더웠다(4);

    private final int code;

    Review(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}