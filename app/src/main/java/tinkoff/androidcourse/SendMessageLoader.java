package tinkoff.androidcourse;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.Date;

/**
 * Created by goto1134
 * on 29.04.2017.
 */
public class SendMessageLoader extends AsyncTaskLoader<MessageItem> {
    private final String messageText;

    public SendMessageLoader(Context context, String messageText) {
        super(context);
        this.messageText = messageText;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public MessageItem loadInBackground() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new MessageItem(messageText, new Date(), R.drawable.test_avatar);
    }
}
