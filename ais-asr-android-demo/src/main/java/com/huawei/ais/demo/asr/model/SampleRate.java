package com.huawei.ais.demo.asr.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SampleRate {
    @JsonProperty("8k")
    RATE_8K,
    @JsonProperty("16k")
    RATE_16K
}
