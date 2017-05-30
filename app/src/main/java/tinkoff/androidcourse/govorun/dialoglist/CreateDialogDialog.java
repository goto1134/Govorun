package tinkoff.androidcourse.govorun.dialoglist;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import tinkoff.androidcourse.govorun.Constants;
import tinkoff.androidcourse.govorun.R;

/**
 * Created by goto1134
 * on 02.05.2017.
 */
public class CreateDialogDialog extends android.support.v4.app.DialogFragment {

    private EditText dialogTitle;
    private EditText dialogDescription;

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
                              Intent intent = new Intent()
                                      .putExtra(Constants.KEY_DIALOG_TITLE,
                                              dialogTitle.getText()
                                                         .toString())
                                      .putExtra(Constants.KEY_DIALOG_DESCRIPTION,
                                              dialogDescription.getText()
                                                               .toString());
                              getTargetFragment()
                                      .onActivityResult(getTargetRequestCode(),
                                              Activity.RESULT_OK,
                                              intent);
                          }
                      })
                      .setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              getTargetFragment()
                                      .onActivityResult(getTargetRequestCode(),
                                              Activity.RESULT_CANCELED,
                                              null);
                              CreateDialogDialog.this.dismiss();
                          }
                      })
                      .create();
    }
}
