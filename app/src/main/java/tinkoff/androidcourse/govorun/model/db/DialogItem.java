package tinkoff.androidcourse.govorun.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Table(database = FintechChatDatabase.class)
public class DialogItem implements Serializable {

    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String title;

    @Column
    String desc;

    @Column
    Date creationDate;

    @Column
    String lastMessage;

    public DialogItem() {
    }

    public DialogItem(String title, String desc) {
        this.title = title;
        this.desc = desc;
        creationDate = new Date();
    }

    public List<MessageItem> getMessages() {
        return SQLite.select()
                     .from(MessageItem.class)
                     .where(MessageItem_Table.dialogItem_id.eq(id))
                     .orderBy(MessageItem_Table.date, false)
                     .queryList();
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}