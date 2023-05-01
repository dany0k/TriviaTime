package ru.vsu.cs.zmaev.screens;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ru.vsu.cs.zmaev.R;
import ru.vsu.cs.zmaev.adapter.ResultsRVAdapter;
import ru.vsu.cs.zmaev.databinding.FragmentAccountBinding;
import ru.vsu.cs.zmaev.model.Result;
import ru.vsu.cs.zmaev.model.User;
import ru.vsu.cs.zmaev.viewmodels.AccountViewModel;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private AccountViewModel viewModel;
    private DatabaseReference reference;
    private ResultsRVAdapter adapter;
    private FirebaseAuth auth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(
                inflater,
                container,
                false);
        viewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        initRvItems();
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = reference.orderByKey().equalTo(auth.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    viewModel.setUser(new User(
                            snapshot.child("name").getValue().toString()
                    ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
        getResults();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.leaverAccountButton.setOnClickListener(v -> {
            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
            bottomNavigationView.setVisibility(View.GONE);
            viewModel.leaveAccount();
            Navigation.findNavController(v)
                    .navigate(R.id.action_navigation_account_to_registerFragment);
        });
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            binding.userNameTv.setText(user.getName());
        });
    }

    public void getResults() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = reference.orderByKey().equalTo(auth.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    List<Result> temp = new ArrayList<>();
                    if (user.getResults() != null) {
                        for (String key : user.getResults().keySet()) {
                            temp.add(new Result(key, user.getResults().get(key)));
                        }
                    }
                    adapter.setItems(temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    private void initRvItems() {
        adapter = new ResultsRVAdapter();
        binding.resultRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.resultRv.setAdapter(adapter);
    }



}