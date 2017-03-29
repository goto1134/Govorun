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

/**
 * Created by goto1134
 * on 23.03.2017.
 */

public class DialogFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    public static DialogFragment newInstance() {
        return new DialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dialog, container, false);
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_messages);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessagesAdapter(createDataset(), new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(), "position = " + position, Toast.LENGTH_SHORT)
                     .show();
            }
        });
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
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
}
