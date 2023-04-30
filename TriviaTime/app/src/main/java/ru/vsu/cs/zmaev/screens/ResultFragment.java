package ru.vsu.cs.zmaev.screens;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ru.vsu.cs.zmaev.R;
import ru.vsu.cs.zmaev.databinding.FragmentResultBinding;
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
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getCorrPercentage().observe(getViewLifecycleOwner(), aFloat -> {
            binding.percentage.setText(viewModel.getCorrPercentage().getValue() + "%");
            FirebaseUser fbUser = mAuth.getCurrentUser();
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference usersRef = rootRef.child("users").child(fbUser.getUid());
            int topicId = Math.toIntExact(viewModel.getTopicId().getValue());
            String topic = viewModel.getQuizTitles().getValue().get(topicId).getTopicName();
            usersRef.child("results").child(topic).setValue(viewModel.getCorrPercentage().getValue() + "%");
        });
        binding.continueButton.setOnClickListener(v -> {
            Navigation.findNavController(v)
                    .navigate(R.id.action_resultFragment_to_navigation_home);
        });
    }
}