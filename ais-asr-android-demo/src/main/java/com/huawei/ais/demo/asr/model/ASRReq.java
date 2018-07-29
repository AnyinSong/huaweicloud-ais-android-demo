package com.huawei.ais.demo.asr.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ASRReq
{
    @JsonProperty("data")
    String data;
    @JsonProperty("encode_type")
    EncodeType encodeType;
    @JsonProperty("sample_rate")
    SampleRate sampleRate;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public EncodeType getEncodeType() {
        return encodeType;
    }

    public void setEncodeType(EncodeType encodeType) {
        this.encodeType = encodeType;
    }

    public SampleRate getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(SampleRate sampleRate) {
        this.sampleRate = sampleRate;
    }
}
