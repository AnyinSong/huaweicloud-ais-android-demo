huaweicloud-ais-android-demo
=
调整了华为云人工智能服务的java sdk和demo的依赖库，使其代码能方便的移植到Android平台运行，
同时重构了demo代码结构以方便开发app时复用代码。<br/>
AIS Java SDK: https://github.com/huaweicloudsdk/ais-sdk/tree/master/java

## 代码移植步骤
1. 将`ais-asr-android-demo/dependency`的依赖库拷贝到工程的依赖库中
2. 移植调用服务代码
    * 服务调用流程参考`com.huawei.ais.demo.asr.sentence.AkskDemo.main(String[] args)`方法
    * 将代码复制到Android App后有一处需要改动，
      位置:`com.huawei.ais.demo.ClientUtils.getBase64Str(String filePath)`<br/>
      Android平台使用`return android.util.Base64.encodeToString(fileData, android.util.Base64.DEFAULT);`<br/>
      Java SE平台上试运行使用`return  com.cloud.sdk.util.Base64.encodeAsString(fileData);`


## 建议及其他说明
1. 建议先在Java SE平台配置好认证信息后试运行demo，运行成功后再开始迁移代码到Android App中
2. 应用中建议使用AK/SK方式认证,因为Token方式会存在token过期需要定期刷新的麻烦。
3. 如果要在Java SE平台试运行demo,除了上面提到的Base64编码方法的切换外还需要添加Android SDK的`android.jar`
   和用于本地测试的`android-all.jar`(参见`pom.xml`中注释`<!--如果想在java SE平台试运行运行样例，则需要下面的两个依赖-->`)。
4. demo代码是基于Android 6.0(API 23)版本编译的，理论上可以在6.0及以上的版本运行。
