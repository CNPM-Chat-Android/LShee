/*
package helloworld.dhspkt.tintruong.listv540;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import example.thuhang.lsheev112.Models.User;
import example.thuhang.lsheev112.R;


public class ListFriendActivity extends AppCompatActivity {
    public static final String TAG = "ListViewExample";
    User adapter;
    private ArrayList<User> listData;
    private ListView lvDemo;
    String itemId;
    Student s=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_friend);
        listData=new ArrayList<>();

        lvDemo = (ListView)findViewById(R.id.listView);
        lvDemo.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        Student ttt = new Student(0,"Tín","0123456789",true);
        Student ttt1 = new Student(1,"Đen","0123456789",true);
        Student ttt2 = new Student(2,"Xì","0123456789",true);
        listData.add(ttt);
        listData.add(ttt1);
        listData.add(ttt2);
        adapter=new StudentAdapter(this,R.layout.item,listData);
        lvDemo.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // CHOICE_MODE_NONE: Không cho phép lựa chọn (Mặc định).
        // ( listView.setItemChecked(..) không làm việc với CHOICE_MODE_NONE).
        // CHOICE_MODE_SINGLE: Cho phép một lựa chọn.
        // CHOICE_MODE_MULTIPLE: Cho phép nhiều lựa chọn.
        // CHOICE_MODE_MULTIPLE_MODAL: Cho phép nhiều lựa chọn trên Modal Selection Mode.


        lvDemo.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"ABC thot",Toast.LENGTH_SHORT).show();
                }
        });
      lvDemo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
              s = (Student) lvDemo.getAdapter().getItem(position);
              itemId =  position+"";
              lvDemo.setItemChecked(position, true);
              return false;
          }
      });
        registerForContextMenu(lvDemo);


        // android.R.layout.simple_list_item_checked: Là một hằng số Layout định nghĩa sẵn của Android
        // ý nghĩa của nó là ListView với ListItem đơn giản (Duy nhất một CheckedTextView).
//        Student[] users = new Student[]{ttt,ttt1, ttt2};
//        ArrayAdapter<Student> arrayAdapters
//                = new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_checked,users );
//
//        //là sao
//       // lvDemo.setAdapter(ArrayAdapter);
//
//        for(int i=0;i< users.length; i++ )  {
//            lvDemo.setItemChecked(i,users[i].isActive());
//        }
    }
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
        if(item.getTitle()==" "){
            Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
        }
        else if(item.getTitle()=="Message"){
           Toast.makeText(getApplicationContext(),itemId+" " + s.getName() ,Toast.LENGTH_LONG).show();
        }else{
            return false;
        }
        return true;
    }
    // Khi người dùng nhấn vào nút "Print Selected Items".
    public void printSelectedItems(View view)  {

        SparseBooleanArray sp = lvDemo.getCheckedItemPositions();

        StringBuilder sb= new StringBuilder();

        for(int i=0;i<sp.size();i++){
            if(sp.valueAt(i)==true){
                Student user= (Student) lvDemo.getItemAtPosition(i);
                // Or:
                // String s = ((CheckedTextView) listView.getChildAt(i)).getText().toString();
                String s= user.getName();
                sb = sb.append(" "+s);
            }
        }
        Toast.makeText(this, "Selected items are: "+sb.toString(), Toast.LENGTH_LONG).show();

    }
}*/
