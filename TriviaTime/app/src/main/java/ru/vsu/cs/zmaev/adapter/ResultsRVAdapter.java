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
import ru.vsu.cs.zmaev.model.Result;

public class ResultsRVAdapter extends RecyclerView.Adapter<ResultsRVAdapter.Holder> {

    private SortedList<Result> sortedList;

    public ResultsRVAdapter() {
        sortedList = new SortedList<>(Result.class, new SortedList.Callback<Result>() {
            @Override
            public int compare(Result o1, Result o2) {
                return 0;
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Result oldItem, Result newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Result item1, Result item2) {
                return Objects.equals(item1.getTopicTitle(), item2.getTopicTitle());
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
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.result_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<Result> results) {
        sortedList.replaceAll(results);
    }

    static class Holder extends RecyclerView.ViewHolder {
        Result result;
        TextView topicName;
        TextView resultTV;

        public Holder(@NonNull View itemView) {
            super(itemView);
            this.topicName = itemView.findViewById(R.id.topic_title);
            this.resultTV = itemView.findViewById(R.id.topic_percentage);
        }

        public void bind(Result result) {
            this.result = result;
            topicName.setText(result.getTopicTitle());
            resultTV.setText(result.getResult());
        }
    }
}

