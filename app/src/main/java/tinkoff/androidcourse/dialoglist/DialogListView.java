package tinkoff.androidcourse.dialoglist;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

import tinkoff.androidcourse.model.db.DialogItem;

/**
 * Created by goto1134
 * on 24.05.2017.
 */
interface DialogListView extends MvpView {

    void showLoadInProgress();

    void showDialogList(List<DialogItem> dialogItemList);
}
