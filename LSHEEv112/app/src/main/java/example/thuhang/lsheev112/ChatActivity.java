package example.thuhang.lsheev112;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import example.thuhang.lsheev112.Custom.MessagesListAdapter;
import example.thuhang.lsheev112.Models.Message;
import example.thuhang.lsheev112.SQlite.Database;

import static example.thuhang.lsheev112.Utils.Utils.RECEIVER;
import static example.thuhang.lsheev112.Utils.Utils.ROOM;
import static example.thuhang.lsheev112.Utils.Utils.URL_SOCKET;
import static example.thuhang.lsheev112.Utils.Utils.USER_NAME;
import static example.thuhang.lsheev112.Utils.Utils.getSendMsgJSON;

public class ChatActivity extends AppCompatActivity {

    private Button btnSend;
    private EditText inputMsg;
    // Chat messages list adapter
    private MessagesListAdapter adapter;
    private List<Message> listMessages;
    private ListView listViewMessages;
    private  boolean isMe = false;
    private Context context;
    private Socket mSocket;
    private Database db;
    {
        try {
            mSocket = IO.socket(URL_SOCKET);
        } catch (URISyntaxException e) {

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        connectView();
        context = getApplicationContext();
        db = new Database(context,"lshee1.sqlite",null,1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //Your toolbar is now an action bar and you can use it like you always do, for example:
        if( actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        listMessages = new ArrayList<Message>();
        adapter = new MessagesListAdapter(context, listMessages);
        listViewMessages.setAdapter(adapter);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doActionSend();
                inputMsg.setText("");
            }
        });
        mSocket.connect();
        // listener for server
        mSocket.on("server-gui-tin-nhan", onNewMessage_chat);
    }
    private void connectView(){
        btnSend = (Button) findViewById(R.id.btnSend);
        inputMsg = (EditText) findViewById(R.id.inputMsg);
        listViewMessages = (ListView) findViewById(R.id.list_view_messages);
    }
    private Emitter.Listener onNewMessage_chat = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    JSONArray noidung;
                    //showTextShort( context,"efefeff");
                    boolean isMe = false;
                    try {
                        noidung = data.getJSONArray("noidung");
                        String from, to, message, date;
                        from = noidung.get(0).toString();
                        to =  noidung.get(1).toString();
                        message =  noidung.get(2).toString();
                        date =  noidung.get(3).toString();
                        if(from.equals(USER_NAME))
                            isMe=true;
                        Message m = new Message(from,to,message,isMe,date);
                        appendMessage(m);

                        if(!isMe)  playBeep();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private void doActionSend(){
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("hh:mm");
        isMe = true;
        if(!inputMsg.getText().toString().equals("")) {
            JSONObject js = getSendMsgJSON(ROOM,USER_NAME,RECEIVER,inputMsg.getText().toString(),f.format(date));
            mSocket.emit("client-gui-tin-nhan", js);
        }
    }
    public void playBeep() {
        try {
            Uri notification = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
                    notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void appendMessage(final Message m) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listMessages.add(m);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
