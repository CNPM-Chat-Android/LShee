package example.thuhang.lsheev112.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import example.thuhang.lsheev112.Models.User;

/**
 * Created by ThuHang on 11/7/2016.
 */
public class Utils {
    private Context context;
    private SharedPreferences sharedPref;
    private static final String KEY_SHARED_PREF = "ANDROID_CHAT";
    private static final int KEY_MODE_PRIVATE = 0;
    private static final String KEY_SESSION_ID = "sessionId",FLAG_MESSAGE = "message";
    public static String USER_NAME, ID, ROOM="room1", RECEIVER;
    public static void showTextShort(Context cxt, String txt)
    {
        Toast.makeText(cxt, txt, Toast.LENGTH_SHORT).show();
    }
    public static final String URL_SOCKET = "http://192.168.43.246:3000";


    public Utils(Context context) {
        this.context = context;
        sharedPref = this.context.getSharedPreferences(KEY_SHARED_PREF,
                KEY_MODE_PRIVATE);
    }
    public void storeSessionId(String sessionId) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_SESSION_ID, sessionId);
        editor.apply();
    }
    public String getSessionId() {
        return sharedPref.getString(KEY_SESSION_ID, null);
    }
    public JSONObject  getMessageJSON(String to,String from,String message, Date date) {
        JSONObject jObj = null;
        try {
            jObj = new JSONObject();
            jObj.put("from", from);
            jObj.put("to", to);
            jObj.put("message", message);
            jObj.put("date", date);
           // json = jObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }
    public static JSONObject  getUserJSON(String username, String email, String phone, String password) {
        JSONObject jObj = null;
        try {
            jObj = new JSONObject();
            jObj.put("username", username);
            jObj.put("email", email);
            jObj.put("phone", phone);
            jObj.put("password", password);
            // json = jObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }
    public static User getUser(JSONObject js) {
        int id;
        String username, email, phone, password;
        User u = null;
        try {
            username = js.getString("username");
            email = js.getString("email");
            phone = js.getString("phone");
            password = js.getString("password");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }
    public static JSONObject  getSendMsgJSON(String room, String username,String to, String message, String date) {
        JSONObject jObj = null;
        try {
            jObj = new JSONObject();
            jObj.put("room", room);
            jObj.put("from", username);
            jObj.put("to", to);
            jObj.put("message", message);
            jObj.put("date", date );
            // json = jObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }
}
