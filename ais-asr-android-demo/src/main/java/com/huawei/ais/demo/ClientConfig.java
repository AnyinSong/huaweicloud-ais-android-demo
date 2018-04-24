package com.huawei.ais.demo;

/**
 * 调用服务的公共配置
 */
public class ClientConfig {

    /*云服务入口信息，无论使用ak/sk方式访问还是token方式访问都需要配置ais服务端点信息和区域信息*/
    private String region = "cn-north-1";   //可从http://developer.huaweicloud.com/dev/endpoint 查询
    private String aisEndpoint = "https://ais.cn-north-1.myhwclouds.com"; //查询方式和endPoint相同

    /*如果使用ak/sk方式访问服务则需要配置ak和sk*/
    private String ak = "*****";
    private String sk = "*******";

    /*如果使用token方式访问服务怎需要配置IAM认证的端点信息以及用户名、密码、账号名*/
    private String iamEndpoint = "https://iam.cn-north-1.myhuaweicloud.com";
    private String username = "username";
    private String password = "******";
    private String accountName = "acountname"; //可从https://console.huaweicloud.com/iam/#/myCredential查询, 账号名

    /*如果需要通过代理才能访问服务则需配置代理信息*/
    private String proxyHost = "proxy.xxxxx.com";
    private int proxyPort = 8080;
    private String proxyUsername = "proxy username";
    private String proxyPassword = "*****";

    public String getAisEndpoint() {
        return aisEndpoint;
    }

    public String getRegion() {
        return region;
    }

    public String getAk() {
        return ak;
    }

    public String getSk() {
        return sk;
    }

    public String getIamEndpoint() {
        return iamEndpoint;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }
}
