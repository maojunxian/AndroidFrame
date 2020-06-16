package com.ylean.expand.network.builder;

import com.ylean.expand.network.MyOkHttp;
import com.ylean.expand.network.callback.MyCallback;
import com.ylean.expand.network.response.IResponseHandler;
import com.ylean.expand.utils.Log;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * put builder
 * Created by tsy on 16/12/06.
 */
public class PutBuilder extends OkHttpRequestBuilderHasParam<PutBuilder> {

    private String mJsonParams = "";
    public PutBuilder(MyOkHttp myOkHttp){
        super(myOkHttp);
    }
    /**
     * json格式参数
     * @param json
     * @return
     */
    public PutBuilder jsonParams(String json) {
        this.mJsonParams = json;
        return this;
    }
    @Override
    public void enqueue(IResponseHandler responseHandler) {
        try {
            if(mUrl == null || mUrl.length() == 0) {
                throw new IllegalArgumentException("url can not be null !");
            }

            Request.Builder builder = new Request.Builder().url(mUrl);
            appendHeaders(builder, mHeaders);

            if (mTag != null) {
                builder.tag(mTag);
            }

            if(mJsonParams.length() > 0) {      //上传json格式参数
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mJsonParams);
                builder.put(body);
            } else {        //普通kv参数
                FormBody.Builder encodingBuilder = new FormBody.Builder();
                appendParams(encodingBuilder, mParams);
                builder.put(encodingBuilder.build());
            }
            Request request = builder.build();
            mMyOkHttp.getOkHttpClient()
                    .newCall(request)
                    .enqueue(new MyCallback(responseHandler));
        } catch (Exception e) {
            Log.e("Put enqueue error:" + e.getMessage());
            responseHandler.onFailure(0, e.getMessage());
        }
    }
    private void appendParams(FormBody.Builder builder, Map<String, String> params) {

        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
    }
}