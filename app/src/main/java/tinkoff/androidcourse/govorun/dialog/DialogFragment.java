package tinkoff.androidcourse.govorun.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

import tinkoff.androidcourse.govorun.OnItemClickListener;
import tinkoff.androidcourse.govorun.R;
import tinkoff.androidcourse.govorun.model.db.DialogItem;
import tinkoff.androidcourse.govorun.model.db.MessageItem;
import tinkoff.androidcourse.govorun.ui.widgets.MessageSender;


/**
 * Created by goto1134
 * on 23.03.2017.
 */

public class DialogFragment extends MvpFragment<DialogView, DialogPresenter>
        implements tinkoff.androidcourse.govorun.ui.widgets.MessageSender.MessageSentListener, DialogView {

    public static final String DIALOG_ITEM = "DIALOG_ITEM";
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MessagesAdapter adapter = new MessagesAdapter(new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Toast.makeText(getContext(), "position = " + position, Toast.LENGTH_SHORT)
                 .show();
        }
    });
    private MessageSender sender;

    public static DialogFragment newInstance(DialogItem dialogItem) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DIALOG_ITEM, dialogItem);
        DialogFragment dialogFragment = new DialogFragment();
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @NonNull
    @Override
    public DialogPresenter createPresenter() {
        DialogItem dialogItem = (DialogItem) getArguments().getSerializable(DIALOG_ITEM);
        return new DialogPresenter(dialogItem);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        initRecyclerView(view);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar_messages);
        sender = (MessageSender) view.findViewById(R.id.fd_message_sender);
        sender.setMessageSentListener(this);
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_messages);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration
                = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void OnMessageSent(String aMessage) {
        getPresenter().sendMessage(aMessage);
    }

    @Override
    public void addMessage(MessageItem messageItem) {
        adapter.addMessage(messageItem);
    }

    @Override
    public void setMessageItems(List<MessageItem> messageItems) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        sender.setVisibility(View.VISIBLE);
        adapter.setDataset(messageItems);
    }

    @Override
    public void showLoadInProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        sender.setVisibility(View.GONE);
    }
}
