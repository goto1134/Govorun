package tinkoff.androidcourse;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import tinkoff.androidcourse.model.db.DialogItem;

public class DialogListFragment extends Fragment {

    private RecyclerView recyclerView;
    private DialogsAdapter adapter;
    private Button addDialog;
    private DialogListListener listener;

    public static DialogListFragment newInstance() {
        return new DialogListFragment();
    }

    @NonNull
    private List<DialogItem> getPreviousDialogItems() {
        return SQLite.select()
                     .from(DialogItem.class)
                     .queryList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        if (!(activity instanceof DialogListListener)) {
            throw new IllegalStateException("Activity does not implement tinkoff.androidcourse.DialogListFragment.DialogListListener");
        }
        listener = ((DialogListListener) activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_list, container, false);
        initRecyclerView(view);

        List<DialogItem> dialogItems = getPreviousDialogItems();
        adapter.setItems(dialogItems);

        addDialog = (Button) view.findViewById(R.id.add_dialog);
        addDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDialogCreateCalled();
            }
        });
        return view;
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_dialogs);
        recyclerView.setHasFixedSize(true);
        Context context = getContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DialogsAdapter(new ArrayList<DialogItem>(), new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openDialog(position);
            }
        });
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void openDialog(int position) {
        listener.onDialogTouched(position);
    }

    public void addDialog(DialogItem dialogItem) {
        adapter.addDialog(dialogItem);
    }

    public interface DialogListListener {
        void onDialogTouched(int position);

        void onDialogCreateCalled();
    }
}
