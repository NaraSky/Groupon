package com.lb.infrastructure.gateway;

import com.lb.types.enums.ResponseCode;
import com.lb.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 处理拼团活动的回调通知服务，通过HTTP接口向指定地址发送回调通知请求。
 */
@Slf4j
@Service
public class GroupBuyNotifyService {

    @Resource
    private OkHttpClient okHttpClient;

    /**
     * 向指定API地址发送拼团活动的回调通知请求。
     *
     * @param apiUrl                回调通知的目标API接口地址
     * @param notifyRequestDTOJSON  回调请求参数的JSON格式数据
     * @return 服务器响应的字符串内容
     * @throws AppException 当HTTP请求发生异常时抛出
     */
    public String groupBuyNotify(String apiUrl, String notifyRequestDTOJSON) {
        try {
            // 发送HTTP POST请求并获取响应
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(mediaType, notifyRequestDTOJSON);
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            // 处理请求异常并记录错误日志
            log.error("拼团回调 HTTP 接口服务异常 {}", apiUrl, e);
            throw new AppException(ResponseCode.HTTP_EXCEPTION);
        }
    }
}
