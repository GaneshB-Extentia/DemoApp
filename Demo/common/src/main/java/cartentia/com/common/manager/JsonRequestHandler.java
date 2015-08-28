package cartentia.com.common.manager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;

/**
 * Created by Abhijeet.Bhosale on 8/28/2015.
 */
public class JsonRequestHandler<T> extends Request<T> {

    private Response.Listener<T> requestFinishedListener;
    private Response.ErrorListener errorListener;
    private Class<T> request;
    private Class<T> responseClass;

    public static final String CHARSET = "utf-8";

    public JsonRequestHandler(int methodType, String url, Class<T> request, Class<T> response, Response.Listener<T> requestFinishedListener, Response.ErrorListener errorListener)

    {
        super(methodType, url, errorListener);
        this.requestFinishedListener = requestFinishedListener;
        this.errorListener = errorListener;
        this.request = request;
        this.responseClass = response;

    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String responseString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            T response = new GsonBuilder().create().fromJson(responseString, responseClass);
            Response<T> result = Response.success(response, HttpHeaderParser.parseCacheHeaders(networkResponse));
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }

    }

    @Override
    protected void deliverResponse(T t) {
        requestFinishedListener.onResponse(t);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (request != null) {
            String requestString = new Gson().toJson(request);
            try {
                return requestString.getBytes(CHARSET);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return super.getBody();
    }
}
