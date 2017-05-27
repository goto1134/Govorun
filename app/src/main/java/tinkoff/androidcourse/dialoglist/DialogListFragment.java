package tinkoff.androidcourse.dialoglist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import tinkoff.androidcourse.OnItemClickListener;
import tinkoff.androidcourse.R;
import tinkoff.androidcourse.model.db.DialogItem;

import static tinkoff.androidcourse.Constants.KEY_DIALOG_DESCRIPTION;
import static tinkoff.androidcourse.Constants.KEY_DIALOG_TITLE;

public class DialogListFragment extends MvpFragment<DialogListView, DialogListPresenter>
        implements DialogListView {

    public static final int CREATE_DIALOG_FRAGMENT = 1;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private DialogsAdapter adapter;
    private Button addDialog;
    private DialogListListener listener;
    private List<DialogItem> dialogItems;

    public static DialogListFragment newInstance() {
        return new DialogListFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        if (!(activity instanceof DialogListListener)) {
            throw new IllegalStateException(
                    "Activity does not implement tinkoff.androidcourse.dialoglist.DialogListFragment.DialogListListener");
        }
        listener = ((DialogListListener) activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_list, container, false);
        initRecyclerView(view);
        addDialog = (Button) view.findViewById(R.id.add_dialog);
        addDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDialogDialog createDialogDialog = new CreateDialogDialog();
                createDialogDialog.setTargetFragment(DialogListFragment.this, CREATE_DIALOG_FRAGMENT);
                createDialogDialog.show(getFragmentManager(), null);
            }
        });
        return view;
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_dialogs);
        recyclerView.setHasFixedSize(true);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar_dialogs);
        Context context = getContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        if (adapter == null) {
            adapter = new DialogsAdapter(new ArrayList<DialogItem>(), new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    openDialog(dialogItems.get(position));
                }
            });
        }
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void openDialog(DialogItem dialogItem) {
        listener.onDialogTouched(dialogItem);
    }

    @Override
    public void addDialog(DialogItem dialogItem) {
        adapter.addDialog(dialogItem);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_DIALOG_FRAGMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String title = data.getStringExtra(KEY_DIALOG_TITLE);
                String description = data.getStringExtra(KEY_DIALOG_DESCRIPTION);
                getPresenter().createDialog(title, description);
            }
        }
    }

    @NonNull
    @Override
    public DialogListPresenter createPresenter() {
        return new DialogListPresenter();
    }

    @Override
    public void showLoadInProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showDialogList(List<DialogItem> dialogItemList) {
        dialogItems = dialogItemList;
        adapter.setItems(dialogItemList);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public interface DialogListListener {
        void onDialogTouched(DialogItem dialogItem);
    }
}
