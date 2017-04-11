package tinkoff.androidcourse;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tinkoff.androidcourse.ui.widgets.MessageSender;

/**
 * Created by goto1134
 * on 23.03.2017.
 */

public class DialogFragment extends Fragment
        implements MessageSender.MessageSentListener,
        LoaderManager.LoaderCallbacks<List<MessageItem>> {

    public static final int MESSAGE_LOADER_ID = 0;
    private RecyclerView recyclerView;
    private MessagesAdapter adapter;
    private MessageSender sender;

    public static DialogFragment newInstance() {
        return new DialogFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(MESSAGE_LOADER_ID, null, this);
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
        sender = (MessageSender) view.findViewById(R.id.fd_message_sender);
        sender.setMessageSentListener(this);
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_messages);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessagesAdapter(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(), "position = " + position, Toast.LENGTH_SHORT)
                     .show();
            }
        });
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration
                = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void OnMessageSent(String aMessage) {
        new MessageItem(aMessage, new Date(), R.drawable.test_avatar);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Loader<List<MessageItem>> onCreateLoader(int id, Bundle args) {
        return new MessageLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<MessageItem>> loader, List<MessageItem> data) {
        adapter.setDataset(data);
    }

    @Override
    public void onLoaderReset(Loader<List<MessageItem>> loader) {
        adapter.setDataset(null);
    }
}
