package tinkoff.androidcourse.govorun.dialoglist;

import android.os.AsyncTask;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import tinkoff.androidcourse.govorun.model.db.DialogItem;


/**
 * Created by goto1134
 * on 25.05.2017.
 */

class LoadDialogListTask extends AsyncTask<Void, Void, List<DialogItem>> {

    private final DialogListPresenter dialogListPresenter;

    LoadDialogListTask(DialogListPresenter dialogListPresenter) {
        this.dialogListPresenter = dialogListPresenter;
    }

    @Override
    protected List<DialogItem> doInBackground(Void... params) {
        return SQLite.select()
                     .from(DialogItem.class)
                     .queryList();
    }

    @Override
    protected void onPostExecute(List<DialogItem> dialogItems) {
        super.onPostExecute(dialogItems);
        dialogListPresenter.onDialogItemsLoaded(dialogItems);
    }
}
