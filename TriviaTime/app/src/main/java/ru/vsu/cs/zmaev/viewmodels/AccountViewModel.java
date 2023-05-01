package ru.vsu.cs.zmaev.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.vsu.cs.zmaev.model.User;

public class AccountViewModel extends ViewModel {
    private MutableLiveData<User> userMutable = new MutableLiveData<>();
    private MutableLiveData<FirebaseUser> firebaseUserMutable = new MutableLiveData<>();
    private MutableLiveData<Long> result = new MutableLiveData<>();

    private FirebaseAuth mAuth;

    public LiveData<Long> getResults() {
        return result;
    }

    public void setResults(long result) {
        this.result.setValue(result);
    }


    public AccountViewModel() {
        mAuth = FirebaseAuth.getInstance();
    }

    public LiveData<User> getUser() {
        return userMutable;
    }

    public void setFBUser(FirebaseUser user) {
        firebaseUserMutable.setValue(user);
    }

    public void setUser(User user) {
        userMutable.setValue(user);
    }

    public LiveData<FirebaseUser> getFBUser() {
        return firebaseUserMutable;
    }

    public void leaveAccount() {
        mAuth.signOut();
    }
}
