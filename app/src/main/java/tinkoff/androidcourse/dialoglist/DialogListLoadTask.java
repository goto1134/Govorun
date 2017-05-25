package tinkoff.androidcourse.dialoglist;

import android.os.AsyncTask;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import tinkoff.androidcourse.model.db.DialogItem;

/**
 * Created by goto1134
 * on 25.05.2017.
 */

class DialogListLoadTask extends AsyncTask<Void, Void, List<DialogItem>> {

    private final DialogListPresenter dialogListPresenter;

    DialogListLoadTask(DialogListPresenter dialogListPresenter) {
        this.dialogListPresenter = dialogListPresenter;
    }

    @Override
    protected List<DialogItem> doInBackground(Void... params) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
