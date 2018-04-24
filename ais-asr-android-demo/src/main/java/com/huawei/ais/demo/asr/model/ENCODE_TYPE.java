package com.huawei.ais.demo.asr.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ENCODE_TYPE {
    WAV("wav");

    String type;
    ENCODE_TYPE(String type) {
        this.type = type;
    }

    @JsonValue
    String getType() {
        return type;
    }
}
