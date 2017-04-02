package tinkoff.androidcourse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class DialogFragment extends Fragment implements MessageSender.MessageSentListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private MessageSender sender;
    private List<MessageItem> dataset;

    public static DialogFragment newInstance() {
        return new DialogFragment();
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
        dataset = createDataset();
        adapter = new MessagesAdapter(dataset, new OnItemClickListener() {
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

    private List<MessageItem> createDataset() {
        List<MessageItem> list = new ArrayList<>();
        list.add(new MessageItem("text", new Date(), R.drawable.test_avatar));
        list.add(new MessageItem("text", new Date(), R.drawable.test_avatar));
        list.add(new MessageItem("text", new Date(), R.drawable.test_avatar));
        list.add(new MessageItem("text", new Date(), R.drawable.test_avatar));
        list.add(new MessageItem("text", new Date(), R.drawable.test_avatar));
        list.add(new MessageItem("text", new Date(), R.drawable.test_avatar));
        list.add(new MessageItem("text", new Date(), R.drawable.test_avatar));
        list.add(new MessageItem("text", new Date(), R.drawable.test_avatar));
        return list;
    }

    @Override
    public void OnMessageSent(String aMessage) {
        dataset.add(new MessageItem(aMessage, new Date(),R.drawable.test_avatar));
        adapter.notifyDataSetChanged();
    }
}
