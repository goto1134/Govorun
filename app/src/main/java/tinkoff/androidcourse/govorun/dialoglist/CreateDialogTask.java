package tinkoff.androidcourse.govorun.dialoglist;

import android.os.AsyncTask;

import com.raizlabs.android.dbflow.config.FlowManager;

import tinkoff.androidcourse.govorun.model.db.DialogItem;


/**
 * Created by goto1134
 * on 02.05.2017.
 */

class CreateDialogTask extends AsyncTask<DialogItem, Void, DialogItem> {
    private final DialogListPresenter presenter;

    CreateDialogTask(DialogListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected DialogItem doInBackground(DialogItem... params) {
        if (params.length != 1) {
            throw new IllegalStateException("Number of parameters must be equal to 1");
        }
        DialogItem dialogItem = params[0];
        FlowManager.getModelAdapter(DialogItem.class)
                   .save(dialogItem);
        return dialogItem;
    }

    @Override
    protected void onPostExecute(DialogItem dialogItem) {
        super.onPostExecute(dialogItem);
        presenter.onDialogCreated(dialogItem);
    }
}
