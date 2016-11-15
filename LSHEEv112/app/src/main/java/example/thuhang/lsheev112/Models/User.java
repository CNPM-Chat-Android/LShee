package example.thuhang.lsheev112.Models;

import android.support.v4.app.FragmentActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ThuHang on 11/6/2016.
 */
public class User implements Serializable {
    private  int id;
    private String username;
    private String pass;
    private String phone;
    private String email;
    private boolean gender;
    private int avatar;
    private boolean isOnline;
    public User(FragmentActivity activity, int item, ArrayList<User> listData) {
    }

    public User(int id, String pass, String username, String phone, String email, boolean gender, int avatar, boolean isOnline) {
        this.id = id;
        this.pass = pass;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.avatar= avatar;
        this.isOnline = isOnline;
    }
    public static final List<User> ListUser = new ArrayList<User>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, User> USER_MAP = new HashMap<String, User>();

    private static void addItem(User item) {
        ListUser.add(item);
        USER_MAP.put(item.id+"", item);
    }

    public boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean online) {
        isOnline = online;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return this.getUsername();
    }
}
