package com.androidtuts4u.arun.registartionapp.ui.dayView;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class DayViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DayViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}