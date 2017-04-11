package tinkoff.androidcourse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import tinkoff.androidcourse.ui.widgets.MessageSender;

/**
 * Created by goto1134
 * on 23.03.2017.
 */

public class DialogFragment extends Fragment
        implements MessageSender.MessageSentListener,
        LoaderCallbacks {

    public static final int MESSAGE_LOADER_ID = 0;
    public static final int SEND_MESSATGE_LOADER_ID = 1;
    public static final String KEY_MESSAGE_TEXT = "MESSAGE_TEXT";
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
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MESSAGE_TEXT, aMessage);
        getLoaderManager().restartLoader(SEND_MESSATGE_LOADER_ID, bundle, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        if (id == MESSAGE_LOADER_ID) {
            return new MessageLoader(getContext());
        } else {
            if (!args.containsKey(KEY_MESSAGE_TEXT))
                throw new IllegalStateException("Bundle does not contain messageText");
            return new SendMessageLoader(getContext(), args.getString(KEY_MESSAGE_TEXT));
        }
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if (loader.getId() == MESSAGE_LOADER_ID) {
            adapter.setDataset((List<MessageItem>) data);
        } else {
            adapter.addMessage(((MessageItem) data));
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
