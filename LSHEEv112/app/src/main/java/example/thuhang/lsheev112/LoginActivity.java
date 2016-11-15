package example.thuhang.lsheev112;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import example.thuhang.lsheev112.Utils.Utils;

import static example.thuhang.lsheev112.Utils.Utils.ID;
import static example.thuhang.lsheev112.Utils.Utils.URL_SOCKET;
import static example.thuhang.lsheev112.Utils.Utils.USER_NAME;
import static example.thuhang.lsheev112.Utils.Utils.showTextShort;

public class LoginActivity extends AppCompatActivity {
    Button btnlogin, btnsignup;
    EditText edituser,editpassword;
    CheckBox chksaveaccount;
    RelativeLayout backgroung;
    Context context;
    Toolbar toolbar;
    //đặt tên cho tập tin lưu trạng thái
    String prefname="my_data";
    private Socket mSocket;
    private Utils utils;
    {
        try {
            mSocket = IO.socket(URL_SOCKET);
        } catch (URISyntaxException e) {

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        connecView();
        context=this;
        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();

        // lang nghe tu server
        //mSocket.on("Ketquadangnhap", onNewMessage_login);
        btnlogin.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View arg0) {
                        doLogin();
                    }
                });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextIntent(context,SignUpActivity.class);
            }
        });
        mSocket.connect();
        // listener for server
        mSocket.on("Ketquadangnhap", onNewMessage_login);
       
    }
    private void connecView()
    {
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnsignup = (Button)findViewById(R.id.ButtonSIGNUP);
        btnlogin = (Button)findViewById(R.id.buttonLOGIN);
        edituser = (EditText)findViewById(R.id.editTextUSERNAME);
        editpassword = (EditText)findViewById(R.id.editTextPASSWORD);
        chksaveaccount = (CheckBox)findViewById(R.id.checkBox);
        backgroung = (RelativeLayout) findViewById(R.id.background);
    }
    private void setBackground(int id)
    {
       /* final int sdk = Build.VERSION.SDK_INT;
        if(sdk < Build.VERSION_CODES.JELLY_BEAN) {
                backgroung.setBackgroundDrawable( getResources().getDrawable(id) );
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                backgroung.setBackground( getResources().getDrawable(id));
            }
        }*/
    }
    /**
     * Method login system
     */
    public void doLogin() {
        //nextIntent(context,MainActivity.class);
        if(edituser.getText().toString().equals(""))
        {
            edituser.setError("Please type your username!");
            edituser.requestFocus();
        }else
        if(editpassword.getText().toString().equals(""))
        {
            editpassword.setError("Please type your password!");
            editpassword.requestFocus();
    }else
        if(editpassword.getText().toString().length()<8)
        {
            editpassword.setError("Your password must have more than 8 characters!");
            editpassword.requestFocus();
        }
        else {
            // send pass and username to server
            mSocket.emit("client-gui-dang-nhap", edituser.getText().toString(),editpassword.getText().toString() );
            //truyền dữ liệu qua màn hình mới
            //i.putExtra("user", edituser.getText().toString());
        }
    }
    // server dui ve ket qua dang nhap
    private Emitter.Listener onNewMessage_login = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data1 = new JSONObject();
                    data1= (JSONObject) args[0];
                    JSONObject data2 = new JSONObject();
                    data2 = (JSONObject) args[1];
                    String noidung;
                    showTextShort( context, "Login! "+ID);
                    try {
                        noidung = data1.getString("noidung");
                        if(noidung.equals("true")) {
                            USER_NAME=edituser.getText().toString();
                            ID = data2.getString("id");
                            showTextShort( context, "Login successful! "+ID);
                            finish();//đóng màn hình hiện tại
                            nextIntent(context, MainActivity.class);
                        }
                        else
                            showTextShort(context,"Username or password is incorrect!");
                    } catch (JSONException e) {
                        showTextShort( context, "Login! "+e.getMessage());
                    }
                }
            });
        }
    };
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //gọi hàm lưu trạng thái ở đây
        savingPreferences();
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //gọi hàm đọc trạng thái ở đây
        restoringPreferences();
    }
    /**
     * hàm lưu trạng thái
     */
    public void savingPreferences()
    {
        //tạo đối tượng getSharedPreferences
        SharedPreferences pre=getSharedPreferences(prefname, MODE_PRIVATE);
        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        String user=edituser.getText().toString();
        String pwd=editpassword.getText().toString();
        boolean bchk=chksaveaccount.isChecked();
        if(!bchk)
        {
            //xóa mọi lưu trữ trước đó
            editor.clear();
        }
        else
        {
            //lưu vào editor
            editor.putString("user", user);
            editor.putString("pwd", pwd);
            editor.putBoolean("checked", bchk);
        }
        //chấp nhận lưu xuống file
        editor.commit();
    }
    /**
     * hàm đọc trạng thái đã lưu trước đó
     */
    public void restoringPreferences()
    {
        SharedPreferences pre = getSharedPreferences(prefname,MODE_PRIVATE);
        //lấy giá trị checked ra, nếu không thấy thì giá trị mặc định là false
        boolean bchk=pre.getBoolean("checked", false);
        if(bchk)
        {
            //lấy user, pwd, nếu không thấy giá trị mặc định là rỗng
            String user=pre.getString("user", "");
            String pwd=pre.getString("pwd", "");
            edituser.setText(user);
            editpassword.setText(pwd);
            // co luu r, chuyen sang man hinh chinh luon
           // nextIntent(context, MainActivity.class);
        }
        chksaveaccount.setChecked(bchk);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private void nextIntent(Context context, Class cl){
        Intent i=new Intent(context, cl);
        startActivity(i);
    }
}