package tinkoff.androidcourse;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.raizlabs.android.dbflow.config.FlowManager;

import tinkoff.androidcourse.model.db.DialogItem;

/**
 * Created by goto1134
 * on 02.05.2017.
 */

public class CreateDialogLoader extends AsyncTaskLoader<DialogItem> {
    private DialogItem dialogItem;

    public CreateDialogLoader(Context context, DialogItem dialogItem) {
        super(context);
        this.dialogItem = dialogItem;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public DialogItem loadInBackground() {
        FlowManager.getModelAdapter(DialogItem.class)
                   .save(dialogItem);
        return dialogItem;
    }
}
