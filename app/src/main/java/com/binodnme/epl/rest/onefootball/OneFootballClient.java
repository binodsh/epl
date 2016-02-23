package com.binodnme.epl.rest.onefootball;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by binodnme on 2/22/16.
 */
public class OneFootballClient {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.addHeader("user-agent","curl");
        client.get(url, params, responseHandler);
    }
}
