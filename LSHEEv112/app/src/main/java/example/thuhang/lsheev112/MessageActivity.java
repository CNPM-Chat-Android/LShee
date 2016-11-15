package example.thuhang.lsheev112;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import example.thuhang.lsheev112.Models.Message;

import static example.thuhang.lsheev112.Utils.Utils.URL_SOCKET;


public class MessageActivity extends AppCompatActivity {
    private Message[] lstMessages;
    private ListView listViewChat=null;
    private Button btnSend;
    private Context context;
    private EditText edtMess, edtID;
    private ArrayList<String> lst;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(URL_SOCKET);
        } catch (URISyntaxException e) {

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        lst = new ArrayList<String>();
        connectView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //Your toolbar is now an action bar and you can use it like you always do, for example:
        if( actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doActionSend();
            }
        });
        mSocket.connect();
        // listener for server
        mSocket.on("server-gui-tinchat", onNewMessage_chat);

    }
    // server dui ve ket qua
    private Emitter.Listener onNewMessage_chat = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String noidung;

                    try {
                        noidung = data.getString("noidung");
                        Toast.makeText(context, noidung,Toast.LENGTH_SHORT).show();
                        lst.add(noidung);
                        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, lst);
                        listViewChat.setAdapter(arrayAdapter);

                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };
    private void connectView()
    {
        context=this;
        listViewChat=(ListView)findViewById(R.id.ListViewChat);
        btnSend=(Button)findViewById(R.id.ButtonSEND);
        edtMess=(EditText)findViewById(R.id.editMESS);
    }
    private void doActionSend(){
        mSocket.emit("client-gui-tinchat",edtMess.getText().toString(),"un");
        mSocket.emit("client-gui-tin-nhan", "room1", edtMess.getText().toString());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
