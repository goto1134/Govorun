package tinkoff.androidcourse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_dialogs);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DialogsAdapter(createDataset(), new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                openDialog(position);
            }
        });
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void openDialog(int position) {
        Intent intent = new Intent(this, DialogActivity.class);
        startActivity(intent);
        Toast.makeText(MainActivity.this, "position = " + position, Toast.LENGTH_SHORT).show();
    }

    private List<DialogItem> createDataset() {
        List<DialogItem> list = new ArrayList<>();
        list.add(new DialogItem("title", "date"));
        list.add(new DialogItem("title", "date"));
        list.add(new DialogItem("title", "date"));
        list.add(new DialogItem("title", "date"));
        list.add(new DialogItem("title", "date"));
        list.add(new DialogItem("title", "date"));
        return list;
    }
}
