package tinkoff.androidcourse.dialog;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

import tinkoff.androidcourse.model.db.DialogItem;
import tinkoff.androidcourse.model.db.MessageItem;

/**
 * Created by goto1134
 * on 25.05.2017.
 */

class DialogPresenter extends MvpBasePresenter<DialogView> {

    private final DialogItem dialogItem;
    private List<MessageItem> messageItems;
    private boolean isFirstTimeAttach = true;

    DialogPresenter(DialogItem dialogItem) {
        this.dialogItem = dialogItem;
    }

    @Override
    public void attachView(DialogView view) {
        super.attachView(view);
        if (messageItems != null) {
            getView().setMessageItems(messageItems);
            messageItems = null;
        } else if (isFirstTimeAttach) {
            isFirstTimeAttach = false;
            refresh();
        }
    }

    void onMessagesLoaded(List<MessageItem> messageItems) {
        if (isViewAttached()) {
            getView().setMessageItems(messageItems);
        } else {
            this.messageItems = messageItems;
        }
    }


    void refresh() {
        getView().showLoadInProgress();
        new LoadMessagesTask(this).execute(dialogItem);
    }
}
