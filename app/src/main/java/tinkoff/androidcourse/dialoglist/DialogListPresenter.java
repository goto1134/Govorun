package tinkoff.androidcourse.dialoglist;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

import tinkoff.androidcourse.model.db.DialogItem;

/**
 * Created by goto1134
 * on 24.05.2017.
 */

class DialogListPresenter extends MvpBasePresenter<DialogListView> {

    private List<DialogItem> dialogItems;

    private boolean isFirstTimeAttach = true;

    @Override
    public void attachView(DialogListView view) {
        super.attachView(view);
        if (dialogItems != null) {
            view.showDialogList(dialogItems);
            dialogItems = null;
        } else if (isFirstTimeAttach) {
            isFirstTimeAttach = false;
            refresh();
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

    public void onDialogCreated(DialogItem dialogItem) {
        getView().addDialog(dialogItem);
    }
}
