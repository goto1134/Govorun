package tinkoff.androidcourse;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import tinkoff.androidcourse.model.db.DialogItem;
import tinkoff.androidcourse.model.db.MessageItem;

/**
 * Created by goto1134
 * on 11.04.2017.
 */
public class MessageLoader extends AsyncTaskLoader<List<MessageItem>> {
    private DialogItem dialogItem;

    public MessageLoader(Context context, DialogItem dialogItem) {
        super(context);
        this.dialogItem = dialogItem;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<MessageItem> loadInBackground() {
        return dialogItem.getMessages();
    }
}
