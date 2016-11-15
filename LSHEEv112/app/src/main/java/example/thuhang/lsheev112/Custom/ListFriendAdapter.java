package example.thuhang.lsheev112.Custom;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import example.thuhang.lsheev112.Models.User;
import example.thuhang.lsheev112.R;

/**
 * Created by ThuHang on 11/6/2016.
 */
public class ListFriendAdapter extends ArrayAdapter<User> {

    Activity context=null;
    List<User> myArray=null;
    // mang tam
    ArrayList<User> arrayList;
    int layoutId;
    /**
     * Constructor này dùng để khởi tạo các giá trị
     * từ MainActivity truyền vào
     * @param context : là Activity từ Main
     * @param layoutId: Là layout custom do ta tạo
     * @param arr : Danh sách nhân viên truyền từ Main
     */
    public ListFriendAdapter(Activity context, int layoutId, ArrayList<User> arr){
        super(context, layoutId, arr);
        this.context=context;
        this.layoutId=layoutId;
        this.myArray=arr;
        arrayList = new ArrayList<>();
        arrayList.addAll(myArray);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.friendline, null);
        }
        User u = getItem(position);
        if (u != null) {
            // Anh xa + Gan gia tri
            TextView txt = (TextView) view.findViewById(R.id.txtUSERNAME);
            ImageView imgAVT=(ImageView)view.findViewById(R.id.imgAvatar);
            //imgitem.setImageResource(R.drawable.bg_login);
            u.setUsername(txt.getText().toString());
            imgAVT.setImageResource(u.getAvatar());
        }
        return view;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        myArray.clear();
        if (charText.length() == 0) {
            myArray.addAll(arrayList);

        } else {
            for (User user : arrayList) {
                if (charText.length() != 0 && user.getUsername().toLowerCase(Locale.getDefault()).contains(charText)) {
                    myArray.add(user);
                } /*else if (charText.length() != 0 && user.getUsername().toLowerCase(Locale.getDefault()).contains(charText)) {
                    myArray.add(user);
                }*/
            }
        }
        notifyDataSetChanged();
    }
}