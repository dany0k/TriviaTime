package ru.vsu.cs.zmaev.screens.auth;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ru.vsu.cs.zmaev.R;
import ru.vsu.cs.zmaev.databinding.FragmentRegisterBinding;
import ru.vsu.cs.zmaev.model.User;
import ru.vsu.cs.zmaev.viewmodels.AccountViewModel;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private AccountViewModel viewModel;

    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;
    private DatabaseReference usersRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(
                inflater,
                container,
                false
        );
        viewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        usersRef = rootRef.child("users");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getFBUser().observe(getViewLifecycleOwner(), firebaseUser -> {
            if (mAuth.getCurrentUser() != null) {
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
                        .navigate(R.id.action_registerFragment_to_navigation_home);
            }
        });
        binding.registerButton.setOnClickListener(v -> {
            String email = binding.userLoginEt.getText().toString();
            String name = binding.userNameEt.getText().toString();
            String password = binding.userPasswordEt.getText().toString();
            String passwordAgain = binding.userPasswordAgainEt.getText().toString();

            if (passwordAgain.equals(password)) {
                createAccount(email, password, name);
                createTotal(name);
                Navigation.findNavController(v)
                        .navigate(R.id.action_registerFragment_to_navigation_home);
            } else {
                Toast.makeText(
                        requireActivity(),
                        "Пароли не совпадают!",
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        binding.haveAccountTv.setOnClickListener(v -> {
            Navigation.findNavController(view)
                    .navigate(R.id.action_registerFragment_to_loginFragment);
        });
    }

    private void createTotal(String name) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("total");
        databaseReference.child(name).setValue(0L);
    }

    private void createAccount(String email, String password, String name) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser fbUser = mAuth.getCurrentUser();
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference usersRef = rootRef.child("users");
                        User user = new User(name);
                        usersRef.child(fbUser.getUid()).setValue(user);
                        Log.d(TAG, "createUserWithEmail:success");
                    } else {
                        Log.d(TAG, "createUserWithEmail:failed");
                    }
                });
    }
}