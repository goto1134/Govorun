package tinkoff.androidcourse;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by goto1134
 * on 11.04.2017.
 */
public class MessageLoader extends AsyncTaskLoader<List<MessageItem>> {
    public MessageLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<MessageItem> loadInBackground() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return createDataset();
    }

    private List<MessageItem> createDataset() {
        List<MessageItem> list = new ArrayList<>();
        list.add(new MessageItem("text", new Date(), R.drawable.test_avatar));
        list.add(new MessageItem("text", new Date(), R.drawable.test_avatar));
        list.add(new MessageItem("text", new Date(), R.drawable.test_avatar));
        list.add(new MessageItem("text", new Date(), R.drawable.test_avatar));
        list.add(new MessageItem("text", new Date(), R.drawable.test_avatar));
        list.add(new MessageItem("text", new Date(), R.drawable.test_avatar));
        list.add(new MessageItem("text", new Date(), R.drawable.test_avatar));
        list.add(new MessageItem("text", new Date(), R.drawable.test_avatar));
        return list;
    }
}
