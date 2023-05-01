package ru.vsu.cs.zmaev.adapter;

import android.annotation.SuppressLint;
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
import ru.vsu.cs.zmaev.model.Leader;

public class LeadersRVAdapter extends RecyclerView.Adapter<LeadersRVAdapter.Holder> {

    private SortedList<Leader> sortedList;

    public LeadersRVAdapter() {
        sortedList = new SortedList<>(Leader.class, new SortedList.Callback<Leader>() {
            @Override
            public int compare(Leader o1, Leader o2) {
                return 0;
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Leader oldItem, Leader newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Leader item1, Leader item2) {
                return Objects.equals(item1.getUsername(), item2.getUsername());
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
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.leader_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<Leader> leaders) {
        sortedList.replaceAll(leaders);
    }

    static class Holder extends RecyclerView.ViewHolder {
        Leader leader;
        TextView userName;
        TextView total;

        public Holder(@NonNull View itemView) {
            super(itemView);
            this.userName = itemView.findViewById(R.id.leader_name);
            this.total = itemView.findViewById(R.id.leader_total);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Leader leader) {
            this.leader = leader;
            userName.setText(leader.getUsername());
            total.setText(Long.toString(leader.getTotal()));
        }
    }
}

