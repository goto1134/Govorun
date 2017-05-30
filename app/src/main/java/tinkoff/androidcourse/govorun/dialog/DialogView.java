package tinkoff.androidcourse.govorun.dialog;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

import tinkoff.androidcourse.govorun.model.db.MessageItem;

/**
 * Created by goto1134
 * on 25.05.2017.
 */

interface DialogView extends MvpView {
    void setMessageItems(List<MessageItem> messageItems);

    void showLoadInProgress();

    void addMessage(MessageItem messageItem);

}
