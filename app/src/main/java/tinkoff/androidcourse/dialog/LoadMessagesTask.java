package tinkoff.androidcourse.dialog;

import android.os.AsyncTask;

import java.util.List;

import tinkoff.androidcourse.model.db.DialogItem;
import tinkoff.androidcourse.model.db.MessageItem;

/**
 * Created by goto1134
 * on 25.05.2017.
 */
class LoadMessagesTask extends AsyncTask<DialogItem, Void, List<MessageItem>> {

    private final DialogPresenter dialogPresenter;

    LoadMessagesTask(DialogPresenter dialogPresenter) {
        this.dialogPresenter = dialogPresenter;
    }

    @Override
    protected List<MessageItem> doInBackground(DialogItem... params) {
        if (params.length != 1) {
            throw new IllegalStateException("Number of parameters must be equal to 1");
        }
        DialogItem dialogItem = params[0];
        return dialogItem.getMessages();
    }

    @Override
    protected void onPostExecute(List<MessageItem> messageItems) {
        super.onPostExecute(messageItems);
        dialogPresenter.onMessagesLoaded(messageItems);
    }
}
