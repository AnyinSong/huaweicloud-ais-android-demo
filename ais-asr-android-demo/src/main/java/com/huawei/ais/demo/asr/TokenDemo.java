package com.huawei.ais.demo.asr;

import com.huawei.ais.demo.ClientUtils;
import com.huawei.ais.demo.asr.model.ASRReq;
import com.huawei.ais.demo.asr.model.ENCODE_TYPE;
import com.huawei.ais.demo.asr.model.SAMPLE_RATE;
import com.huawei.ais.sdk.util.HttpDataUtils;
import com.huawei.ais.demo.asr.model.ASRRes;
import com.huawei.ais.sdk.util.HttpClientUtils;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpRequestBase;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.message.BasicHeader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;


/**
 * 使用Token认证方式访问服务
 */
public class TokenDemo {

    private static final Log LOGGER = LogFactory.getLog(TokenDemo.class);

    public static void main(String[] args) throws Exception {

        String tokenUri = "/v3/auth/tokens";
        String serviceUri = "/v1.0/voice/asr/sentence";

        //1 在ClientConfig中配置了服务入口信息(endpoint, region)和token方式所需的username, password等信息

        //2 初始化服务调用客户端
        CloseableHttpClient httpClient = HttpClientUtils.acceptsUntrustedCertsHttpClient();

        //2.1 如果要通过代理访问服务请先在ClientConfig中配置代理服务器信息
        //CloseableHttpClient httpClient = HttpClientUtils.acceptsUntrustedCertsHttpClient(ClientUtils.getProxyHost());

        //3 指定要识别的音频文件（短语音识别支持小于1分钟的音频，Base64编码后不超过4M，格式支持wav， 采样率支持8k，16k，单声道）
        String audioFilePath = "data/sentence.wav";

        //4 发起服务调用
        try {
            HttpEntity entity = buildASRSentenceEntity(audioFilePath, ENCODE_TYPE.WAV, SAMPLE_RATE.RATE_8K);
            String asrResult = callASRService(httpClient,
                    ClientUtils.getIamEndpoint() + tokenUri,
                    ClientUtils.getAisEndpoint() + serviceUri,
                    entity);
            LOGGER.info(asrResult);
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            httpClient.close();
        }
    }

    private static String callASRService(CloseableHttpClient httpClient, String tokenUrl, String serviceUrl,
                                         HttpEntity asrSentenceEntity)
            throws IOException {
        //获取token
        String token;
        HttpRequestBase tokenReq = buildTokenRequest(tokenUrl);
        HttpResponse tokenRes = httpClient.execute(tokenReq);
        if (HttpDataUtils.isOKResponded(tokenRes)) {
            token = tokenRes.getHeaders("X-Subject-Token")[0].getValue();
        } else {
            LOGGER.error("fail to get token.");
            LOGGER.error("request:" + HttpDataUtils.requestToString(tokenReq));
            LOGGER.error("response:" + HttpDataUtils.responseToString(tokenRes));
            return null;
        }

        //调用服务
        HttpRequestBase asrSentenceReq = buildASRSentenceRequest(serviceUrl, token, asrSentenceEntity);

        HttpResponse asrSentenceRes = httpClient.execute(asrSentenceReq);

        if (HttpDataUtils.isOKResponded(asrSentenceRes)) {
            ASRRes asrRes = HttpDataUtils.getResponseObject(asrSentenceRes, ASRRes.class);
            //LOGGER.info("response object:" + HttpDataUtils.ObjectToPrettyJsonString(asrRes));
            return asrRes.getResult().getWords();
        } else {
            LOGGER.error("request:" + HttpDataUtils.requestToString(asrSentenceReq));
            return "response:" + HttpDataUtils.responseToString(asrSentenceRes);
        }
    }

    private static HttpRequestBase buildTokenRequest(String tokenUrl) {
        Header[] headers = new Header[]{new BasicHeader("Content-Type", ContentType.APPLICATION_JSON.toString())};
        StringEntity stringEntity = new StringEntity(
                ClientUtils.getTokenReqBody(),
                ContentType.APPLICATION_JSON.getCharset());

        HttpPost httpPost = new HttpPost(tokenUrl);
        httpPost.setHeaders(headers);
        httpPost.setEntity(stringEntity);

        return httpPost;

    }

    private static HttpRequestBase buildASRSentenceRequest(String url, String token, HttpEntity entity) {
        Header[] headers = new Header[]{
                new BasicHeader("X-Auth-Token", token),
                new BasicHeader("Content-Type", ContentType.APPLICATION_JSON.toString())};

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeaders(headers);
        httpPost.setEntity(entity);

        return httpPost;
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
