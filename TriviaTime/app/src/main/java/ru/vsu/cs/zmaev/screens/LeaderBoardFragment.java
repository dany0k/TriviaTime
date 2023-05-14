package ru.vsu.cs.zmaev.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.vsu.cs.zmaev.adapter.LeadersRVAdapter;
import ru.vsu.cs.zmaev.databinding.FragmentLeaderBoardBinding;
import ru.vsu.cs.zmaev.model.Leader;
import ru.vsu.cs.zmaev.viewmodels.AccountViewModel;

public class LeaderBoardFragment extends Fragment {

    private LeadersRVAdapter adapter;
    private AccountViewModel viewModel;

    private FragmentLeaderBoardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLeaderBoardBinding.inflate(
                inflater,
                container,
                false);
        viewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        initRvItems();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getTotalVal();
    }

    private void initRvItems() {
        adapter = new LeadersRVAdapter();
        binding.leadersRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.leadersRv.setAdapter(adapter);
    }

    private void getTotalVal() {
        List<Leader> totalVal = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("total");
        Query query = reference.orderByValue().limitToLast(100);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String username = dataSnapshot.getKey();
                    long total = (long) dataSnapshot.getValue();
                    totalVal.add(new Leader(username, total));
                }
                Collections.reverse(totalVal);
                adapter.setItems(totalVal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}