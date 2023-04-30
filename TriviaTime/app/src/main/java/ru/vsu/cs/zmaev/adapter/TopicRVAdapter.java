package ru.vsu.cs.zmaev.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import java.util.List;
import java.util.Objects;

import ru.vsu.cs.zmaev.R;
import ru.vsu.cs.zmaev.model.Topic;

public class TopicRVAdapter extends RecyclerView.Adapter<TopicRVAdapter.Holder> {

    private SortedList<Topic> sortedList;
    public TopicClickInterface topicClickInterface;

    public TopicRVAdapter(TopicClickInterface topicClickInterface) {
        this.topicClickInterface = topicClickInterface;
        sortedList = new SortedList<>(Topic.class, new SortedList.Callback<Topic>() {
            @Override
            public int compare(Topic o1, Topic o2) {
                return 0;
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Topic oldItem, Topic newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Topic item1, Topic item2) {
                return Objects.equals(item1.getTopicName(), item2.getTopicName());
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);

            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);

            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);

            }
        });
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(sortedList.get(position));
        holder.itemView.setOnClickListener(it -> {
            topicClickInterface.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<Topic> topics) {
        sortedList.replaceAll(topics);
    }

    static class Holder extends RecyclerView.ViewHolder {
        Topic topic;
        TextView topicName;

        public Holder(@NonNull View itemView) {
            super(itemView);
            this.topicName = itemView.findViewById(R.id.topic_name);
        }

        public void bind(Topic topic) {
            this.topic = topic;
            topicName.setText(topic.getTopicName());
        }
    }

    public interface TopicClickInterface {
        void onClick(long id);
    }

}

