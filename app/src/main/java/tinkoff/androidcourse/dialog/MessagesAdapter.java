package tinkoff.androidcourse.dialog;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

import tinkoff.androidcourse.OnItemClickListener;
import tinkoff.androidcourse.R;
import tinkoff.androidcourse.model.db.MessageItem;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * Created by goto1134
 * on 23.03.2017.
 */

class MessagesAdapter
        extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {
    private List<MessageItem> dataset;
    private OnItemClickListener clickListener;

    MessagesAdapter(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    void setDataset(List<MessageItem> dataset) {
        this.dataset = dataset;
        this.notifyDataSetChanged();
    }

    void addMessage(MessageItem messageItem) {
        this.dataset.add(messageItem);
        this.notifyDataSetChanged();
    }

    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_chat_message, parent, false);
        return new MessagesAdapter.ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(MessagesAdapter.ViewHolder holder, int position) {
        if (dataset != null) {
            MessageItem messageItem = dataset.get(dataset.size() - position - 1);
            holder.text.setText(messageItem.getText());
            holder.date.setText(DateFormat.getDateTimeInstance()
                                          .format(messageItem.getDate()));
            holder.avatar.setImageResource(R.drawable.test_avatar);
        }
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text;
        public TextView date;
        public ImageView avatar;

        ViewHolder(View view, OnItemClickListener listener) {
            super(view);
            text = (TextView) view.findViewById(R.id.tv_message_text);
            date = (TextView) view.findViewById(R.id.tv_message_date);
            avatar = (ImageView) view.findViewById(R.id.iv_message_avatar);
            setListener(listener);
        }

        private void setListener(final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (listener != null && adapterPosition != NO_POSITION) {
                        listener.onItemClick(adapterPosition);
                    }
                }
            });
        }
    }
}
