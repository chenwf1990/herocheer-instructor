package com.herocheer.instructor.utils;

import com.trs.idm.util.Base64Util;
import com.trs.idm.util.StringHelper;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class IDSAPIClient {
    private String serviceUrl;
    private String secretKey;
    private String digestAlgorithm;
    private String responseType;
    private static Logger logger = LoggerFactory.getLogger(IDSAPIClient.class);
    private static final String ERROR_SECRETKEY_ISEMPTY = "error.secretkey.isempty";
    private static final String ERROR_DIGESTALGORITHM_ISEMPTY = "error.digestalgorithm.isempty";
    private static final String ERRPR_DIGEST_ILLEGALITY = "error.digest.illegality";
    private static final String ERROR_RESPONSE_ILLEGALITY = "error.response.illegality";
    private static final String ERROR_CALLING_EXCEPTION = "error.calling.exception";

    public IDSAPIClient(String serviceUrl, String secretKey, String digestAlgorithm, String responseType) {
        this.serviceUrl = serviceUrl;
        this.secretKey = secretKey;
        this.digestAlgorithm = digestAlgorithm;
        this.responseType = responseType;
    }

    public String processor(String appName, String data) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("primitive parameters from caller serviceUrl = " + this.serviceUrl + " , secretKey = " + this.secretKey + " , digestAlgorithm = " + this.digestAlgorithm + " , appName = " + appName + " , data = " + data + " , responseType = " + this.responseType);
        }

        if (StringHelper.isEmpty(this.secretKey)) {
            logger.error("secretKey [" + this.secretKey + "] is null , can not call ids api !");
            throw new Exception("error.secretkey.isempty");
        } else if (StringHelper.isEmpty(this.digestAlgorithm)) {
            logger.error("digestAlgorithm [" + this.digestAlgorithm + "] is null , can not call ids api !");
            throw new Exception("error.digestalgorithm.isempty");
        } else {
            PostMethod methodPost = new PostMethod(this.serviceUrl);
            if (this.isHttpSSOAPI()) {
                methodPost.addParameter("coAppName", appName);
            } else {
                methodPost.addParameter("appName", appName);
            }

            methodPost.addParameter("type", this.responseType);
            String encryptedData = "";

            try {
                encryptedData = IDSAPIEncryptUtil.encrypt(data, this.digestAlgorithm, this.secretKey);
            } catch (Exception var15) {
                logger.error("encrypt data failed data, digestAlgorithm, secretKey =(" + data + "," + this.digestAlgorithm + "," + this.secretKey + ") !", var15);
                throw var15;
            }

            methodPost.addParameter("data", encryptedData);
            if (logger.isDebugEnabled()) {
                logger.debug("final to post ids request is [" + methodPost + "] !");
            }

            HttpClient httpClient = new HttpClient();

            try {
                httpClient.executeMethod(methodPost);
            } catch (Exception var14) {
                logger.error("calling ids api has exception !", var14);
                throw new Exception("error.calling.exception", var14);
            }

            String responsePost = new String(methodPost.getResponseBody(), "UTF-8");
            System.out.println(responsePost);
            if (logger.isDebugEnabled()) {
                logger.debug("original response from ids is [" + responsePost + "] !");
            }

            String[] digestAndResult = StringHelper.split(responsePost, "&");
            if (digestAndResult.length != 2) {
                logger.error("response from ids format is illegality ! after split is digestAndResult [" + digestAndResult + "]");
                throw new Exception("error.response.illegality");
            } else {
                String digestOfServer = digestAndResult[0];
                String result = digestAndResult[1];
                String afterDESResult = DesEncryptUtil.decrypt(result, this.secretKey);
                if (logger.isDebugEnabled()) {
                    logger.debug("after des decrypt response is [" + afterDESResult + "] , decrypt secretKey is [" + this.secretKey + "] !");
                }

                String plaintextResponse = Base64Util.decode(afterDESResult, "UTF-8");
                if (logger.isDebugEnabled()) {
                    logger.debug("after base64 decode plaintext response is [" + plaintextResponse + "] , before base64 decode response is [" + afterDESResult + "] !");
                }

                MessageDigest sd = MessageDigest.getInstance(this.digestAlgorithm);
                sd.update(plaintextResponse.getBytes("UTF-8"));
                String digestOfAgent = StringHelper.toString(sd.digest());
                if (!digestOfAgent.equals(digestOfServer)) {
                    logger.error("response from ids digest [" + digestOfServer + "] not eq client create digest [" + digestOfAgent + "] !");
                    throw new Exception("error.digest.illegality");
                } else {
                    return plaintextResponse;
                }
            }
        }
    }

    private boolean isHttpSSOAPI() {
        return this.serviceUrl.contains("idsServiceType=httpssoservice");
    }
}