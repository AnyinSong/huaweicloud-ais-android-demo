package com.huawei.ais.demo.asr.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ASRReq
{
    @JsonProperty("data")
    String data;
    @JsonProperty("encode_type")
    ENCODE_TYPE encodeType;
    @JsonProperty("sample_rate")
    SAMPLE_RATE sampleRate;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ENCODE_TYPE getEncodeType() {
        return encodeType;
    }

    public void setEncodeType(ENCODE_TYPE encodeType) {
        this.encodeType = encodeType;
    }

    public SAMPLE_RATE getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(SAMPLE_RATE sampleRate) {
        this.sampleRate = sampleRate;
    }
}
