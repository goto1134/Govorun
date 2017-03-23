package tinkoff.androidcourse;

import java.util.Date;

/**
 * Created by goto1134
 * on 23.03.2017.
 */

public class MessageItem {
    private String text;
    private Date date;
    private int avatarID;

    public MessageItem(String text, Date date, int avatarID) {
        this.text = text;
        this.date = date;
        this.avatarID = avatarID;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public int getAvatarID() {
        return avatarID;
    }
}
