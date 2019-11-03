package com.androidtuts4u.arun.registartionapp.ui.about;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class AboutViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AboutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("About mai kya likhke chahiye tha bhai!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}