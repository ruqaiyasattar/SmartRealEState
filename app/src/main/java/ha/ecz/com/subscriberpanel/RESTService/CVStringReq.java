package ha.ecz.com.subscriberpanel.RESTService;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class CVStringReq extends Request<String> {

    private Response.Listener<String> listener;
    private Map<String, String> params;
    Priority mPriority;

    public CVStringReq(int method, String url, Map<String, String> params,
                           Response.Listener responseListener, Response.ErrorListener errorListener) {
        super(method, ("http://rehajomobileapi.hundredalpha.com/api/" + url), errorListener);
        this.listener = responseListener;
        this.params = params;
    }

    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return params;
    };

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(jsonString,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (Exception je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }

}
