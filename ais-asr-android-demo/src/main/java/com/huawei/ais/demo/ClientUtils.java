package com.huawei.ais.demo;

import com.huawei.ais.common.AuthInfo;
import com.huawei.ais.common.ProxyHostInfo;
import com.huawei.ais.sdk.AisAkskClient;
import com.huawei.ais.sdk.AisAkskClientWithProxy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 此处为调用服务的的工具函数
 */
public class ClientUtils {

    //https://stackoverflow.com/questions/3038392/do-java-arrays-have-a-maximum-size
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private static final ClientConfig CONFIG = new ClientConfig();

    private static final AuthInfo HEC_AUTH = new AuthInfo(CONFIG.getAisEndpoint(), CONFIG.getRegion(), CONFIG.getAk(), CONFIG.getSk());
    private static final ProxyHostInfo PROXY_INFO = new ProxyHostInfo(CONFIG.getProxyHost(), CONFIG.getProxyPort(),
            CONFIG.getProxyUsername(), CONFIG.getProxyPassword());

    private static AisAkskClient client = null;
    private static AisAkskClient clientWithProxy = null;

    public static AuthInfo getAuthInfo() {
        return HEC_AUTH;
    }

    public static ProxyHostInfo getProxyHost() {
        return PROXY_INFO;
    }

    public static String getAisEndpoint() {
        return CONFIG.getAisEndpoint();
    }

    public static String getIamEndpoint() {
        return CONFIG.getIamEndpoint();
    }

    public static synchronized AisAkskClient getAisAkskClient(AuthInfo authInfo) {
        if (client == null) {
            client = new AisAkskClient(authInfo);
        }
        return client;
    }

    public static synchronized AisAkskClient getAisAkskClient(AuthInfo authInfo, ProxyHostInfo proxyHostInfo) {
        if (clientWithProxy == null) {
            clientWithProxy = new AisAkskClientWithProxy(authInfo, proxyHostInfo);
        }
        return clientWithProxy;
    }

    public static String getTokenReqBody() {
        return "{" +
                "    \"auth\":{" +
                "        \"identity\":{" +
                "            \"password\":{" +
                "                \"user\":{" +
                "                    \"name\":\"" + CONFIG.getUsername() + "\"," +
                "                    \"password\":\"" + CONFIG.getPassword() + "\"," +
                "                    \"domain\":{" +
                "                        \"name\":\"" + CONFIG.getAccountName() + "\"" +
                "                    }" +
                "                }" +
                "            }," +
                "            \"methods\":[\"password\"]" +
                "        }," +
                "        \"scope\":{" +
                "            \"project\":{" +
                "                 \"name\":\"" + CONFIG.getRegion() + "\"" +
                "            }" +
                "        }" +
                "    }" +
                "}";
    }

    public static String getBase64Str(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.length() >= Integer.MAX_VALUE - 8) {
            throw new IllegalArgumentException(filePath + " is too large!");
        }
        byte[] fileData = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(fileData);
        fis.close();

        //return android.util.Base64.encodeToString(fileData, android.util.Base64.DEFAULT);
        return  com.cloud.sdk.util.Base64.encodeAsString(fileData);
    }
}
