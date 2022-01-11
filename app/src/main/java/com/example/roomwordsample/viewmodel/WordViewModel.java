package com.example.roomwordsample.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.roomwordsample.models.Word;
import com.example.roomwordsample.repository.WordRepository;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository repository;

    private boolean isNetworkAvailable;

    public WordViewModel(@NonNull Application application) {
        super(application);
        this.isNetworkAvailable = false;
        repository = new WordRepository(application);
    }

    public LiveData<List<Word>> getAlphabetizedWords() {
        return repository.getAlphabetizedWords(isNetworkAvailable);
    }

    public void insert(Word word){
        repository.insert(isNetworkAvailable, word);
    }

    public void setNetworkAvailable(boolean networkAvailable) {
        isNetworkAvailable = networkAvailable;
    }
}
