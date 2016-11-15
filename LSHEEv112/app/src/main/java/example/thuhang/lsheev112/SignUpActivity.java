package example.thuhang.lsheev112;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static example.thuhang.lsheev112.Utils.Utils.URL_SOCKET;
import static example.thuhang.lsheev112.Utils.Utils.getUserJSON;
import static example.thuhang.lsheev112.Utils.Utils.showTextShort;

public class SignUpActivity extends AppCompatActivity {

    Button btnSignUp, btnLogin;
    Toolbar toolbar;
    RelativeLayout backgroung;
    EditText edituser,editpassword, editphone, editmail, editcomfirm_password;
    Context context;
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
        setContentView(R.layout.activity_sign_up);
        connectView();
        if(getSupportActionBar()!=null)
        getSupportActionBar().hide();
        context=this;


      btnLogin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              nextIntent(SignUpActivity.this,LoginActivity.class);
          }
      });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignUp();
            }
        });

        mSocket.connect();
        // listener for server
        mSocket.on("Ketquadangky", onNewMessage_signup);
    }
    // server dui ve ket qua dang ky
    private Emitter.Listener onNewMessage_signup = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String noidung;
                    try {
                        noidung = data.getString("noidung");
                        //Toast.makeText(getApplicationContext(),  noidung,Toast.LENGTH_SHORT ).show();
                        if(noidung.equals("true")) {
                            showTextShort(context, "Sign up successful!");
                            finish();//đóng màn hình hiện tại
                            nextIntent(getApplicationContext(), LoginActivity.class);
                        }
                        else {
                            edituser.requestFocus();
                            edituser.setError("Username is Exists! Please type again!");
                        }
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };
    private void doSignUp() {

        if(edituser.getText().toString().equals(""))
        {
            edituser.requestFocus();
            edituser.setError("Please type your username!");
        }
        else
        if(!isValidEmail(editmail.getText().toString()))
        {
            editmail.requestFocus();
            editmail.setError("Please type your email!");
        }else
        if(!isValidMatcherPhone(editphone.getText().toString()))
        {
            editphone.requestFocus();
            editphone.setError("Your phone number must have more than 10 numbers.");
        }else
        if(!isValidMatcherPass(editpassword.getText().toString()))
        {
            editpassword.requestFocus();
            editpassword.setError("Your password must have more than 8 characters.");
        }else
        if(editcomfirm_password.getText().toString().equals(""))
        {
            editcomfirm_password.requestFocus();
            editcomfirm_password.setError("Please type your confirm password!");
        }
        else
        if(!editpassword.getText().toString().equals(editcomfirm_password.getText().toString()))
        {
            editcomfirm_password.requestFocus();
            editcomfirm_password.clearComposingText();
            editcomfirm_password.setError("The password confirmation must match your password");
        }
        else
        {
            JSONObject jsObj = getUserJSON(edituser.getText().toString()
                    , editmail.getText().toString(), editphone.getText().toString(),editpassword.getText().toString());
            // send pass and username to server
            mSocket.emit("client-gui-dang-ky",jsObj );
        }
    }
    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{3,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    // validating phone id
    private boolean isValidMatcherPhone(String phone) {
        String PHONE_PATTERN = "^[_0-9]{10,}";

        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    // validating phone id
    private boolean isValidMatcherPass(String pass) {
        String PASS_PATTERN = "^[_A-Za-z0-9]{8,}";

        Pattern pattern = Pattern.compile(PASS_PATTERN);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }
    private void connectView()
    {
        backgroung = (RelativeLayout)findViewById(R.id.background);
        editcomfirm_password = (EditText)findViewById(R.id.editTextCONFIRMPASSWORD);
        editmail=(EditText)findViewById(R.id.editEMAIL);
        btnSignUp=(Button)findViewById(R.id.buttonSIGNUP);
        btnLogin=(Button)findViewById(R.id.ButtonLOGIN);
       // toolbar = (Toolbar)findViewById(R.id.toolbar);
        editphone=(EditText)findViewById(R.id.editPHONE);
        editpassword=(EditText)findViewById(R.id.editTextPASSWORD);
        edituser=(EditText)findViewById(R.id.editTextUSERNAME);

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
    private void nextIntent(Context context, Class cl){
        Intent i=new Intent(context, cl);
        startActivity(i);
    }
}
