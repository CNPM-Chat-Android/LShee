package example.thuhang.lsheev112.Utils;

import android.app.Application;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by ThuHang on 11/15/2016.
 */
public class MyApplication extends Application {
    public static void eventSocket(Socket mSocket) {
        try {
            mSocket = IO.socket("http://192.168.43.235:3000");
        } catch (URISyntaxException e) {
            System.out.print(e.toString());
        }
    }
}
