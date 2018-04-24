package com.huawei.ais.demo.asr.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SAMPLE_RATE {
    RATE_8K("8k"),
    RATE_16K("16k");

    String rate;
    SAMPLE_RATE(String rate){
        this.rate = rate;
    }

    @JsonValue
    String getRate() {
        return rate;
    }
}
