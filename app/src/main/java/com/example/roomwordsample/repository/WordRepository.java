package com.example.roomwordsample.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.roomwordsample.dao.WordDao;
import com.example.roomwordsample.dao.WordService;
import com.example.roomwordsample.database.WordRoomDatabase;
import com.example.roomwordsample.models.Word;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordRepository {

    private WordDao wordDao;
    private WordService wordService;
    private MutableLiveData<List<Word>> alphabetizedWords;

    public WordRepository(Application application){
        alphabetizedWords = new MutableLiveData<>();
        //Service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.42.29/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        wordService = retrofit.create(WordService.class);

        // Local Cache
        WordRoomDatabase database = WordRoomDatabase.getInstance(application);
        wordDao = database.getWordDao();
    }

    public LiveData<List<Word>> getAlphabetizedWords(boolean isNetworkAvailable) {
        if (isNetworkAvailable){
            wordService.getAlphabetizedWords().enqueue(new Callback<List<Word>>() {
                @Override
                public void onResponse(Call<List<Word>> call, Response<List<Word>> response) {
                    List<Word> words = response.body();
                    alphabetizedWords.setValue(words);
                }

                @Override
                public void onFailure(Call<List<Word>> call, Throwable t) { }
            });

            return alphabetizedWords;
        }else {
            return wordDao.getAlphabetizedWords();
        }
    }

    public void insert(boolean isNetworkAvailable, Word word){
        if (isNetworkAvailable){
            wordService.insert(word)
                    .enqueue(new Callback<Word>() {
                        @Override
                        public void onResponse(Call<Word> call, Response<Word> response) {

                        }

                        @Override
                        public void onFailure(Call<Word> call, Throwable t) {

                        }
                    });
        }else {
            WordRoomDatabase.databaseWriteExecutor.execute(() -> {
                wordDao.insert(word);
            });
        }
    }
}
