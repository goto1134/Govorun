package tinkoff.androidcourse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by goto1134
 * on 23.03.2017.
 */

public class DialogActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_messages);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessagesAdapter(createDataset(), new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(tinkoff.androidcourse.DialogActivity.this, "position = " + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
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
