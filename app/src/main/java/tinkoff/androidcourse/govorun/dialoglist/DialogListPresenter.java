package tinkoff.androidcourse.govorun.dialoglist;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

import tinkoff.androidcourse.govorun.model.db.DialogItem;


/**
 * Created by goto1134
 * on 24.05.2017.
 */

class DialogListPresenter extends MvpBasePresenter<DialogListView> {

    private List<DialogItem> dialogItems;

    private boolean isFirstTimeAttach = true;
    private DialogItem newDialog;

    @Override
    public void attachView(DialogListView view) {
        super.attachView(view);
        if (isFirstTimeAttach) {
            isFirstTimeAttach = false;
            refresh();
        } else if (newDialog != null) {
            getView().addDialog(newDialog);
            newDialog = null;
        } else if (dialogItems != null) {
            view.showDialogList(dialogItems);
            dialogItems = null;
        }
    }


    void refresh() {
        getView().showLoadInProgress();
        new LoadDialogListTask(this).execute();
    }

    void onDialogItemsLoaded(List<DialogItem> dialogItems) {
        if (isViewAttached()) {
            getView().showDialogList(dialogItems);
        } else {
            this.dialogItems = dialogItems;
        }
    }

    void createDialog(String title, String description) {
        DialogItem dialogItem = new DialogItem(title, description);
        new CreateDialogTask(this).execute(dialogItem);
    }

    void onDialogCreated(DialogItem dialogItem) {
        if (isViewAttached()) {
            getView().addDialog(dialogItem);
        } else {
            newDialog = dialogItem;
        }
    }
}
