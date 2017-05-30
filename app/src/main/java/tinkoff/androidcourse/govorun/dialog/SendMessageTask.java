package tinkoff.androidcourse.govorun.dialog;

import android.os.AsyncTask;

import com.raizlabs.android.dbflow.config.FlowManager;

import tinkoff.androidcourse.govorun.model.db.MessageItem;


/**
 * Created by goto1134
 * on 29.04.2017.
 */
class SendMessageTask extends AsyncTask<MessageItem, Void, MessageItem> {

    private DialogPresenter dialogPresenter;

    SendMessageTask(DialogPresenter dialogPresenter) {

        this.dialogPresenter = dialogPresenter;
    }

    @Override
    protected MessageItem doInBackground(MessageItem... params) {
        if (params.length != 1) {
            throw new IllegalStateException("Number of parameters must be equal to 1");
        }
        MessageItem messageItem = params[0];
        FlowManager.getModelAdapter(MessageItem.class)
                   .save(messageItem);
        return messageItem;

    }

    @Override
    protected void onPostExecute(MessageItem messageItem) {
        super.onPostExecute(messageItem);
        dialogPresenter.onMessageSent(messageItem);
    }
}
