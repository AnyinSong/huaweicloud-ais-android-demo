package com.huawei.ais.demo.asr.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ASRRes
{
    @JsonProperty("result")
    Result result;

    public Result getResult() {
        return result;
    }

    public static class Result
    {
        @JsonProperty("words")
        String words;

        public String getWords() {
            return words;
        }
    }
}
