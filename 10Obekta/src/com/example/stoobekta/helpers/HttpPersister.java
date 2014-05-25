package com.example.stoobekta.helpers;

import org.apache.http.entity.StringEntity;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpPersister {

	private static final String BASE_URL = "http://100nationalsites.apphb.com/api/";

	public static void Get(Context context, String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(context, getAbsoluteUrl(url), params, responseHandler);
	}
	
	public static void Post(Context context, String url, StringEntity entity,
			AsyncHttpResponseHandler responseHandler) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(context, getAbsoluteUrl(url), entity, "application/json", responseHandler);
		
	}
	
	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}
}
