package ru.vsu.cs.zmaev.screens.auth;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.vsu.cs.zmaev.R;
import ru.vsu.cs.zmaev.databinding.FragmentLoginBinding;
import ru.vsu.cs.zmaev.viewmodels.AccountViewModel;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private FirebaseAuth mAuth;
    private AccountViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(
                inflater,
                container,
                false
        );
        viewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.loginButton.setOnClickListener(v -> {
            String email = binding.userLoginEt.getText().toString();
            String password = binding.userPasswordEt.getText().toString();
            loginAccount(email, password);
            Navigation.findNavController(v)
                    .navigate(R.id.action_loginFragment_to_navigation_home);
        });
    }

    private void loginAccount(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        viewModel.setFBUser(user);
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                    }
                });
    }
}