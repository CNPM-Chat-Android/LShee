package example.thuhang.lsheev112.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.util.ArrayList;

import example.thuhang.lsheev112.ChatActivity;
import example.thuhang.lsheev112.Custom.UserAdapter;
import example.thuhang.lsheev112.Models.User;
import example.thuhang.lsheev112.R;

import static example.thuhang.lsheev112.Utils.Utils.RECEIVER;
import static example.thuhang.lsheev112.Utils.Utils.ROOM;
import static example.thuhang.lsheev112.Utils.Utils.URL_SOCKET;
import static example.thuhang.lsheev112.Utils.Utils.showTextShort;

public class ListFrFragment extends Fragment  {
    UserAdapter adapter;
    private ArrayList<User> listData;
    private ArrayList<Integer> toDelete;
    private ListView lvDemo;
    private Socket mSocket;
    private User u;
    {
        try {
            mSocket = IO.socket(URL_SOCKET);
        } catch (URISyntaxException e) {

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_fr, container, false);
        listData=new ArrayList<User>();
        lvDemo = (ListView)v.findViewById(R.id.lvFriend);
        User ttt = new User(1,"123","Thot not","018121332","dedf@gmail.com",true, R.id.imgAvatar,true);
        User ttt1 = new User(1,"123","Thot not","018121332","dedf@gmail.com",true, R.id.imgAvatar,true);
        User ttt2 = new User(1,"123","Thot not","018121332","dedf@gmail.com",true, R.id.imgAvatar,true);
        User ttt3 = new User(1,"123","Thot not","018121332","dedf@gmail.com",true, R.id.imgAvatar,true);
        listData.add(ttt);
        listData.add(ttt1);
        listData.add(ttt2);
        listData.add(ttt3);
        adapter=new UserAdapter(getActivity(),R.layout.item,listData);
        lvDemo.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        lvDemo.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    u = adapter.getItem(position);
                    RECEIVER = u.getUsername();
                    //ROOM = ID+"-"+u.getId();
                    mSocket.emit("join_room", ROOM);
                    nextIntent(getActivity(), ChatActivity.class);
            }
        });
        lvDemo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                u = (User) lvDemo.getAdapter().getItem(position);
                lvDemo.setItemChecked(position, true);
                return false;
            }
        });
        registerForContextMenu(lvDemo);
       // mSocket.connect();
        //mSocket.on("server-gui-friend", onNewMessage_friend);
        return v;
    }
/*    private Emitter.Listener onNewMessage_friend = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    JSONArray noidung;
                    boolean isMe = false;
                    JSONObject js;
                    showTextShort(getActivity(),"sdsf");
                    String id, un, pnone, email;
                    try {
                        noidung = data.getJSONArray("noidung");
                        String friend;
                        for (int i=0; i< noidung.length();i++ ) {
                            js =(JSONObject) noidung.get(i);
                            id = js.toString();
                            showTextShort(getActivity(),id+"sdsf");

                        }
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Message");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Unfriend");
        menu.add(0, v.getId(), 0, "Block");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Message"){

        }
        else if(item.getTitle()=="Unfriend"){
            showTextShort(getActivity(),u.getId()+"");
        }
        if(item.getTitle()=="Message"){

        }
        else if(item.getTitle()=="Block"){
            showTextShort(getActivity(),u.getUsername());
        }
        else{
            return false;
        }
        return true;
    }

    private void nextIntent(Context context, Class cl){
        Intent i=new Intent(context, cl);
        startActivity(i);
    }
}

