package tinkoff.androidcourse.govorun.model.db;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;
import com.raizlabs.android.dbflow.sql.migration.BaseMigration;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

/**
 * @author Sergey Boishtyan
 */
@Database(name = "chat_db", version = 2)
public final class FintechChatDatabase {


    @Migration(version = 2, priority = 2, database = FintechChatDatabase.class)
    public static class CreateColumnLastMSG extends AlterTableMigration<DialogItem> {

        public CreateColumnLastMSG(Class<DialogItem> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.TEXT, "lastMessage");
        }
    }

    @Migration(version = 2, priority = 1, database = FintechChatDatabase.class)
    public static class UpdateLastMessageText extends BaseMigration {

        @Override
        public void migrate(DatabaseWrapper database) {
            // run some code here
            Where<MessageItem> maxDateForDialogId = SQLite.select(
                    Method.max(MessageItem_Table.date))
                                                          .from(MessageItem.class)
                                                          .where(MessageItem_Table.dialogItem_id.eq(
                                                                  DialogItem_Table.id));
            Where<MessageItem> textOfLastMessage = SQLite.select(MessageItem_Table.text)
                                                         .from(MessageItem.class)
                                                         .where(MessageItem_Table.dialogItem_id.eq(
                                                                 DialogItem_Table.id))
                                                         .and(MessageItem_Table.date.eq(
                                                                 maxDateForDialogId));
            SQLite.update(DialogItem.class)
                  .set(DialogItem_Table.lastMessage.eq(textOfLastMessage))
                  .execute(database);
        }
    }
}
