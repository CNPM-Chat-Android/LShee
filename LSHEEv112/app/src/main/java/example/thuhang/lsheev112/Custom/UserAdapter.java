package example.thuhang.lsheev112.Custom;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import example.thuhang.lsheev112.Models.User;
import example.thuhang.lsheev112.R;

public class UserAdapter extends ArrayAdapter<User> {
    ArrayList<User> listData=null;
    LayoutInflater inflater;
    int Layout;
    Activity Context =null;

    public Activity getContext() {
        return Context;
    }

    // Hàm tạo của custom
    public UserAdapter(Activity context,int layout, ArrayList<User> listData) {
        super(context, layout,listData);
        this.Context=context;
        this.Layout=layout;
        this.listData = listData;
    }

    // Trả về số lượng phần tử được hiển thị trong listview
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public User getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        User item = listData.get(position);
        TextView txtName, txtState;
         if(v==null)
         {
              inflater = LayoutInflater.from(getContext());
              v = inflater.inflate(R.layout.item,null);
         }
        txtName = (TextView) v.findViewById(R.id.txtName);
        txtState = (TextView) v.findViewById(R.id.txtPhone);
        txtName.setText(item.getUsername());
        if(item.getIsOnline())
        txtState.setText("Online");
        else txtState.setText("Offline");
        return v;
    }
}