package ru.vsu.cs.zmaev.screens;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ru.vsu.cs.zmaev.R;
import ru.vsu.cs.zmaev.databinding.FragmentResultBinding;
import ru.vsu.cs.zmaev.model.User;
import ru.vsu.cs.zmaev.viewmodels.AccountViewModel;
import ru.vsu.cs.zmaev.viewmodels.GameViewModel;


public class ResultFragment extends Fragment {

    private FragmentResultBinding binding;
    private GameViewModel viewModel;
    private AccountViewModel accountViewModel;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultBinding.inflate(
                inflater,
                container,
                false
        );
        mAuth = FirebaseAuth.getInstance();
        viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        getUser();
        accountViewModel.getUser().observe(getViewLifecycleOwner(), user ->
                getTotalVal(accountViewModel.getUser().getValue())
        );
        accountViewModel.getUser().removeObserver(a -> {});
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.percentage.setText(viewModel.getCorrPercentage().getValue() + "%");
        binding.continueButton.setOnClickListener(v -> {
            viewModel.getCorrPercentage().observe(getViewLifecycleOwner(), aFloat -> {
                FirebaseUser fbUser = mAuth.getCurrentUser();
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference usersRef = rootRef.child("users").child(fbUser.getUid());
                int topicId = Math.toIntExact(viewModel.getTopicId().getValue());
                String topic = viewModel.getQuizTitles().getValue().get(topicId).getTopicName();
                usersRef.child("results").child(topic).setValue(viewModel.getCorrPercentage().getValue() + "%");
                accountViewModel.getResults().observe(getViewLifecycleOwner(), aLong -> {
                    float perc = aFloat;
                    long res = aLong;
                    if (perc == 100f) {
                        res += 5;
                    }
                    if (perc > 69f) {
                        res += 3;
                    }
                    if (perc > 49f) {
                        res += 2;
                    }
                    rootRef.child("total").child(accountViewModel.getUser().getValue().getName()).setValue(res);
                });
                accountViewModel.getResults().removeObserver(aLong1 -> {});
            });
            Navigation.findNavController(v)
                    .navigate(R.id.action_resultFragment_to_navigation_home);
        });
    }

    private void getUser() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = reference.orderByKey().equalTo(mAuth.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    accountViewModel.setUser(new User(
                            snapshot.child("name").getValue().toString()
                    ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    private void getTotalVal(User user) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("total");
        Query query = reference.orderByKey().equalTo(user.getName());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    accountViewModel.setResults(Long.parseLong(dataSnapshot.getValue().toString()));
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: ASDASDA");
            }
        });
    }
}