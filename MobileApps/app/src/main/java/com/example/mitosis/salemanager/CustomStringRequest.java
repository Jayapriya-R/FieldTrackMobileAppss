package com.example.mitosis.salemanager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CustomStringRequest extends StringRequest {
    private final Map<String, String> _params;
    private int _method;
    private String _url;

    /**
     * @param method
     * @param url
     * @param params        A {@link HashMap} to post with the request. Null is allowed
     *                      and indicates no parameters will be posted along with request.
     * @param listener
     * @param errorListener
     */
    public CustomStringRequest(int method, String url, Map<String, String> params, Response.Listener<String> listener,
                               Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);

        _method = method;
        _params = params;
        _url = url;

        //Set the retry policy to not to retry and the timeout to 60s.
        this.setRetryPolicy(new DefaultRetryPolicy(60000, 0, 0));
    }

    @Override
    protected Map<String, String> getParams() {
        return _params;
    }

    @Override
    public String getUrl() {
        if (_method == Request.Method.GET) {
            StringBuilder stringBuilder = new StringBuilder(_url);
            if (_params != null) {
                Iterator<Map.Entry<String, String>> iterator = _params.entrySet().iterator();
                int i = 1;
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    if (i == 1) {
                        stringBuilder.append("?" + entry.getKey() + "=" + entry.getValue());
                    } else {
                        stringBuilder.append("&" + entry.getKey() + "=" + entry.getValue());
                    }
                    iterator.remove(); // avoids a ConcurrentModificationException
                    i++;
                }
                _url = stringBuilder.toString();
            }
        }
        return _url;
    }

    /* (non-Javadoc)
     * @see com.android.volley.toolbox.StringRequest#parseNetworkResponse(com.android.volley.NetworkResponse)
     */
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        // since we don't know which of the two underlying network vehicles
        // will Volley use, we have to handle and store session cookies manually

        //Paybee.get().checkSessionCookie(response.headers);

        return super.parseNetworkResponse(response);
    }

    /* (non-Javadoc)
     * @see com.android.volley.Request#getHeaders()
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }

        //Paybee.get().addSessionCookie(headers);

        return headers;
    }
}