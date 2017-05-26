package tinkoff.androidcourse.dialog;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.raizlabs.android.dbflow.config.FlowManager;

import tinkoff.androidcourse.model.db.DialogItem;
import tinkoff.androidcourse.model.db.MessageItem;

/**
 * Created by goto1134
 * on 29.04.2017.
 */
class SendMessageLoader extends AsyncTaskLoader<MessageItem> {
    private final String messageText;
    private DialogItem dialogItem;
    private long userId;

    SendMessageLoader(Context context, String messageText, DialogItem dialogItem, long userId) {
        super(context);
        this.messageText = messageText;
        this.dialogItem = dialogItem;
        this.userId = userId;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public MessageItem loadInBackground() {
        MessageItem messageItem = new MessageItem(messageText, userId, dialogItem);
        FlowManager.getModelAdapter(MessageItem.class)
                   .save(messageItem);
        return messageItem;
    }
}
