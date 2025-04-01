package com.lb.api;

import com.lb.api.response.Response;

public interface IDCCService {

    Response<Boolean> updateConfig(String key, String value);

}
