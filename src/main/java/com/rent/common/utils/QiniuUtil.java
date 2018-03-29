package com.rent.common.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Exrickx
 */
public class QiniuUtil {

    private final static Logger log= LoggerFactory.getLogger(QiniuUtil.class);

    /**
     * 生成上传凭证，然后准备上传
     */
    private static String accessKey = "foqF1A__-aTEg_8EWj5yb5oi4CFrcWnQluwEN1qV";
    private static String secretKey = "pnm8kAw-kjdaRYPuwpuBOU-5eXc6KXNc4clHIoZg";
    private static String bucket = "xmall";
    private static String origin="http://ow2h3ee9w.bkt.clouddn.com/";
    private static Auth auth = Auth.create(accessKey, secretKey);

    public static String qiniuUpload(String filePath){

        //构造一个带指定Zone对象的配置类 zone2华南
        Configuration cfg = new Configuration(Zone.zone2());

        UploadManager uploadManager = new UploadManager(cfg);

        String localFilePath = filePath;
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return origin+putRet.key;
        }catch(QiniuException ex){
            Response r = ex.response;
            log.warn(r.toString());
            try {
                log.warn(r.bodyString());
                return r.bodyString();
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }

    /**
     * 文件流上传
     * @param file
     * @param key 文件名
     * @return
     */
    public static String qiniuInputStreamUpload(FileInputStream file,String key){

        //构造一个带指定Zone对象的配置类 zone2华南
        Configuration cfg = new Configuration(Zone.zone2());

        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(file,key,upToken,null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return origin+putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.warn(r.toString());
            try {
                log.warn(r.bodyString());
                return r.bodyString();
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }

    public static String getUpToken() {
        return auth.uploadToken(bucket, null, 3600, new StringMap().put("insertOnly", 1));
    }

    public static String qiniuBase64Upload(String data64){

        String key = renamePic(".png");
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        //服务端http://up-z2.qiniup.com
        String url = "http://up-z2.qiniup.com/putb64/-1/key/"+ UrlSafeBase64.encodeToString(key);
        RequestBody rb = RequestBody.create(null, data64);
        Request request = new Request.Builder().
                url(url).
                addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getUpToken())
                .post(rb).build();
        System.out.println(request.headers());
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);
        return origin+key;
    }

    public static String base64Data(String data){

        if(data==null||data.isEmpty()){
            return "";
        }
        String base64 =data.substring(data.lastIndexOf(",")+1);
        return base64;
    }

    /**
     * 以UUID重命名
     * @param fileName
     * @return
     */
    public static String renamePic(String fileName){
        String extName = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID().toString().replace("-","")+extName;
    }

    public static void main(String[] args){
        base64Data("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEASABIAAD/2");
    }
}
