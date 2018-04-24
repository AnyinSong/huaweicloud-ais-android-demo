package com.huawei.ais.demo.asr;

import com.huawei.ais.demo.ClientUtils;
import com.huawei.ais.demo.asr.model.ASRReq;
import com.huawei.ais.demo.asr.model.ENCODE_TYPE;
import com.huawei.ais.demo.asr.model.SAMPLE_RATE;
import com.huawei.ais.sdk.util.HttpDataUtils;
import com.huawei.ais.demo.asr.model.ASRRes;
import com.huawei.ais.sdk.AisAkskClient;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;


/**
 * 短语音识别服务的使用示例类
 */
public class AkskDemo {

    private static final Log LOGGER = LogFactory.getLog(AkskDemo.class);

    public static void main(String[] args) {

        String serviceUri = "/v1.0/voice/asr/sentence";

        //1 在ClientConfig中配置了服务入口信息(endpoint, region)

        //2 初始化服务调用客户端
        AisAkskClient aisAkskClient = ClientUtils.getAisAkskClient(ClientUtils.getAuthInfo());

        //2.1 如果要通过代理访问服务请先在ClientConfig中配置代理服务器信息
        //AisAkskClient aisAkskClient = ClientUtils.getAisAkskClient(ClientUtils.getAuthInfo(), ClientUtils.getProxyHost());

        //3 指定要识别的音频文件（短语音识别支持小于1分钟的音频，Base64编码后不超过4M，格式支持wav， 采样率支持8k，16k，单声道）
        String audioFilePath = "data/sentence.wav";

        //4 发起服务调用
        try {
            HttpEntity entity = buildASRSentenceEntity(audioFilePath, ENCODE_TYPE.WAV, SAMPLE_RATE.RATE_8K);
            String asrResult = callASRService(aisAkskClient, serviceUri, entity);
            LOGGER.info(asrResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            aisAkskClient.close();
        }
    }

    private static String callASRService(AisAkskClient serviceClient, String serviceUri, HttpEntity asrSentenceEntity)
            throws IOException {

        HttpResponse asrSentenceRes = serviceClient.post(serviceUri, asrSentenceEntity);

        if (HttpDataUtils.isOKResponded(asrSentenceRes)) {
            ASRRes asrRes = HttpDataUtils.getResponseObject(asrSentenceRes, ASRRes.class);
            //LOGGER.info("response object:" + HttpDataUtils.ObjectToPrettyJsonString(asrRes));
            return asrRes.getResult().getWords();
        } else {
            LOGGER.error("request body:" + HttpDataUtils.entityToPrettyString(asrSentenceEntity));
            return "response:" + HttpDataUtils.responseToString(asrSentenceRes);
        }
    }

    private static HttpEntity buildASRSentenceEntity(String filePath, ENCODE_TYPE encodeType, SAMPLE_RATE sampleRate)
            throws IOException {
        String fileBase64Str = ClientUtils.getBase64Str(filePath);

        ASRReq asrReq = new ASRReq();
        asrReq.setData(fileBase64Str);
        asrReq.setEncodeType(encodeType);
        asrReq.setSampleRate(sampleRate);

        return HttpDataUtils.ObjectToHttpEntity(asrReq);
    }

}
