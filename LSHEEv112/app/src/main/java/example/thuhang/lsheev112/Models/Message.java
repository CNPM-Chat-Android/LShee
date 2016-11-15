package example.thuhang.lsheev112.Models;

/**
 * Created by ThuHang on 10/24/2016.
 */
public class Message   {
    private int id;
    private String from, message, to;
    private boolean isMe;
    private String date;
    public Message() {
    }
    public Message(String from, String to,  String message, boolean isMe,String date) {
        this.from = from;
        this.message = message;
        this.isMe = isMe;
        this.to=to;
        this.date= date;
    }

    public int getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }
    public void setFrom(String fromName) {
        this.from = fromName;
    }
    public String getTo(){
        return to;
    }
    public void setTo(String to){
        this.to=to;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date=date;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isMe() {
        return isMe;
    }
    public void setIsMe(boolean isMe) {
        this.isMe = isMe;
    }
}
