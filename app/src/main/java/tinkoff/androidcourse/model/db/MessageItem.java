package tinkoff.androidcourse.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Date;

/**
 * Created by goto1134
 * on 02.05.2017.
 */

@Table(database = FintechChatDatabase.class)
public class MessageItem {
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String text;

    @Column
    long authorId;

    @ForeignKey
    DialogItem dialogItem;

    public String getText() {
        return text;
    }

    public long getAuthorId() {
        return authorId;
    }

    public Date getDate() {
        return date;
    }

    @Column
    Date date;

    public MessageItem() {
    }

    public MessageItem(String text, long authorId, DialogItem dialogItem) {
        this.text = text;
        this.authorId = authorId;
        this.dialogItem = dialogItem;
        date = new Date();
    }
}
