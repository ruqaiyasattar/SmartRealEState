package ha.ecz.com.subscriberpanel.RESTService;

import org.json.JSONException;
import org.json.JSONObject;

public interface VolleyCallback {
    void onSuccess(JSONObject result) throws JSONException;
    void onError(String result) throws Exception;
}
