package tinkoff.androidcourse;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.*;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by goto1134
 * on 02.05.2017.
 */
public class CreateDialogDialog extends android.support.v4.app.DialogFragment {

    private CreateDialogListener createDialogListener;
    private EditText dialogTitle;
    private EditText dialogDescription;

    public interface CreateDialogListener {
        void onCreateDialog(String title, String description);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        if (activity instanceof CreateDialogListener) {
            createDialogListener = (CreateDialogListener) activity;
        } else throw new IllegalStateException("Activity does not implement Listener");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_create_dialog, null);
        dialogTitle = ((EditText) view.findViewById(R.id.dialog_title));
        dialogDescription = ((EditText) view.findViewById(R.id.dialog_description));
        return builder.setView(view)
                      .setPositiveButton("Создать", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              createDialogListener.onCreateDialog(
                                      dialogTitle.getText()
                                                 .toString(),
                                      dialogDescription.getText()
                                                       .toString());
                          }
                      })
                      .setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              CreateDialogDialog.this.dismiss();
                          }
                      })
                      .create();
    }
}
