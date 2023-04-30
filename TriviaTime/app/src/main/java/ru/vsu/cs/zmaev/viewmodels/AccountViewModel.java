package ru.vsu.cs.zmaev.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.vsu.cs.zmaev.model.Result;
import ru.vsu.cs.zmaev.model.User;

public class AccountViewModel extends ViewModel {
    private MutableLiveData<User> userMutable = new MutableLiveData<>();
    private MutableLiveData<List<Result>> results = new MutableLiveData<>();

    public MutableLiveData<User> getUser() {
        return userMutable;
    }

    public void setUser(User user) {
        userMutable.setValue(user);
    }


}
