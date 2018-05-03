package com.hebeitv.news.test;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * 网络请求类
 * 
 * @Description:
 * @ClassName:	AsyncHttp 
 * @author: XM 
 * @date: 2015年11月6日 上午09:09:30
 *
 */
public class AsyncHttp {

	private AsyncHttpClient mHttpClient = new AsyncHttpClient();
	private Gson mGson = new Gson();

	public AsyncHttp() {
		mHttpClient.setTimeout(20 * 1000);
	}

	public void get(IRequest request, IHttpListener listener) {
		if (request != null) {
//			Log.v("log", "server url:" + request.getUrl() + "?"
//					+ request.getParams().toString());
			mHttpClient.get(request.getUrl() + "?"
					+ request.getParams().toString(), new HttpResponse(request,
					listener));
		}
	}

	public void post(IRequest request, IHttpListener listener) {
//		Log.v("log", "server url:" + request.getUrl());
		System.out.println("server url:" + request.getUrl());
		mHttpClient.post(request.getContext(), request.getUrl(),
				request.getParams(), new HttpResponse(request, listener));
	}

	public void cancelPost() {
		// client.cancelRequests(mContext, true);
		// client.cancelRequests(context, mayInterruptIfRunning);
	}

	public interface IHttpListener {
		void onStart(int requestId);

		void onSuccess(int requestId, Response response);

		void onFailure(int requestId, Throwable error);

	}

	class HttpResponse extends TextHttpResponseHandler {

		private IHttpListener mHttpListener;
		private IRequest mRequest;

		public HttpResponse(IRequest request, IHttpListener httpListener) {
			mHttpListener = httpListener;
			mRequest = request;
		}

		@Override
		public void onStart() {
			if (mHttpListener != null) {
				mHttpListener.onStart(mRequest.getRequestId());
			}
		}

		@Override
		public void onFailure(int code, Header[] arg1, String arg2,
				Throwable arg3) {
//			Log.e("log", "[onFailure] error url:" + mRequest.getUrl() + "?"
//					+ mRequest.getParams().toString(), arg3);
			if (mHttpListener != null) {
				mHttpListener.onFailure(mRequest.getRequestId(), arg3);
			}

		}

		@Override
		public void onSuccess(int code, Header[] headers, String content) {
//			Log.v("log", "http content:" + content);
			if (mHttpListener != null && content != null) {
				try {
					if (mRequest.getResponseType() == IRequest.RESPONSE_OBJECT_TYPE) {
						mHttpListener.onSuccess(
								mRequest.getRequestId(),
								(Response) mGson.fromJson(content,
										mRequest.getParserType()));
					} else if (mRequest.getResponseType() == IRequest.RESPONSE_TYPE) {

					}

				} catch (JsonSyntaxException e) {
					onFailure(code, headers, content, e);
				}
			}
		}
	}

}
