package cartentia.com.common.manager;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Abhijeet.Bhosale on 8/28/2015.
 */
public class VolleyManager {

    private static VolleyManager INSATANCE;
    private RequestQueue requestQueue;
    private Context context;

    public static synchronized VolleyManager getInstance() {
        if (INSATANCE != null)
            return INSATANCE;
        return INSATANCE = new VolleyManager();

    }

    public void initRequestQueue(final Context context) {
        requestQueue = Volley.newRequestQueue(context);
        this.context = context;
    }

    public <T> void addRequestToQueue(Request<T> request) {
        requestQueue.add(request);
    }


}
