package com.taobao.taobaoadmin.service.IMPL;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.taobao.taobaoadmin.dto.OssCallbackResult;
import com.taobao.taobaoadmin.dto.OssPolicyResult;
import com.taobao.taobaoadmin.service.OssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class OssServiceImpl implements OssService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OssServiceImpl.class);

    @Value("${aliyun.oss.policy.expire}")
    private int ALIYUN_OSS_EXPIRE;
    @Value("${aliyun.oss.maxSize}")
    private int ALIYUN_OSS_MAX_SIZE;
    @Value("${aliyun.oss.callback}")
    private String ALIYUN_OSS_CALLBACK;
    @Value("${aliyun.oss.bucketName}")
    private String ALIYUN_OSS_BUCKET_NAME;
    @Value("${aliyun.oss.endpoint}")
    private String ALIYUN_OSS_ENDPOINT;
    @Value("${aliyun.oss.dir.prefix}")
    private String ALIYUN_OSS_DIR_PREFIX;


    @Autowired
    private OSSClient ossClient;


    /**
     * 签名生成
     * 签名直传服务,响应客户端发送给应用服务器的GET消息(在Controller上看get或者POST)
     * @return
     */
    @Override
    public OssPolicyResult policy() {
        OssPolicyResult result = new OssPolicyResult();
        //存储目录
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dir = ALIYUN_OSS_DIR_PREFIX + sdf.format(new Date());

        //签名有效期
        long expireEndTime = System.currentTimeMillis() + ALIYUN_OSS_EXPIRE * 1000;
        Date expiration = new Date(expireEndTime);

        //文件大小
        long maxSize = ALIYUN_OSS_MAX_SIZE * 1024 * 1024;

        //提交节点
        String action = "http://" + ALIYUN_OSS_BUCKET_NAME + "." + ALIYUN_OSS_ENDPOINT;

        try {
            PolicyConditions policyCond = new PolicyConditions();
            policyCond.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
            policyCond.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            String postPolicy = ossClient.generatePostPolicy(expiration, policyCond);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String policy = BinaryUtil.toBase64String(binaryData);
            String signatrue = ossClient.calculatePostSignature(postPolicy);

            //访问身份验证中用到用户标识
            result.setAccessKeyId(ossClient.getCredentialsProvider().getCredentials().getAccessKeyId());
            //用户表单上传的策略，经过Base64编码过的字符串
            result.setPolicy(policy);
            //对policy签名后的字符串
            result.setSignature(signatrue);
            //上传文件夹路径前缀
            result.setDir(dir);
            //OSS对外服务的访问域名
            result.setHost(action);
        } catch (Exception e) {
            LOGGER.error("签名生成失败", e);
        }

        return result;
    }

    @Override
    public OssCallbackResult callback(HttpServletRequest request) {
        OssCallbackResult result = new OssCallbackResult();
        String filename = request.getParameter("filename");
        filename = "http://".concat(ALIYUN_OSS_BUCKET_NAME).concat(".").concat(ALIYUN_OSS_ENDPOINT).concat("/").concat(filename);

        result.setFilename(filename);
        result.setSize(request.getParameter("size"));
        result.setMineType(request.getParameter("mimeType"));
        result.setWidth(request.getParameter("width"));
        result.setHeight(request.getParameter("height"));

        return result;
    }
}
